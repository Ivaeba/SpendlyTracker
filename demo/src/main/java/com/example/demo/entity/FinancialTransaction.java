package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class FinancialTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 100)
    private String title;
    @Column(nullable = false,precision = 12,scale = 2)
    private BigDecimal amount;
    @Column(nullable = false)
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;
    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;
    protected FinancialTransaction() {
    }
    public FinancialTransaction( String title,BigDecimal amount,Category category,LocalDate date,TransactionType type){
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.type = type;
    }
    public String getTitle() {
        return title;
    }
    public Long getId() {
        return id;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public LocalDate getDate() {
        return date;
    }
    public TransactionType getType() {
        return type;
    }
    public Category getCategory() {
        return category;
    }
    public void update(String title, BigDecimal amount, Category category,
                       LocalDate date, TransactionType type) {
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.type = type;
    }
}