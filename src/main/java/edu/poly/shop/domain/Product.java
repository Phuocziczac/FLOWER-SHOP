package edu.poly.shop.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Products")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private int productID;

    @Column(name = "ProductName")
    private String productName;

    @Column(name = "CategoryID")
    private Long categoryId;

    @Column(name = "UnitPrice")
    private double unitPrice;

    @Column(name = "Quantity")
    private int quantity;

    @Column(name = "Image")
    private String image;


   
    @Column(name = "EnteredDate")
    private String enteredDate;

   
    @Column(name = "status")
    public String status;
    private Boolean isEdit = false;
    @ManyToOne
    @JoinColumn(name = "CategoryID", insertable = false, updatable = false)
    @ToString.Exclude
    private Category category;

    @OneToMany
    @JsonIgnore
    @JoinColumn(name = "ProductID")
    private List<OrderDetail> orderDetails;


    // Getters and setters
}