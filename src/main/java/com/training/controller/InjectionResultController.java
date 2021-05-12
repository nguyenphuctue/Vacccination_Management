package com.training.controller;

import com.training.model.Customer;
import com.training.model.InjectionResult;
import com.training.model.Vaccine;
import com.training.service.CustomerService;
import com.training.service.InjectionResultService;
import com.training.service.VaccineService;
import com.training.validator.InjectionResultValidator;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.training.utils.Constant.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("injection-result")
public class InjectionResultController {

	private final InjectionResultService injectionResultService;
	private final CustomerService customerService;
	private final VaccineService vaccineService;
	private final InjectionResultValidator injectionResultValidator;
	String size = "5";

	@InitBinder
	protected void initBinder(WebDataBinder dataBinder) {
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		if (target.getClass() == InjectionResult.class) {
			dataBinder.setValidator(injectionResultValidator);
		}
	}

	@GetMapping("/add")
	public String addInjectionResult(Model model) {
		model.addAttribute("injectionResult", new InjectionResult());
		model.addAttribute("customers", customerService.findAllCI());
		model.addAttribute("vaccines", vaccineService.findByActive(true));
		return IRM_ADD_INJECTION_RESULT;
	}

	@PostMapping(value = "/update")
	public String update(Model model, @RequestParam(name = "idChecked", required = false) List<Long> ids,
			RedirectAttributes redirectAttributes) {
		if (ids != null && ids.size() == 1) {
			model.addAttribute("injectionResult", injectionResultService.findByInjectionResultId(ids.get(0)).get());
			model.addAttribute("customers", customerService.findAllCI());
			model.addAttribute("vaccines", vaccineService.findByActive(true));
			return IRM_ADD_INJECTION_RESULT;
		}
		if (ids == null) {
			redirectAttributes.addFlashAttribute("errorDelete", "No data update!");
		} else {
			redirectAttributes.addFlashAttribute("errorDelete", "Only 1 data update!");
		}
		return IRM_REDIRECT_LIST_INJECTION_RESULT;
	}

	@PostMapping("/save")
	public String save(Model model, @ModelAttribute("injectionResult") @Valid InjectionResult injectionResult,
			BindingResult result, RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			if (injectionResult.getCustomer() == null) {
				injectionResult.setCustomer(new Customer());
			}
			if (injectionResult.getVaccine() == null) {
				injectionResult.setVaccine(new Vaccine());
			}
			model.addAttribute("customers", customerService.findAllCI());
			model.addAttribute("vaccines", vaccineService.findByActive(true));
			return IRM_ADD_INJECTION_RESULT;
		}
		injectionResultService.save(injectionResult);
		redirectAttributes.addFlashAttribute("addSuccess", "Add Success!");
		return IRM_REDIRECT_LIST_INJECTION_RESULT;
	}

	@GetMapping("/list")
	public String injectionList(Model model) {
		return findPaginated(model, 1, "");
	}

	@GetMapping("/search")
	public String search(Model model, @RequestParam String sizee, @RequestParam String keyword) {
		size = sizee;
		return findPaginated(model, 1, keyword);
	}

	@GetMapping("/page/{pageNo}")
	public String findPaginated(Model model, @PathVariable(value = "pageNo") int pageNo,
			@RequestParam("keyword") String keyword) {
		List<Integer> sizeList = Arrays.asList(5, 25, 50, 100);
		int pageSize = Integer.parseInt(size);

		Page<InjectionResult> page = injectionResultService.findPaginated(pageNo, pageSize, keyword);
		List<InjectionResult> injectionResults = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("sizePage", size);
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sizeList", sizeList);
		model.addAttribute("sizee", size);
		model.addAttribute("keyword", keyword);

		model.addAttribute("injectionResults", injectionResults);
		return IRM_LIST_INJECTION_RESULT;
	}

	@PostMapping(value = "/delete")
	public String delete(@RequestParam(name = "idChecked", required = false) List<Long> ids,
			RedirectAttributes redirectAttributes) {
		if (ids != null) {
			for (long id : ids) {
				injectionResultService.deleteById(id);
				redirectAttributes.addFlashAttribute("deleteSuccess", "Delete Success");
			}
			return IRM_REDIRECT_LIST_INJECTION_RESULT;
		}
		redirectAttributes.addFlashAttribute("errorDelete", "No data deleteted!");
		return IRM_REDIRECT_LIST_INJECTION_RESULT;
	}

}
