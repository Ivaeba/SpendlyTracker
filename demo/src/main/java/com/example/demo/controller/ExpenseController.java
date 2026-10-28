package com.example.demo.controller;

import com.example.demo.dto.CreateExpenseRequest;
import com.example.demo.entity.Expense;
import com.example.demo.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService service;
    public ExpenseController(ExpenseService service) {
        this.service = service;
    }
    @GetMapping
    public List<Expense> findAll(@RequestParam(required = false) Optional<Long> categoryId,@RequestParam Optional<String> sort) {
        if (categoryId.isEmpty() && sort.isEmpty()) {
            return service.findAll();
        }
        else if(categoryId.isEmpty() && sort.get().equals("amount")) {
            return service.findAllByAmountDesc();
        } else if(!categoryId.isEmpty() && sort.isEmpty()) {
            return service.findByCategory(categoryId.get());
        } else if (!categoryId.isEmpty() && sort.get().equals("amount")) {
            return service.findByCategoryAmount(categoryId.get());
        } else if (categoryId.isEmpty() && sort.get().equals("date")) {
            return service.findAll();
        } else if (!categoryId.isEmpty() && sort.get().equals("date")) {
            return service.findByCategory(categoryId.get());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unsupported sort value"
            );
        }
    }
    @GetMapping("/{id}")
    public Expense findById(@PathVariable Long id) {
        return service.findById(id);
    }
    @GetMapping("/sort/amount")
    public List<Expense> findAllByAmountDesc(){
        return service.findAllByAmountDesc();
    }
    @GetMapping("/page")
    public Page<Expense> findPage(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size,@RequestParam(defaultValue = "date") String sort,@RequestParam Optional<Long> categoryId,@RequestParam(defaultValue = "desc") String direction) {
        if(!categoryId.isEmpty()){
            return service.findPageByCategory(categoryId.get(), page, size, sort,direction);
        }
            return service.findPage(page, size , sort , direction);
    }

    @GetMapping("/search")
    public List<Expense> searchByTitle(@RequestParam String title){
        return service.searchByTitle(title);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Expense create(
            @Valid @RequestBody CreateExpenseRequest request
    ) {
        return service.create(request);
    }
    @PutMapping("/{id}")
    public Expense update(
            @PathVariable Long id,
            @Valid @RequestBody CreateExpenseRequest request
    ) {
        return service.update(id, request);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
