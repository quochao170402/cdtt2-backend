package com.quochao.website.controller;

import com.quochao.website.dto.CustomerDto;
import com.quochao.website.entity.Order;
import com.quochao.website.entity.User;
import com.quochao.website.security.MyUserDetail;
import com.quochao.website.service.OrderService;
import com.quochao.website.service.UserService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@Data
@CrossOrigin("*")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> carts() {
        return ResponseEntity.ok(orderService.getCart());
    }

    @PostMapping
    public ResponseEntity<?> addToCart(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "quantity", defaultValue = "1") Integer quantity) {
        return ResponseEntity.ok(orderService.addToCart(code, quantity));
    }

    @PutMapping
    public ResponseEntity<?> updateQuantity(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "quantity") Integer quantity) {
        return ResponseEntity.ok(orderService.updateQuantity(code, quantity));
    }

    @DeleteMapping
    public ResponseEntity<?> removeItem(@RequestParam(name = "code") String code) {
        return ResponseEntity.ok(orderService.removeItem(code));
    }

    @PostMapping("/checkouts")
    public ResponseEntity<?> checkout(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(orderService.checkout(customerDto));
    }

    @GetMapping("/histories")
    public ResponseEntity<?> getOrderHistory(@RequestBody Long userId) {
        return ResponseEntity.ok(orderService.getOrderHistory(userId));
    }

    @DeleteMapping("/histories/{id}")
    public ResponseEntity<?> removeOrderHistory(@RequestBody Long userId,@PathVariable Long id) {
        User user = userService.getById(userId);
        return ResponseEntity.ok(orderService.removeOrderHistory(user,id));
    }
}
