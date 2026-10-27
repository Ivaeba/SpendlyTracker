package com.example.demo.service;

import com.example.demo.repository.ExpenseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.YearMonth;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;

@Service
public class StatisticsService {
    private final ExpenseRepository repository;

    public StatisticsService(ExpenseRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public BigDecimal getTotal() {
        return repository.calculateTotalAmount();
    }
    @Transactional(readOnly = true)
    public BigDecimal getMonthly(int year,int month){
        if (month < 1 || month > 12){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Month must be between 1 and 12");
        }
        if(year < 1 || year > 9999){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"year is wrong!");
        }
        YearMonth date = YearMonth.of(year, month);
        LocalDate firstDay = date.atDay(1);
        LocalDate firstDayNextMonth = date.plusMonths(1).atDay(1);
        return  repository.calculateTotalBetweenDates(firstDay,firstDayNextMonth);
    }
    @Transactional(readOnly = true)
    public List<ExpenseRepository.CategoryAmountProjection> getByCategory(){
        return repository.calculateTotalsByCategory();
    }

}