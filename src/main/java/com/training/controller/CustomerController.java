package com.training.controller;

import com.training.dto.CustomerDto;
import com.training.service.CustomerService;
import com.training.validator.CustomerValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.training.utils.Constant.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("customer")
public class CustomerController {

    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;
    private final CustomerValidator customerValidator;
    String size = "5";

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        if (target.getClass() == CustomerDto.class) {
            dataBinder.setValidator(customerValidator);
        }
    }

    @GetMapping(value = "/register")
    public String register(Model model) {
        model.addAttribute("customer", new CustomerDto());
        return CUSTOMER_REGISTER_CUSTOMER;
    }

    @GetMapping(value = "/update")
    public String update(Model model, @RequestParam long id) {
        model.addAttribute("customer", customerService.findById(id).get());
        return CUSTOMER_REGISTER_CUSTOMER;
    }

    @PostMapping(value = "/save")
    public String save(@ModelAttribute("customer") @Valid CustomerDto customerDto, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return CUSTOMER_REGISTER_CUSTOMER;
        }
        customerService.save(customerDto);
        redirectAttributes.addFlashAttribute("status", "Register Success");
        return CUSTOMER_REDIRECT_LIST_CUSTOMER;
    }

    @GetMapping("/list-customer")
    public String showCustomer(Model model) {
        return findPaginated(model, 1, "customerId", "asc", "");
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam String sizee, @RequestParam String keyword) {
        size = sizee;
        return findPaginated(model, 1,"customerId", "asc", keyword);
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(Model model, @PathVariable (value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                @RequestParam("keyword") String keyword) {
        List<Integer> sizeList = Arrays.asList(5, 25, 50, 100);
        int pageSize = Integer.parseInt(size);
        Page<CustomerDto> page = customerService.findPaginated(pageNo, pageSize, sortField ,sortDir, keyword);
        List<CustomerDto> customers = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("sizePage", size);
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("sizeList", sizeList);
        model.addAttribute("sizee", size);
        model.addAttribute("keyword", keyword);

        model.addAttribute("customers", customers);
        return CUSTOMER_LIST_CUSTOMER;
    }

    @PostMapping(value = "/delete")
    public String delete(@RequestParam(name = "idChecked", required = false) List<Long> ids, RedirectAttributes redirectAttributes) {
        if (!(ids == null || ids.isEmpty())){
            for (long id:ids) {
                customerService.deleteById(id);
                redirectAttributes.addFlashAttribute("deleteSuccess", "Delete Success");
            }
            return CUSTOMER_REDIRECT_LIST_CUSTOMER;
        }
        redirectAttributes.addFlashAttribute("errorDelete", "No data deleteted!");
        return CUSTOMER_REDIRECT_LIST_CUSTOMER;
    }

    @GetMapping(value = "/detail")
    public String detail(Model model, @RequestParam long id, Principal principal) {
        long idCheck = 0;
        Optional<CustomerDto> customerOption = customerService.findByUserName(principal.getName());
        if (customerOption.isPresent()) {
            idCheck = customerService.findByUserName(principal.getName()).get().getCustomerId();
        }
        model.addAttribute("idCheck", idCheck);
        model.addAttribute("customer", customerService.findById(id).get());
        return CUSTOMER_DETAIL_CUSTOMER;
    }

    private CustomerDto customer;
    @GetMapping(value = "/change-password")
    public String changePass(Model model, @RequestParam long id) {
        customer = customerService.findById(id).get();
        model.addAttribute("customer", customer);
        return CUSTOMER_CHANGE_PASSWORD;
    }

    @PostMapping(value = "/save-password")
    public String save(Model model, String prPass ,String newPassword, String passwordConfirm, String capcha, String code, RedirectAttributes redirectAttributes) {
        if (capcha.equals("")) {
            model.addAttribute( "errorCapcha","You must input information into fields (*)");
            return CUSTOMER_CHANGE_PASSWORD;
        }
        if (!passwordEncoder.matches(prPass, customer.getPassword())) {
            model.addAttribute( "errorPrPass","Password not confirm");
            return CUSTOMER_CHANGE_PASSWORD;
        }
        if (!newPassword.equals(passwordConfirm)) {
            model.addAttribute( "errorPasswordConfirm","Password not confirm");
            return CUSTOMER_CHANGE_PASSWORD;
        }
        if (!capcha.equals(code)) {
            model.addAttribute( "errorCapcha","Capcha not confirm");
            return CUSTOMER_CHANGE_PASSWORD;
        }
        customer.setPassword(newPassword);
        customerService.save(customer);
        redirectAttributes.addFlashAttribute("status", "Register Success");
        return CUSTOMER_REDIRECT_LIST_CUSTOMER;
    }
}
