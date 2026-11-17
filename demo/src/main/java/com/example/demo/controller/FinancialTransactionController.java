package com.example.demo.controller;

import com.example.demo.dto.CreateTransactionRequest;
import com.example.demo.entity.FinancialTransaction;
import com.example.demo.service.FinancialTransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class FinancialTransactionController {
    private final FinancialTransactionService service;


    public FinancialTransactionController(FinancialTransactionService service) {
        this.service = service;
    }
    @GetMapping("/")
    public List<FinancialTransaction> findAllByOrderByDateDesc(){ return service.findAll();}
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public FinancialTransaction create(@Valid @RequestBody CreateTransactionRequest dto){
        return service.create(dto);
    }
    @PutMapping("/{id}")
    public FinancialTransaction updateTransaction(@Valid @RequestBody CreateTransactionRequest dto, @PathVariable Long id){
        return service.update(id,dto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}

