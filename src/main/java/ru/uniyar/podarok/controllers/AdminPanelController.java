package ru.uniyar.podarok.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.uniyar.podarok.dtos.OrderDataDto;
import ru.uniyar.podarok.exceptions.OrderNotFoundException;
import ru.uniyar.podarok.services.AdminService;

@RestController
@AllArgsConstructor
public class AdminPanelController {
    private AdminService adminService;
    @GetMapping("/getOrders")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getOrders(@RequestParam String status) {
        return ResponseEntity.ok(adminService.getOrders(status));
    }

    @PutMapping("/changeOrderStatus")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> changeOrderStatus(@RequestBody OrderDataDto orderDataDto) {
        try {
            adminService.changeOrderStatus(orderDataDto);
            return ResponseEntity.ok().body("Статус заказа успешно изменён!");
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteGift")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteGift(@RequestParam Long id) {
        try {
            adminService.deleteGift(id);
            return ResponseEntity.ok("Подарок успешно удалён!");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
