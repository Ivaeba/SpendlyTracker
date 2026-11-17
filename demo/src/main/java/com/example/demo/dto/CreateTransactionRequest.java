package com.example.demo.dto;

import com.example.demo.entity.TransactionType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateTransactionRequest(
        @NotBlank
        @Size(max = 100)
        String title,

        @NotNull
        @DecimalMin("0.01")
        @Digits(integer = 10, fraction = 2)
        BigDecimal amount,

        @NotNull
        @Positive
        Long categoryId,

        @NotNull
        LocalDate date,

        @NotNull
        TransactionType type
) {}

