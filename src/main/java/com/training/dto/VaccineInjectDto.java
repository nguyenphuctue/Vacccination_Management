package com.training.dto;

import com.training.model.VaccineType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccineInjectDto {

    private String vaccineId;
    private String vaccineName;
    private VaccineType vaccineType;

}
