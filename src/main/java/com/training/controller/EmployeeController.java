package com.training.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.training.dto.EmployeeDto;
import com.training.model.Role;
import com.training.service.EmployeeService;
import com.training.service.RoleService;
import com.training.utils.ConvertData;

import javax.validation.Valid;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static com.training.utils.Constant.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("employee")
public class EmployeeController {

	private final EmployeeService employeeService;
	private final RoleService roleService;
	
	@GetMapping("/listEmployee")
    public String showEmployees(Model model) {
        return listEmployees(model, 1, 5, "employeeId", "asc", "");
    }
	
	@GetMapping("/listEmployee/{page}")
    public String pageEmployees(Model model, @PathVariable(value = "page") int page,
								@RequestParam(value = "size", required = false, defaultValue = "5") int size,
								@RequestParam(value = "sortField", required = false) String sortField,
								@RequestParam(value = "sortDir", required = false) String sortDir,
								@RequestParam(value = "keyword", required = false) String keyword) {
		return listEmployees(model, page, size, sortField, sortDir, keyword);
	}
	
	 @GetMapping("/listEmployee/search")
	 public String searchEmployees(Model model, @RequestParam(value = "size", required = false, defaultValue = "5") int size,
						  @RequestParam(value = "keyword", required = false) String keyword) {
	     return listEmployees(model, 1, size, "employeeId", "asc", keyword);
	 }
	 
	public String listEmployees(Model model, int page, int size, String sortField, String sortDir, String keyword) {
		List<Integer> pagesizes = Arrays.asList(5, 25, 50, 100);
		Page<EmployeeDto> pages = employeeService.findPaginated(page, size, sortField ,sortDir, keyword);
        List<EmployeeDto> employees = pages.getContent();
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("totalItems", pages.getTotalElements());
        model.addAttribute("elementt",pages.getNumberOfElements()  + size*(page-1));
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("keyword", keyword);
        model.addAttribute("size", size);
        model.addAttribute("firstE", size*(page-1) + 1);
        model.addAttribute("pagesizes", pagesizes);
        model.addAttribute("employees", employees);
		return EMPLOYEE_LIST_EMPLOYEE;
	}

	@GetMapping("/detailEmployee/{id}")
	public String detailEmployee(@PathVariable("id") String employeeId, Model model, RedirectAttributes redirectAttributes) {
		Optional<EmployeeDto> employee = employeeService.findById(employeeId);
		if (!employee.isPresent()) {
			redirectAttributes.addFlashAttribute("mess", "Id not found!");
			return EMPLOYEE_REDIRECT_LIST_EMPLOYEE;
		}
		model.addAttribute("employee", employee.get());
		model.addAttribute("ava", employee.get().getBase64Img());
		return EMPLOYEE_DETAIL_EMPLOYEE;
	}

	@GetMapping(value = "/addEmployee")
	public String addEmployee(Model model) {
		EmployeeDto employeeDto = new EmployeeDto();
		model.addAttribute("employee", employeeDto);
		return EMPLOYEE_ADD_EMPLOYEE;
	}

	@PostMapping(value = "/addEmployee")
	public String saveEmployee(Model model, @ModelAttribute("employee") @Valid EmployeeDto employeeAttr,
			BindingResult result, @RequestParam("images") MultipartFile multipartFile,
			RedirectAttributes redirectAttributes) throws IOException {
		String mess = "";
		if (result.hasErrors()) {
			return EMPLOYEE_ADD_EMPLOYEE;
		}
		Optional<EmployeeDto> optional = employeeService.findById(employeeAttr.getEmployeeId());
		if (!optional.isPresent()) {
			mess = "Add employee successfull!";
			employeeAttr.setStatusSave(1);
		} else {
			mess = "Update employee successfull!";
		}
		if (optional.isPresent() && (employeeAttr.getStatusSave() == 0 || employeeAttr.getStatusSave() == 2)) {
			mess = "Employee id is duplicate, please input again!";
			model.addAttribute("mess", mess);
			employeeAttr.setEmployeeId(null);
			return EMPLOYEE_ADD_EMPLOYEE;
		}
		if (!StringUtils.cleanPath(multipartFile.getOriginalFilename()).equals("") || employeeAttr.getBase64Img() == null) {
			byte[] bytes = multipartFile.getBytes();
			String fileName = employeeAttr.getEmployeeId() + "_"
					+ StringUtils.cleanPath(multipartFile.getOriginalFilename());
			employeeAttr.setImage(fileName);
			Path path = Paths.get(UPLOAD_FOLDER + fileName);
			Files.write(path, bytes);
			byte[] imgByte = new byte[0];
			File file = path.toFile();
			try (InputStream in = new FileInputStream(file)) {
				imgByte = ConvertData.toByteArray(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
			employeeAttr.setBase64Img(Base64.getEncoder().encodeToString(imgByte));
		}
		employeeService.save(employeeAttr);
		redirectAttributes.addFlashAttribute("messs", mess);
		return EMPLOYEE_REDIRECT_LIST_EMPLOYEE;
	}

	@GetMapping(value = "/updateEmployee")
	public String showUpdateEmployee(Model model, @RequestParam("id") String id, RedirectAttributes redirectAttributes) {
		if (id.isEmpty()) {
			redirectAttributes.addFlashAttribute("mess", "Id not found!");
			return EMPLOYEE_REDIRECT_LIST_EMPLOYEE;
		}
		EmployeeDto employeeDto = employeeService.findById(id).get();
		model.addAttribute("employee", employeeDto);
		return EMPLOYEE_ADD_EMPLOYEE;
	}

	@GetMapping(value = "/deleteEmployee")
	public String deleteEmployee(@RequestParam("id") String id, RedirectAttributes redirectAttributes) {
		Optional<EmployeeDto> optional = employeeService.findById(id);
		Optional<Role> role = roleService.findByRoleName("ROLE_ADMIN");
		String mess = null;
		if (optional.isPresent() && !optional.get().getRoles().contains(role.get())) {
			optional.get().setStatusSave(2);
			employeeService.save(optional.get());
		} else {
			mess = "This account is ADMIN, can not delete!";
		}
		redirectAttributes.addFlashAttribute("mess", mess);
		return EMPLOYEE_REDIRECT_LIST_EMPLOYEE;
	}

	@PostMapping(value = "/deleteMulti")
	public String deleteMulti(@RequestParam(value = "idChecked", required = false) List<String> ids, RedirectAttributes redirectAttributes) {
		String mess = "";
		List<String> idAd = new ArrayList<>();
		if (ids == null) {
			mess = "Delete not completed. Please choose one or more data to delete";
			redirectAttributes.addFlashAttribute("mess", mess);
			return EMPLOYEE_REDIRECT_LIST_EMPLOYEE;
		}
		Optional<Role> role = roleService.findByRoleName("ROLE_ADMIN");
		for (String id : ids) {
			Optional<EmployeeDto> optional = employeeService.findById(id);
			if (optional.isPresent() && !optional.get().getRoles().contains(role.get())) {
				optional.get().setStatusSave(2);
				employeeService.save(optional.get());
			} else {
				idAd.add(optional.get().getEmployeeId());
			}
		}
		if (idAd.size() > 0) {
			mess = " This account " + idAd.get(0) + " is ADMIN, can not delete!";
		}
		if (idAd.size() > 1) {
			mess = "List account " + idAd + " are ADMIN, can not delete!";
		}
		redirectAttributes.addFlashAttribute("mess", mess);
		return EMPLOYEE_REDIRECT_LIST_EMPLOYEE;
	}
}
