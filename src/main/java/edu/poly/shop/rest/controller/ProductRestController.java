package edu.poly.shop.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.poly.shop.domain.Category;
import edu.poly.shop.domain.Product;
import edu.poly.shop.service.CategoryService;
import edu.poly.shop.service.ProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/products")
public class ProductRestController {
@Autowired
ProductService productService;
@Autowired
private CategoryService categoryService;
 @GetMapping("{id}")
 public Optional<Product>  getOne(@PathVariable("id") Integer id) {
	 
     return productService.findById(id);
 }
 @PostMapping()
 public Optional<Product> create(@RequestBody Product product) {
	 
     return productService.create(product);
 }
 @PutMapping("{productID}")
 public ResponseEntity<Product> update(@PathVariable("productID") Integer id,@RequestBody Product product) {
	 Product updatedProduct = productService.update(product);
	    if (updatedProduct != null) {
	        return ResponseEntity.ok(updatedProduct);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
 }
 @DeleteMapping("{productID}")
 public void update(@PathVariable("productID") Integer id) {
	 
    productService.deleteById(id);
 }
 @GetMapping()
 public List<Product>  getALL() {
	 
     return productService.findAll();
 }



 @GetMapping("/categories")
 public List<Category> getAllCategories() {
     return categoryService.findAll();
 }
 
}
