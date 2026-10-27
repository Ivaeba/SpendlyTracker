package com.example.demo.service;

import com.example.demo.dto.CreateExpenseRequest;
import com.example.demo.entity.Category;
import com.example.demo.entity.Expense;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ExpenseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository repository;
    private final CategoryRepository categoryRepository;

    public ExpenseService(
            ExpenseRepository repository,
            CategoryRepository categoryRepository
    ) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<Expense> findAll() {
        return repository.findAllByOrderByDateDesc();
    }
    @Transactional(readOnly = true)
    public List<Expense> findByCategory(Long categoryId){
        return repository.findByCategory_IdOrderByDateDesc(categoryId);
    }
    @Transactional(readOnly = true)
    public List<Expense> findAllByAmountDesc(){
        return repository.findAllByOrderByAmountDesc();
    }
    @Transactional(readOnly = true)
    public List<Expense> findByCategoryAmount(Long categoryId){
        return repository.findByCategory_IdOrderByAmountDesc(categoryId);
    }
    @Transactional(readOnly = true)
    public Expense findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Expense not found"
                ));
    }
    @Transactional(readOnly = true)
    public Page<Expense> findPage(int page, int size){
        if(page < 0 || size < 1 || size > 100){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad request!");
        }
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date"));
        return repository.findAll(pageable);
    }
    @Transactional
    public Expense create(CreateExpenseRequest request) {
        Category category = findCategory(request.categoryId());

        Expense expense = new Expense(
                request.title(),
                request.amount(),
                category,
                request.date()
        );

        return repository.save(expense);
    }

    @Transactional
    public Expense update(Long id, CreateExpenseRequest request) {
        Expense expense = findById(id);
        Category category = findCategory(request.categoryId());

        expense.update(
                request.title(),
                request.amount(),
                category,
                request.date()
        );

        return repository.save(expense);
    }

    @Transactional
    public void delete(Long id) {
        Expense expense = findById(id);
        repository.delete(expense);
    }

    private Category findCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category not found"
                ));
    }
}