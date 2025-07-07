package com.codecampx.codecampx.payload.appuser;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserSignUpDto {

    @Size(min = 6,message = "UserName Must be 6 Character Long")
    @NotBlank(message = "UserName Must Required")
    private  String userName;

    @Email(message = "Enter A Valid Email")
    @NotBlank(message = "Enter Your Email")
    private String email;

    @NotBlank(message = "Enter Password")
    @Size(min = 8,message = "Password Must Be 8 Character")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#?!@]).{8,}$",message = "Password Must Be Combination Of 8 Character and digit,uppercase,lowercase,special character")
    private String password;

}
