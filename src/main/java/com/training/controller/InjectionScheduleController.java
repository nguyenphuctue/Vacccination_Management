package com.training.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.training.dto.VaccineScheDto;
import com.training.model.InjectionSchedule;
import com.training.service.InjectionScheduleService;

import lombok.RequiredArgsConstructor;

import static com.training.utils.Constant.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/injectionSchedule")
public class InjectionScheduleController {

	private final InjectionScheduleService injectionScheduleService;

	@GetMapping("/listInjectionSchedule")
    public String showEmployee(Model model) {
        return listInjectionSchedules(model, 1, 5, "vaccine1.vaccineName", "asc", "");
    }
	
	@GetMapping("/listInjectionSchedule/{page}")
    public String pageEmployees(Model model, @PathVariable(value = "page") int page,
								@RequestParam(value = "size", required = false) int size,
								@RequestParam(value = "sortField", required = false) String sortField,
								@RequestParam(value = "sortDir", required = false) String sortDir,
								@RequestParam(value = "keyword", required = false) String keyword) {
		return listInjectionSchedules(model, page, size, sortField, sortDir, keyword);
	}
	
	 @GetMapping("/listInjectionSchedule/search")
	 public String search(Model model, @RequestParam(value = "size", required = false, defaultValue = "5") int size,
						  @RequestParam(value = "keyword", required = false) String keyword) {
		return listInjectionSchedules(model, 1, size, "vaccine1.vaccineName", "asc", keyword);
	 }
	
	public String listInjectionSchedules(Model model, int page, int size, String sortField, String sortDir, String keyword) {
		List<Integer> pagesizes = Arrays.asList(5, 25, 50, 100);
		Page<InjectionSchedule> pages = injectionScheduleService.findPaginated(page, size, sortField ,sortDir, keyword);
        List<InjectionSchedule> injectionSchedules = pages.getContent();
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
        model.addAttribute("injectionSchedules", injectionSchedules);
		return ISM_LIST_INJECTION_SCHEDULE;
	}
	
	@GetMapping("/detailInjectionSchedule/{id}")
	public String detailInjectionSchedule(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
		Optional<InjectionSchedule> injectOp = injectionScheduleService.findById(id);
		if (!injectOp.isPresent()) {
			redirectAttributes.addFlashAttribute("mess", "Id not found!");
			return ISM_REDIRECT_LIST_INJECTION_SCHEDULE;
		}
		model.addAttribute("injectionSchedule", injectOp.get());
		return ISM_DETAIL_INJECTION_SCHEDULE;
	}

	@GetMapping("/addInjectionSchedule")
	public String addInjectionSchedule(Model model) {
		List<VaccineScheDto> listVaccineScheDto = injectionScheduleService.findAllVac();
		InjectionSchedule injectionSchedule = new InjectionSchedule();
		model.addAttribute("listVaccineScheDto",listVaccineScheDto);
		model.addAttribute("injectionSchedule", injectionSchedule);
		return ISM_ADD_INJECTION_SCHEDULE;
	}

	@PostMapping("/addInjectionSchedule")
	public String saveInjectionSchedule(Model model, @ModelAttribute("injectionSchedule") @Valid InjectionSchedule injectionScheduleAttr, BindingResult result) {
		if (result.hasErrors()) {
			List<VaccineScheDto> listVaccineScheDto = injectionScheduleService.findAllVac();
			model.addAttribute("listVaccineScheDto",listVaccineScheDto);
			return ISM_ADD_INJECTION_SCHEDULE;
		}
		injectionScheduleService.save(injectionScheduleAttr);
		return ISM_REDIRECT_LIST_INJECTION_SCHEDULE;
	}

	@GetMapping("/updateInjectionSchedule")
	public String showUpdateInjectionSchedule(Model model, @RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
		Optional<InjectionSchedule> injectionOp = injectionScheduleService.findById(id);
		if (!injectionOp.isPresent()) {
			redirectAttributes.addFlashAttribute("mess", "Id not found!");
			return ISM_REDIRECT_LIST_INJECTION_SCHEDULE;
		}
		model.addAttribute("listVaccineScheDto", injectionScheduleService.findAllVac());
		model.addAttribute("injectionSchedule", injectionOp.get());
		return ISM_ADD_INJECTION_SCHEDULE;
	}

}
