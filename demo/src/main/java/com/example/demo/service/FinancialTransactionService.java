package com.example.demo.service;

import com.example.demo.entity.Category;
import com.example.demo.entity.FinancialTransaction;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FinancialTransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FinancialTransactionService {
    private final FinancialTransactionRepository financialTransactionRepository;
    private final CategoryRepository categoryRepository;
    public FinancialTransactionService(FinancialTransactionRepository financialTransactionRepository, CategoryRepository categoryRepository) {
        this.financialTransactionRepository = financialTransactionRepository;
        this.categoryRepository = categoryRepository;
    }
    @Transactional(readOnly = true)
    public List<FinancialTransaction> findAll() {
        return financialTransactionRepository.findAllByOrderByDateDesc();
    }
    private Category findCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not FOUND"));
    }
}
