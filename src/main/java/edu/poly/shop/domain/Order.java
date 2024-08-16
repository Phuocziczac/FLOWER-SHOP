package edu.poly.shop.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    private String orderDate;
    private String address;
    private Double amount;

    @ManyToOne(fetch = FetchType.LAZY) // Sử dụng lazy loading để tránh tải dữ liệu không cần thiết
    @JoinColumn(name = "username", nullable = false)
    
    @ToString.Exclude
    private Account account;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private List<OrderDetail> orderDetails;
}
