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

import edu.poly.shop.service.CategoryService;
import edu.poly.shop.service.ProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/category")
public class CategoryRestController {
@Autowired
ProductService productService;
@Autowired
private CategoryService categoryService;
 @GetMapping("{id}")
 public Optional<Category>  getOne(@PathVariable("id") Long id) {
	 
     return categoryService.findById(id);
 }
 @PostMapping()
 public Optional<Category> create(@RequestBody Category category) {
	 
     return categoryService.create(category);
 }
 @PutMapping("{categoryId}")
 public ResponseEntity<Category> update(@PathVariable("categoryId") Long id,@RequestBody Category category) {
	 Category updatedCategory =categoryService.update(category);
	    if (updatedCategory!= null) {
	        return ResponseEntity. ok(updatedCategory);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
 }
 @DeleteMapping("{categoryId}")
 public void update(@PathVariable("categoryId") Long id) {
	 
    categoryService.deleteById(id);;
 }
 @GetMapping()
 public List<Category>  getALL() {
	 
     return categoryService.findAll();
 }




 
}
