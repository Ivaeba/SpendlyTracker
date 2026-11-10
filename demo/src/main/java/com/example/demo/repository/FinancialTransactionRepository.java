package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.FinancialTransaction;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, Long> {
    List<FinancialTransaction> findAllByOrderByDateDesc();
}