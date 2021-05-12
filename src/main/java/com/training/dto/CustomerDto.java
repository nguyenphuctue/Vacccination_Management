package com.training.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private long customerId;

    private String address;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    private String email;

    private String fullName;

    private boolean gender;

    private String identityCard;

    private String password;

    @Pattern(regexp = "(0)+([0-9]{9})", message = "Wrong phone!")
    private String phone;

    private String userName;

    private String captcha;

    private String code;

    private String confirmPassword;

}
