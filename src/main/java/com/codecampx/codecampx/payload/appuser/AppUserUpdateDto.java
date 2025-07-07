package com.codecampx.codecampx.payload.appuser;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserUpdateDto {

    @Size(min = 6,message = "UserName Must be 6 Character Long")
    @NotBlank(message = "UserName Must Required")
    private  String userName;

    @Email(message = "Enter A Valid Email")
    @NotBlank(message = "Enter Your Email")
    private String email;
}
