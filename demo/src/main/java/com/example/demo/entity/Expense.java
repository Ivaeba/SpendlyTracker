package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @Column(nullable = false)
    private LocalDate date;
    protected Expense() {
    }
    public Expense(
            String title,
            BigDecimal amount,
            Category category,
            LocalDate date
    ) {
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }
    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public Category getCategory() {
        return category;
    }
    public LocalDate getDate() {
        return date;
    }
    public void update(
            String title,
            BigDecimal amount,
            Category category,
            LocalDate date
    ) {
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }
}
