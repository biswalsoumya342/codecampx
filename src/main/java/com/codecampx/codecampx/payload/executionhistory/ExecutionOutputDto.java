package com.codecampx.codecampx.payload.executionhistory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ExecutionOutputDto {

    private String message;

    private String output;

    private boolean status;
}
