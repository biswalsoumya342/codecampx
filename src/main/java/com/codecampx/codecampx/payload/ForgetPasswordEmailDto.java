package com.codecampx.codecampx.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgetPasswordEmailDto {

    @Email(message = "Enter Valid Email")
    @NotBlank(message = "Enter Your Email")
    private String email;
}
