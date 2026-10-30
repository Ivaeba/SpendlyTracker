package com.example.demo.service;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.demo.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository repository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ExpenseService service;
        @Test
        void findPage_shouldThrowExceptionWhenPageIsNegative () {
            ResponseStatusException excection = assertThrows(ResponseStatusException.class, () -> service.findPage(-1, 5, "date", "desc"));
            assertThat(excection.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            verifyNoInteractions(repository);
        }
    @Test
    void findPage_shouldThrowExceptionWhenSizeIsTooLarge() {

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> service.findPage(0, 101, "date", "desc")
        );

        assertThat(exception.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);

        verifyNoInteractions(repository);
    }
    @Test
    void findPage_shouldThrowExceptionWhenSortIsInvalid() {
            ResponseStatusException excection = assertThrows(ResponseStatusException.class, () -> service.findPage(0, 5, "price", "desc"));
            assertThat(excection.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            verifyNoInteractions(repository);
    }
    @Test
    void findPage_shouldThrowExceptionWhenDirectionIsInvalid() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> service.findPage(0, 5, "date", "wrong"));
        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        verifyNoInteractions(repository);
    }
    @Test
    void findPage_shouldSortByAmountAscending() {
            Page<Expense> expectedPage = new PageImpl<>(List.of());
            when(repository.findAll(any(Pageable.class))).thenReturn(expectedPage);
            Page<Expense> result = service.findPage(0,5,"amount","asc");
            assertThat(result).isSameAs(expectedPage);
            verify(repository).findAll(PageRequest.of(0, 5, Sort.by("amount").ascending()));

        }
    @Test
    void findPage_shouldSortByAmountDescending() {
        Page<Expense> expectedPage = new PageImpl<>(List.of());
        when(repository.findAll(any(Pageable.class))).thenReturn(expectedPage);
        Page<Expense> result = service.findPage(0,5,"amount","desc");
        assertThat(result).isSameAs(expectedPage);
        verify(repository).findAll(PageRequest.of(0, 5, Sort.by("amount").descending()));

    }
    }
