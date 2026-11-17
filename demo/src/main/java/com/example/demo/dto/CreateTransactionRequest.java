package com.example.demo.dto;

import com.example.demo.entity.TransactionType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateTransactionRequest(
        @NotNull(groups = {New.class,UpdateTransaction.class})
        @Size(max = 100)
        String title,
        @NotNull(groups = {New.class})
        @Null(groups = {UpdateTransaction.class})
        @DecimalMin(value = "0.01")
        @Digits(integer = 10, fraction = 2)
        BigDecimal amount,
        @Null(groups = {New.class})
        @NotNull(groups = {UpdateTransaction.class})
        @Positive
        Long categoryId,
        @NotNull(groups = {New.class})
        @Null(groups = {UpdateTransaction.class})
        LocalDate date,
        @NotNull(groups = {New.class})
        @Null(groups = {UpdateTransaction.class})
        TransactionType type){
        public interface New{

        }
        public interface Exists{

        }
        public interface UpdateTransaction extends Exists{

        }

}

