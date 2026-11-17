package com.example.demo.controller;

import com.example.demo.dto.CreateTransactionRequest;
import com.example.demo.entity.FinancialTransaction;
import com.example.demo.service.FinancialTransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finances")
public class FinancialTransactionController {
    private final FinancialTransactionService service;


    public FinancialTransactionController(FinancialTransactionService service) {
        this.service = service;
    }
    @GetMapping("/transactions")
    public List<FinancialTransaction> findAllByOrderByDateDesc(){ return service.findAll();}
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public FinancialTransaction create(@Validated(CreateTransactionRequest.New.class) @RequestBody CreateTransactionRequest dto){
        return service.create(dto);
    }
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FinancialTransaction updateTransaction(@Validated(CreateTransactionRequest.UpdateTransaction.class) @RequestBody CreateTransactionRequest dto,@PathVariable Long id){
        return service.update(id,dto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}

