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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Category;
import edu.poly.shop.service.AccountService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/profile")
public class profileRestController {
@Autowired
AccountService accountService;


@GetMapping("{id}")
public Optional<Account>  getOne(@PathVariable("id") String id) {
	 
    return accountService.findById(id);
}
@PostMapping()
public Optional<Account> create(@RequestBody Account account) {
	 
    return accountService.create(account);
}
@PutMapping("{username}")
public ResponseEntity<Account> update(@PathVariable("username") String id,@RequestBody Account account) {
	 Account updatedAccount =accountService.update(account);
	    if (updatedAccount!= null) {
	        return ResponseEntity. ok(updatedAccount);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
}
@DeleteMapping("{username}")
public void update(@PathVariable("username") String id) {
	 
   accountService.deleteById(id);;
}
@GetMapping()
public List<Account>  getALL() {
	 
    return accountService.findAll();
}
	
}
