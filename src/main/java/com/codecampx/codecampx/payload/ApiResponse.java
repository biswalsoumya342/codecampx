package com.codecampx.codecampx.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    private LocalDateTime time;

    private String message;

    private Boolean status;
}
