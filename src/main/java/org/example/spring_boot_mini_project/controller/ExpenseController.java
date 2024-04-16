package org.example.spring_boot_mini_project.controller;

import lombok.RequiredArgsConstructor;
import org.example.spring_boot_mini_project.model.Expense;
import org.example.spring_boot_mini_project.model.Request.ExpenseRequest;
import org.example.spring_boot_mini_project.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> addExpense(@Valid @RequestBody ExpenseRequest expenseRequest) {
        try {
            ExpenseRequest savedExpense = expenseService.createExpense(expenseRequest);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("message", "The Expense has been successfully added.");
            response.put("payload", savedExpense);
            response.put("status", "CREATED");
            response.put("time", LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
//    @PostMapping
//    public ResponseEntity<ExpenseRequest> createExpense(@Valid @RequestBody ExpenseRequest expenseRequest) {
//        ExpenseRequest createdExpense = expenseService.createExpense(expenseRequest);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdExpense);
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<ExpenseRequest> getExpenseById(@PathVariable UUID id) {
//        ExpenseRequest expenseDto = expenseService.getExpenseById(id);
//        return ResponseEntity.ok(expenseDto);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ExpenseRequest> updateExpense(@PathVariable UUID id, @Valid @RequestBody ExpenseRequest expenseDto) {
//        ExpenseRequest updatedExpense = expenseService.updateExpense(id, expenseDto);
//        return ResponseEntity.ok(updatedExpense);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteExpense(@PathVariable UUID id) {
//        expenseService.deleteExpense(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping
//    public ResponseEntity<List<ExpenseDto>> getAllExpenses() {
//        List<ExpenseDto> expenseDtos = expenseService.getAllExpenses();
//        return ResponseEntity.ok(expenseDtos);
//    }
}
