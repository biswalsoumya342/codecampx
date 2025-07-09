package com.codecampx.codecampx.payload.executionhistory;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionInputDto {

    @NotBlank(message = "Select Language")
    private  String language;

    @NotBlank(message = "Write Code For Execution")
    private String code;
}
