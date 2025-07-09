package com.codecampx.codecampx.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgetPasswordPasswordDto {

    @NotBlank(message = "Enter Password")
    @Size(min = 8,message = "Password Must Be 8 Character")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#?!@]).{8,}$",message = "Password Must Be Combination Of 8 Character and digit,uppercase,lowercase,special character")
    private String password;
}
