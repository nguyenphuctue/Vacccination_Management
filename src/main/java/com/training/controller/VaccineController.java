package com.training.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

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

import com.training.dto.VaccineDto;
import com.training.service.VaccineService;
import com.training.service.VaccineTypeService;
import com.training.utils.ExcelHelper;

import lombok.RequiredArgsConstructor;

import static com.training.utils.Constant.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("vaccine")
public class VaccineController {

	private final VaccineService vaccineService;
	private final VaccineTypeService vaccineTypeService;

	String messError = null;
	String messSuccess = null;

	@GetMapping("/listVaccine")
	public String showVaccines(Model model) {
		return listVaccines(model, 1, 5, "vaccineId", "asc", "");
	}

	@GetMapping("/listVaccine/{page}")
	public String pageVaccines(Model model, @PathVariable(value = "page") int page,
			@RequestParam(value = "size", required = false, defaultValue = "5") int size,
			@RequestParam(value = "sortField", required = false) String sortField,
			@RequestParam(value = "sortDir", required = false) String sortDir,
			@RequestParam(value = "keyword", required = false) String keyword) {
		return listVaccines(model, page, size, sortField, sortDir, keyword);
	}

	@GetMapping("/listVaccine/search")
	public String searchVaccines(Model model,
			@RequestParam(value = "size", required = false, defaultValue = "5") int size,
			@RequestParam(value = "keyword", required = false) String keyword) {
		return listVaccines(model, 1, size, "vaccineId", "asc", keyword);
	}

	public String listVaccines(Model model, int page, int size, String sortField, String sortDir, String keyword) {
		List<Integer> pagesizes = Arrays.asList(5, 25, 50, 100);
		Page<VaccineDto> pages = vaccineService.findPaginated(page, size, sortField, sortDir, keyword);
		List<VaccineDto> vaccines = pages.getContent();
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", pages.getTotalPages());
		model.addAttribute("totalItems", pages.getTotalElements());
		model.addAttribute("elementt", pages.getNumberOfElements() + size * (page - 1));
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("keyword", keyword);
		model.addAttribute("size", size);
		model.addAttribute("firstE", size * (page - 1) + 1);
		model.addAttribute("pagesizes", pagesizes);
		model.addAttribute("vaccines", vaccines);
		return VACCINE_LIST_VACCINE;
	}

	@GetMapping("/detailVaccine/{id}")
	public String detailEmployee(@PathVariable("id") String vaccineId, Model model) throws Exception {
		Optional<VaccineDto> vaccine = vaccineService.findById(vaccineId);
		model.addAttribute("vaccine", vaccine.get());
		return VACCINE_DETAIL_VACCINE;
	}

	@GetMapping(value = "/addVaccine")
	public String showAddVaccine(Model model) {
		VaccineDto vaccine = new VaccineDto();
		model.addAttribute("vaccineTypes", vaccineTypeService.findByVaccineTypeStatus(true));
		model.addAttribute("vaccine", vaccine);
		return VACCINE_ADD_VACCINE;
	}

	@PostMapping(value = "/addVaccine")
	public String saveVaccine(@Valid @ModelAttribute("vaccine") VaccineDto vaccine, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) throws IOException {
		if (result.hasErrors()) {
			model.addAttribute("vaccineTypes", vaccineTypeService.findByVaccineTypeStatus(true));
			return VACCINE_ADD_VACCINE;
		} else if (vaccineService.findById(vaccine.getVaccineId()).isPresent()) {
			messError = "Vaccine Id is exist";
			redirectAttributes.addFlashAttribute("messError", messError);
			return VACCINE_REDIRECT_ADD_VACCINE;
		} else if ((vaccine.getTimeBeginNextInjection()).after(vaccine.getTimeEndNextInjection())) {
			messError = "Time to start next vaccination must be less than end time";
			redirectAttributes.addFlashAttribute("messError", messError);
			return VACCINE_REDIRECT_ADD_VACCINE;
		} else {
			vaccineService.save(vaccine);
			messSuccess = "Add vaccine successfull";
			redirectAttributes.addFlashAttribute("messSuccess", messSuccess);
			return VACCINE_REDIRECT_LIST_VACCINE;
		}
	}

	@GetMapping(value = "/updateVaccine/{id}")
	public String showUpdateVaccine(Model model, @PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		Optional<VaccineDto> vaccine = vaccineService.findById(id);
		model.addAttribute("vaccineTypes", vaccineTypeService.findByVaccineTypeStatus(true));
		model.addAttribute("vaccine", vaccine);
		return VACCINE_UPDATE_VACCINE;
	}

	@PostMapping(value = "/saveUpdateVaccine")
	public String saveUpdate(Model model, @Valid @ModelAttribute("vaccine") VaccineDto vaccine, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return VACCINE_UPDATE_VACCINE;
		} else if ((vaccine.getTimeBeginNextInjection()).after(vaccine.getTimeEndNextInjection())) {
			messError = "Time to start next vaccination must be less than end time";
			redirectAttributes.addFlashAttribute("messError", messError);
			return "redirect:updateVaccine/" + vaccine.getVaccineId();
		} else {
			vaccineService.save(vaccine);
			messSuccess = "Update successfull";
			redirectAttributes.addFlashAttribute("messSuccess", messSuccess);
			return VACCINE_REDIRECT_LIST_VACCINE;
		}
	}

	@PostMapping(value = "/changeStatus")
	public String changeStatus(Model model, @RequestParam(value = "idChecked", required = false) List<String> ids,
			RedirectAttributes redirectAttributes) {
		if (ids == null) {
			messError = "No data to make inactive!";
			redirectAttributes.addFlashAttribute("messError", messError);
			return VACCINE_REDIRECT_LIST_VACCINE;
		} else {
			for (String id : ids) {
				Optional<VaccineDto> opVaccine = vaccineService.findById(id);
				VaccineDto vaccine = opVaccine.get();
				if (vaccine.isActive() == false) {
					messError = "Invalid data - please recheck your selects!";
					redirectAttributes.addFlashAttribute("messError", messError);
				} else {
					vaccineService.changeStatus(id);
					messSuccess = "Change status successfull";
					redirectAttributes.addFlashAttribute("messSuccess", messSuccess);
				}
			}
			return VACCINE_REDIRECT_LIST_VACCINE;
		}
	}

	@PostMapping(value = "/deleteVaccine")
	public String delete(Model model, @RequestParam(value = "idChecked", required = false) List<String> ids,
			RedirectAttributes redirectAttributes) {
		if (ids == null) {
			messError = "No data to delete!";
			redirectAttributes.addFlashAttribute("messError", messError);
			return VACCINE_REDIRECT_LIST_VACCINE;
		}
		for (String id : ids) {
			Optional<VaccineDto> optional = vaccineService.findById(id);
			if (optional.isPresent()) {
				optional.get().setStatusSave(2);
				vaccineService.save(optional.get());
				messSuccess = "Delete successfull";
				redirectAttributes.addFlashAttribute("messSuccess", messSuccess);
			} else {
				messError = "Delete fail";
				redirectAttributes.addFlashAttribute("messError", messError);
			}
		}
		return VACCINE_REDIRECT_LIST_VACCINE;
	}

	@GetMapping(value = "/importVaccine")
	public String showImportVaccine() {
		return "import-vaccine";
	}

	@PostMapping(value = "/saveImport")
	public String showImportVaccine(Model model, @RequestParam("file") MultipartFile[] files) {

		String hasExcelFormat = Arrays.stream(files).filter(file -> !ExcelHelper.hasExcelFormat(file))
				.map(file -> file.getOriginalFilename()).collect(Collectors.joining(" , "));
		if (!StringUtils.isEmpty(hasExcelFormat)) {
			model.addAttribute("error", "Not Excel File");
			return VACCINE_IMPORT_VACCINE;
		}
		try {
			for (MultipartFile file : files) {
				vaccineService.excel(file);
			}
			model.addAttribute("mess", "Import Successfully");
			return VACCINE_REDIRECT_LIST_VACCINE;
		} catch (Exception e) {
			model.addAttribute("error", "Invalid Data");
			return VACCINE_IMPORT_VACCINE;
		}
	}

}
