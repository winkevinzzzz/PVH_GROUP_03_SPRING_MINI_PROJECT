package org.example.spring_boot_mini_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import lombok.RequiredArgsConstructor;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.example.spring_boot_mini_project.model.Expense;
import org.example.spring_boot_mini_project.model.dto.request.ExpenseRequest;
import org.example.spring_boot_mini_project.service.ExpenseService;

@RestController
@RequestMapping("/api/v1/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

//    private String getUsernameOfCurrentUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return authentication.getName();
//    }

    @PostMapping
    @Operation(summary = "Add New Expense")
    public ResponseEntity<Map<String, Object>> createExpense(@Valid @RequestBody ExpenseRequest expenseRequest) {
        try {
            Expense savedExpense = expenseService.createExpense(expenseRequest);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("message", "The Expense has been successfully added.");
            response.put("payload", savedExpense);
            response.put("status", "CREATED");
            response.put("time", LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }   catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExpenseById(@PathVariable Long id) {
        Expense expense = expenseService.getExpenseById(id);
        try {
            if (expense != null) {

//                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//                String email =authentication.getName();
//                User user = userService.findUserByEmail();
                Map<String, Object> response = new LinkedHashMap<>();
                response.put("message", "The expense has been successfully found.");
                response.put("payload", expense);
                response.put("status", "OK");
                response.put("time", LocalDateTime.now());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateExpense(@PathVariable Long id, @Valid @RequestBody ExpenseRequest expenseRequest) {
//        try {
//            Expense updatedExpense = expenseService.updateExpense(id, expenseRequest);
//            if (updatedExpense != null) {
//                Map<String, Object> response = new LinkedHashMap<>();
//                response.put("message", "The expense has been successfully updated.");
//                response.put("payload", updatedExpense);
//                response.put("status", "OK");
//                response.put("time", LocalDateTime.now());
//                return ResponseEntity.ok(response);
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateEvent(@PathVariable Long id, @Valid @RequestBody Expense expense) {
        try {
            expense.setExpense_id(id);
            Expense updatedEvent = expenseService.updateExpense(expense);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("message", "The expense has been successfully updated.");
            response.put("payload", updatedEvent);
            response.put("status", "OK");
            response.put("time", LocalDateTime.now());

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Expense By ID")

    public ResponseEntity<Map<String, Object>> deleteExpense(@PathVariable Long id) {
        try {
            expenseService.deleteExpense(id);
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("message", "The expense has been successfully deleted.");
            response.put("status", "OK");
            response.put("time", LocalDateTime.now());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    @Operation(summary = "Get All Expense")
    public ResponseEntity<Map<String, Object>> getAllExpense(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        try {
            List<Expense> expenses = expenseService.getAllExpense(offset, limit, sortBy, ascending);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("message", "All expenses have been successfully fetched.");
            response.put("payload", expenses);
            response.put("status", "OK");
            response.put("time", LocalDateTime.now());
            response.put("offset", offset);
            response.put("limit", limit);
            response.put("sortBy", sortBy);
            response.put("ascending", ascending);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // Handle invalid request parameters
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




    private Map<String, Object> buildSuccessResponse(String message, Object payload) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", message);
        response.put("payload", payload);
        response.put("status", "OK");
        response.put("time", LocalDateTime.now());
        return response;
    }

}
