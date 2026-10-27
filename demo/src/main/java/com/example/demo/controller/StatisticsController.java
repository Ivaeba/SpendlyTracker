package com.example.demo.controller;

import com.example.demo.repository.ExpenseRepository;
import com.example.demo.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;
import java.util.List;
import java.time.YearMonth;
import java.util.Optional;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    private final StatisticsService service;

    public StatisticsController(StatisticsService service) {
        this.service = service;
    }

    @GetMapping("/total")
    public BigDecimal getTotal(){
        return service.getTotal();
    }
    @GetMapping("/monthly")
    public BigDecimal getMonthly(@RequestParam Optional<Integer> year,@RequestParam Optional<Integer> month){
        int selectedYear;
        int selectedMonth;
        if(year.isEmpty()){
            selectedYear =  YearMonth.now().getYear();
        }
        else {
            selectedYear = year.get();
        }
        if(month.isEmpty()){
            selectedMonth =  YearMonth.now().getMonthValue();
        }
        else {
            selectedMonth = month.get();
        }
        return service.getMonthly(selectedYear,selectedMonth);
    }
    @GetMapping("/categories")
    public List<ExpenseRepository.CategoryAmountProjection> getByCategory(){
        return service.getByCategory();
    }



}