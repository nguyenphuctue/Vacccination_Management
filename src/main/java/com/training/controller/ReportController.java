package com.training.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.training.dto.CustomerReportDto;
import com.training.dto.InjectionResultRpDto;
import com.training.dto.VaccineDto;
import com.training.service.CustomerService;
import com.training.service.InjectionResultService;
import com.training.service.VaccineService;
import com.training.utils.ConvertData;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("report")
public class ReportController {

	private final CustomerService customerService;
	private final VaccineService vaccineService;
	private final InjectionResultService injectionResultService;

	private static final String REPORT_VACCINE = "report-vaccine";
	private static final String CHART_VACCINE = "chart-vaccine";

	@GetMapping("/reportCustomer")
	public String reportCustomer(Model model) {
		List<CustomerReportDto> customers = customerService.findAllRp();
		model.addAttribute("customers", customers);
		return "report-customer";
	}

	@GetMapping("/chartCustomer")
	public String chartCustomer(Model model,
			@RequestParam(value = "year", required = false, defaultValue = "0") int year) {
		List<Integer> listYears = injectionResultService.getListYear();
		Map<String, Object> dataByMonth = new LinkedHashMap<>();
		List<Object[]> getlistByYear = customerService.getlistByYear(year);
			dataByMonth.put("January", getlistByYear.get(0)[0]);
			dataByMonth.put("February", getlistByYear.get(0)[1]);
			dataByMonth.put("March", getlistByYear.get(0)[2]);
			dataByMonth.put("April", getlistByYear.get(0)[3]);
			dataByMonth.put("May", getlistByYear.get(0)[4]);
			dataByMonth.put("June", getlistByYear.get(0)[5]);
			dataByMonth.put("July", getlistByYear.get(0)[6]);
			dataByMonth.put("August", getlistByYear.get(0)[7]);
			dataByMonth.put("September", getlistByYear.get(0)[8]);
			dataByMonth.put("October", getlistByYear.get(0)[9]);
			dataByMonth.put("November", getlistByYear.get(0)[10]);
			dataByMonth.put("December", getlistByYear.get(0)[11]);
		model.addAttribute("year", year);
		model.addAttribute("listYears", listYears);
		model.addAttribute("chartData", dataByMonth);
		return "chart-customer";
	}

	@GetMapping("/chartInjectionResult")
	public String chartInjectionResult(Model model,
			@RequestParam(value = "year", required = false, defaultValue = "0") int year) {
		List<Integer> listYears = injectionResultService.getListYear();
		Map<String, Object> dataByMonth = new LinkedHashMap<>();
		List<Object[]> getlistByYear = injectionResultService.getlistByYear(year);
		for (Object[] month : getlistByYear) {
			dataByMonth.put("January", month[0]);
			dataByMonth.put("February", month[1]);
			dataByMonth.put("March", month[2]);
			dataByMonth.put("April", month[3]);
			dataByMonth.put("May", month[4]);
			dataByMonth.put("June", month[5]);
			dataByMonth.put("July", month[6]);
			dataByMonth.put("August", month[7]);
			dataByMonth.put("September", month[8]);
			dataByMonth.put("October", month[9]);
			dataByMonth.put("November", month[10]);
			dataByMonth.put("December", month[11]);
		}
		model.addAttribute("year", year);
		model.addAttribute("listYears", listYears);
		model.addAttribute("chartData", dataByMonth);
		return "chart-injection-result";
	}

	@GetMapping("/reportInjectionResult")
	public String reportInjectionResult(Model model) {
		List<InjectionResultRpDto> injectionResults = injectionResultService.findAllRp();
		model.addAttribute("injectionResults", injectionResults);
		model.addAttribute("vaccineTypes", vaccineService.findAll());
		return "report-injection-result";
	}

	@GetMapping("/searchInjectionResult")
	public String searchInjectionResult(Model model,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "prevention", required = false) String prevention,
			@RequestParam(value = "vaccineTypeName", required = false) String vaccineTypeName) {
		List<InjectionResultRpDto> findByOptions = new ArrayList<>();
		Date frDate = null;
		Date tDate = null;
		frDate = ConvertData.stringToDate(fromDate);
		tDate = ConvertData.stringToDate(toDate);
		if (frDate != null && tDate != null && frDate.after(tDate)) {
			model.addAttribute("mess", "Start Date must not be later than End Date");
			return "report-injection-result";
		}
		findByOptions = injectionResultService.findByOptions(frDate, tDate, prevention, vaccineTypeName);
		model.addAttribute("fromDate", fromDate);
		model.addAttribute("toDate", toDate);
		model.addAttribute("prevention", prevention);
		model.addAttribute("vaccineTypeName", vaccineTypeName);
		model.addAttribute("injectionResults", findByOptions);
		return "report-injection-result";
	}

	@GetMapping("/searchCustomer")
	public String searchCustomer(Model model, @RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "fullName", required = false) String fullName,
			@RequestParam(value = "address", required = false) String address) {
		List<CustomerReportDto> findByOptions = new ArrayList<>();
		Date frDate = null;
		Date tDate = null;
		frDate = ConvertData.stringToDate(fromDate);
		tDate = ConvertData.stringToDate(toDate);
		if (frDate != null && tDate != null && frDate.after(tDate)) {
			model.addAttribute("mess", "Start Date must not be later than End Date");
			return "report-customer";
		}
		findByOptions = customerService.findByOptions(frDate, tDate, fullName, address);
		model.addAttribute("fromDate", fromDate);
		model.addAttribute("toDate", toDate);
		model.addAttribute("fullName", fullName);
		model.addAttribute("address", address);
		model.addAttribute("customers", findByOptions);
		return "report-customer";
	}

	@GetMapping("/reportVaccine")
	public String vaccineReport(Model model) {
		List<VaccineDto> vaccines = vaccineService.findAll();
		model.addAttribute("vaccines", vaccines);
		return REPORT_VACCINE;
	}

	@GetMapping("/searchVaccine")
	public String searchVaccine(Model model,
			@RequestParam(value = "timeBeginNextInjection", required = false) String timeBeginNextInjection,
			@RequestParam(value = "timeEndNextInjection", required = false) String timeEndNextInjection,
			@RequestParam(value = "vaccineTypeName", required = false) String vaccineTypeName,
			@RequestParam(value = "origin", required = false) String origin) {
		List<VaccineDto> findByOptions = new ArrayList<>();

		Date frDate = null;
		Date tDate = null;
		frDate = ConvertData.stringToDate(timeBeginNextInjection);
		tDate = ConvertData.stringToDate(timeEndNextInjection);
		if (frDate != null && tDate != null && frDate.after(tDate)) {
			model.addAttribute("mess", "Start Date must not be later than End Date");
			return REPORT_VACCINE;
		}
		findByOptions = vaccineService.findByOptions(frDate, tDate, vaccineTypeName, origin);
		model.addAttribute("timeBeginNextInjection", timeBeginNextInjection);
		model.addAttribute("timeEndNextInjection", timeEndNextInjection);
		model.addAttribute("vaccineTypeName", vaccineTypeName);
		model.addAttribute("origin", origin);
		model.addAttribute("vaccines", findByOptions);
		return REPORT_VACCINE;
	}

	@GetMapping("/chartVaccine")
	public String chartVaccine(Model model,
			@RequestParam(value = "year", required = false, defaultValue = "2020") int year) {
		List<Integer> listYears = vaccineService.getListYear();
		Map<String, Object> dataByMonth = new LinkedHashMap<>();
		List<Object[]> getlistByYear = vaccineService.getlistByYear(year);
		for (Object[] month : getlistByYear) {
			dataByMonth.put("January", month[0]);
			dataByMonth.put("February", month[1]);
			dataByMonth.put("March", month[2]);
			dataByMonth.put("April", month[3]);
			dataByMonth.put("May", month[4]);
			dataByMonth.put("June", month[5]);
			dataByMonth.put("July", month[6]);
			dataByMonth.put("August", month[7]);
			dataByMonth.put("September", month[8]);
			dataByMonth.put("October", month[9]);
			dataByMonth.put("November", month[10]);
			dataByMonth.put("December", month[11]);
		}
		model.addAttribute("year", year);
		model.addAttribute("listYears", listYears);
		model.addAttribute("chartData", dataByMonth);
		return CHART_VACCINE;
	}

}
