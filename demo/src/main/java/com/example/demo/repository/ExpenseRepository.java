package com.example.demo.repository;

import com.example.demo.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository
        extends JpaRepository<Expense, Long> {
    @Query("""
        SELECT COALESCE(SUM(e.amount), 0)
        FROM Expense e
        """)
    BigDecimal calculateTotalAmount();
    @Query("""
        SELECT COALESCE(SUM(e.amount), 0)
        FROM Expense e
        WHERE e.date >= :startDate
          AND e.date < :endDate
        """)
    BigDecimal calculateTotalBetweenDates(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
    interface CategoryAmountProjection {
        Long getCategoryId();
        String getCategoryName();
        BigDecimal getTotalAmount();
    }
    @Query("""
        SELECT e.category.id AS categoryId,
               e.category.name AS categoryName,
               SUM(e.amount) AS totalAmount
        FROM Expense e
        GROUP BY e.category.id, e.category.name
        ORDER BY SUM(e.amount) DESC
        """)
    List<CategoryAmountProjection> calculateTotalsByCategory();
    List<Expense> findByCategory_IdOrderByDateDesc(Long categoryId);
    List<Expense> findAllByOrderByDateDesc();
    List<Expense> findAllByOrderByAmountDesc();
    List<Expense> findByCategory_IdOrderByAmountDesc(Long categoryId);
    Page<Expense> findByCategory_Id(Long categoryId, Pageable pageable);
    List<Expense> findByTitleContainingIgnoreCase(String title);
    Page<Expense> findByTitleContainingIgnoreCase(
            String title,
            Pageable pageable
    );
    Page<Expense> findByTitleContainingIgnoreCaseAndCategory_Id(String title, Long categoryId,Pageable pageable);
}