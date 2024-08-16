package edu.poly.shop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "OrderDetail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private int orderDetailId;

    private Integer quantity;
    private Double unitPrice;
    private Boolean isEdit = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id") // Tên cột trong cơ sở dữ liệu phải khớp với tên cột trong `OrderDetail`
    @ToString.Exclude
    private Order order; // Thay đổi tên trường từ `orders` thành `order` để dễ hiểu hơn

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id") // Tên cột trong cơ sở dữ liệu phải khớp với tên cột trong `OrderDetail`
    @ToString.Exclude
    private Product product;

    // getters and setters
}
