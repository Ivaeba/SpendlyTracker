package com.example.demo.service;

import com.example.demo.dto.CreateTransactionRequest;
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
    private final FinancialTransactionRepository financialRepository;
    private final CategoryRepository categoryRepository;
    public FinancialTransactionService(FinancialTransactionRepository financialRepository, CategoryRepository categoryRepository) {
        this.financialRepository = financialRepository;
        this.categoryRepository = categoryRepository;
    }
    @Transactional(readOnly = true)
    public List<FinancialTransaction> findAll() {
        return financialRepository.findAllByOrderByDateDesc();
    }
    private Category findCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not FOUND"));
    }
    public FinancialTransaction findById(Long id){
        return financialRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"ID not found!"));
    }
    @Transactional
    public FinancialTransaction create(CreateTransactionRequest request) {
        Category category = findCategory(request.categoryId());

        FinancialTransaction transaction = new FinancialTransaction(
                request.title(),
                request.amount(),
                category,
                request.date(),
                request.type()
        );

        return financialRepository.save(transaction);
    }
    @Transactional
    public FinancialTransaction update(Long id,CreateTransactionRequest request){
        FinancialTransaction updatedTransaction = financialRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Transaction not found"
                ));
            Category category = findCategory(request.categoryId());
            updatedTransaction.update(
                    request.title(),
                    request.amount(),
                    category,
                    request.date(),
                    request.type()
            );

        return financialRepository.save(updatedTransaction);
    }
    @Transactional
    public void delete(Long id){
        FinancialTransaction deletedTransaction = financialRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found"));
        financialRepository.delete(deletedTransaction);
    }

}
