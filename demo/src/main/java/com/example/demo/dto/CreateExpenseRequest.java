package com.example.demo.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateExpenseRequest(
        @NotBlank
        @Size(max = 100)
        String title,

        @NotNull
        @DecimalMin(value = "0.01")
        @Digits(integer = 10, fraction = 2)
        BigDecimal amount,

        @NotNull
        @Positive
        Long categoryId,

        @NotNull
        LocalDate date
) {
}