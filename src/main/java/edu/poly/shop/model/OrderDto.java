package edu.poly.shop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import edu.poly.shop.domain.Order;
import edu.poly.shop.domain.OrderDetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto implements Serializable {
private Long orderId;
@NotNull
private String orderDate;

@NotNull
private double amount;
@NotNull
private String status;
private Boolean isEdit = false;
private List<OrderDetaildto> orderDetails;
}
