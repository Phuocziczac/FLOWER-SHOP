package edu.poly.shop.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import edu.poly.shop.domain.Order;
import edu.poly.shop.service.OrderService;
import edu.poly.shop.service.ProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/orders")
public class OrderRestcontroller {
@Autowired
OrderService orderService;

@PostMapping()
public ResponseEntity<Order> create(@RequestBody JsonNode orderData) {
    try {
        Order createdOrder = orderService.create(orderData);
        return ResponseEntity.ok(createdOrder); // Trả về đơn hàng vừa được tạo
    } catch (Exception e) {
        e.printStackTrace(); // In lỗi ra console
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Trả về mã lỗi 500 nếu có lỗi
    }
}

}
	

