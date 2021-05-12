package com.training.utils;

import com.training.model.Vaccine;
import com.training.model.VaccineType;
import com.training.service.VaccineTypeService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Component
public class ExcelHelper {

    public static String EXCELTYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static VaccineTypeService vaccineTypeService;

    @Autowired
    public ExcelHelper(VaccineTypeService vaccineTypeService) {
        ExcelHelper.vaccineTypeService = vaccineTypeService;
    }

    public static boolean hasExcelFormat(MultipartFile file) {
        if (!EXCELTYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<Vaccine> excelVaccines(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet("Sheet1");
            Iterator<Row> rows = sheet.iterator();
            List<Vaccine> vaccines = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Vaccine vaccine = new Vaccine();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    System.out.println("Current cell " + cellIdx+ ": " + currentCell.toString() + " - " + currentCell.getCellType());
                    switch (cellIdx) {
                        case 0:
                            String vaccineId = null;
                            if (currentCell.getCellType() == CellType.STRING) {
                                vaccineId = currentCell.getStringCellValue();
                            } else if (currentCell.getCellType() == CellType.NUMERIC) {
                                vaccineId = String.valueOf((int) currentCell.getNumericCellValue());
                            }
                            vaccine.setVaccineId(vaccineId);
                            break;
                        case 1:
                            boolean active = currentCell.getNumericCellValue() != 0;
                            vaccine.setActive(active);
                            break;
                        case 2:
                            vaccine.setContraindication(currentCell.getStringCellValue());
                            break;
                        case 3:
                            vaccine.setIndication(currentCell.getStringCellValue());
                            break;
                        case 4:
                            vaccine.setNumberOfInjection(currentCell.getColumnIndex());
                            break;
                        case 5:
                            vaccine.setOrigin(currentCell.getStringCellValue());
                            break;
                        case 6:
                            vaccine.setTimeBeginNextInjection(currentCell.getDateCellValue());
                            break;
                        case 7:
                            vaccine.setTimeEndNextInjection(currentCell.getDateCellValue());
                            break;
                        case 8:
                            vaccine.setUsage(currentCell.getStringCellValue());
                            break;
                        case 9:
                            vaccine.setVaccineName(currentCell.getStringCellValue());
                            break;
                        case 10:
                            String id = null;
                            if (currentCell.getCellType() == CellType.STRING) {
                                id = currentCell.getStringCellValue();
                            } else if (currentCell.getCellType() == CellType.NUMERIC) {
                                id = String.valueOf((int) currentCell.getNumericCellValue());
                            }
                            Optional<VaccineType> optionalVaccineType = vaccineTypeService.findByVcId(id);
                            optionalVaccineType.ifPresent(vaccine::setVaccineType);
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                System.out.println(vaccine);
                vaccines.add(vaccine);
            }
            workbook.close();
            return vaccines;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

}
