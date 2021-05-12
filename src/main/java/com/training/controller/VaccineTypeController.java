package com.training.controller;

import static com.training.utils.Constant.VT_ADD_VACCINE_TYPE;
import static com.training.utils.Constant.VT_DETAIL_VACCINE_TYPE;
import static com.training.utils.Constant.VT_LIST_VACCINE_TYPE;
import static com.training.utils.Constant.VT_REDIRECT_ADD_VACCINE_TYPE;
import static com.training.utils.Constant.VT_REDIRECT_LIST_VACCINE_TYPE;
import static com.training.utils.Constant.VT_UPDATE_VACCINE_TYPE;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

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

import com.training.dto.VaccineTypeDto;
import com.training.service.VaccineTypeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("vaccineType")
public class VaccineTypeController {

	private final VaccineTypeService vaccineTypeService;
	private final String uploadFolder = "D:/temp/";
	String messError = null;

	@GetMapping("/listVaccineType")
	public String showVaccineType(Model model) {
		return listVaccineTypes(model, 1, 5, "vaccineTypeId", "asc", "");
	}

	@GetMapping("/listVaccineType/{page}")
	public String pageVaccineTypes(Model model, @PathVariable(value = "page") int page,
			@RequestParam(value = "size", required = false) int size,
			@RequestParam(value = "sortField", required = false) String sortField,
			@RequestParam(value = "sortDir", required = false) String sortDir,
			@RequestParam(value = "keyword", required = false) String keyword) {
		return listVaccineTypes(model, page, size, sortField, sortDir, keyword);
	}

	@GetMapping("/listVaccineType/search")
	public String search(Model model, @RequestParam(value = "size", required = false, defaultValue = "5") int size,
			@RequestParam(value = "keyword", required = false) String keyword) {
		return listVaccineTypes(model, 1, size, "vaccineTypeId", "asc", keyword);
	}

	public String listVaccineTypes(Model model, int page, int size, String sortField, String sortDir, String keyword) {
		List<Integer> pagesizes = Arrays.asList(5, 25, 50, 100);
		Page<VaccineTypeDto> pages = vaccineTypeService.findPaginated(page, size, sortField, sortDir, keyword);
		List<VaccineTypeDto> vaccineTypes = pages.getContent();
		if (pages.isEmpty()) {
			messError = "No data found!";
			model.addAttribute("messError", messError);
		}
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
		model.addAttribute("vaccineTypes", vaccineTypes);
		return VT_LIST_VACCINE_TYPE;
	}

	@GetMapping("/detailVaccineType/{id}")
	public String detailVaccineType(@PathVariable("id") String vaccineTypeId, Model model) throws Exception {
		Optional<VaccineTypeDto> vaccineType = vaccineTypeService.findById(vaccineTypeId);
		model.addAttribute("vaccineType", vaccineType.get());
		model.addAttribute("ava", vaccineType.get().getBase64Img());
		return VT_DETAIL_VACCINE_TYPE;
	}

	@GetMapping(value = "/addVaccineType")
	public String addVaccineType(Model model) {
		VaccineTypeDto vaccineTypeDto = new VaccineTypeDto();
		model.addAttribute("vaccineType", vaccineTypeDto);
		return VT_ADD_VACCINE_TYPE;
	}

	@PostMapping(value = "/addVaccineType")
	public String saveVaccineType(Model model, @ModelAttribute("vaccineType") @Valid VaccineTypeDto vaccineTypeAttr,
			BindingResult result, @RequestParam("avatar") MultipartFile multipartFile,
			RedirectAttributes redirectAttributes) throws IOException {
		String messAdd = null;
		if (result.hasErrors()) {
			return VT_ADD_VACCINE_TYPE;
		} else if (vaccineTypeService.findById(vaccineTypeAttr.getVaccineTypeId()).isPresent()) {
			messAdd = "Vaccine type id is exist";
			redirectAttributes.addFlashAttribute("messAdd", messAdd);
			return VT_REDIRECT_ADD_VACCINE_TYPE;
		}
		byte[] bytes = multipartFile.getBytes();
		String fileName = vaccineTypeAttr.getVaccineTypeId() + "_"
				+ StringUtils.cleanPath(multipartFile.getOriginalFilename());
		VaccineTypeDto vaccineTypeDto = new VaccineTypeDto();
		Optional<VaccineTypeDto> optionalVaccineType = vaccineTypeService.findById(vaccineTypeAttr.getVaccineTypeId());
		if (!optionalVaccineType.isPresent()) {
			vaccineTypeDto = vaccineTypeAttr;
		} else {
			vaccineTypeDto = optionalVaccineType.get();
			vaccineTypeDto.setVaccineTypeName(vaccineTypeAttr.getVaccineTypeName());
			vaccineTypeDto.setDescription(vaccineTypeAttr.getDescription());
			vaccineTypeDto.setVaccineTypeStatus(vaccineTypeAttr.isVaccineTypeStatus());
		}
		vaccineTypeDto.setImage(fileName);
		Path path = Paths.get(uploadFolder + fileName);
		Files.write(path, bytes);
		byte[] imgByte = new byte[0];
		File file = path.toFile();
		try (InputStream in = new FileInputStream(file)) {
			imgByte = toByteArray(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (StringUtils.cleanPath(multipartFile.getOriginalFilename()).equals("")
				&& vaccineTypeDto.getBase64Img() != null) {
			vaccineTypeDto.setBase64Img(vaccineTypeDto.getBase64Img());
		} else {
			vaccineTypeDto.setBase64Img(Base64.getEncoder().encodeToString(imgByte));
		}
		vaccineTypeService.save(vaccineTypeDto);
		return VT_REDIRECT_LIST_VACCINE_TYPE;
	}

	@GetMapping(value = "/makeActiveVaccineType", params = "action=changeStatus")
	public String changeStatus(Model model, @RequestParam(value = "idChecked", required = false) List<String> ids,
			RedirectAttributes redirectAttributes) {
		if (ids == null) {
			redirectAttributes.addFlashAttribute("errorStatus", "No data to make inactive!");
			return VT_REDIRECT_LIST_VACCINE_TYPE;
		}

		boolean kt = true;
		for (String id : ids) {
			Optional<VaccineTypeDto> optional = vaccineTypeService.findById(id);
			if (optional.get().isVaccineTypeStatus() == false) {
				kt = false;
			}
		}

		if (kt == false) {
			redirectAttributes.addFlashAttribute("errorStatus1", "Invalid data - please recheck your selects!");
			return VT_REDIRECT_LIST_VACCINE_TYPE;
		}

		for (String id : ids) {
			vaccineTypeService.changeStatus(id);
			redirectAttributes.addFlashAttribute("successStatus", "Change status of all selected successfull");
		}
		return VT_REDIRECT_LIST_VACCINE_TYPE;
	}

	@GetMapping(value = "/updateVaccineType/{id}")
	public String showUpdateVaccineType(Model model, @PathVariable("id") String id,
			RedirectAttributes redirectAttributes) {
		model.addAttribute("vaccineType", vaccineTypeService.findById(id));
		model.addAttribute("ava", vaccineTypeService.findById(id).get().getBase64Img());
		return VT_UPDATE_VACCINE_TYPE;
	}

	@PostMapping(value = "/saveUpdateVaccineType")
	public String saveUpdate(Model model, @Valid VaccineTypeDto vaccineType, BindingResult result,
			RedirectAttributes redirectAttributes) {
		String messUpdate = null;
		if (result.hasErrors()) {
			return "update-vaccine-type";
		} else {
			vaccineTypeService.save(vaccineType);
			messUpdate = "Update successfull";
			redirectAttributes.addFlashAttribute("messUpdate", messUpdate);
			return VT_REDIRECT_LIST_VACCINE_TYPE;
		}
	}

	private byte[] toByteArray(InputStream in) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = in.read(buffer)) != -1) {
			os.write(buffer, 0, len);
		}
		return os.toByteArray();
	}

}
