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
@RequestMapping("/rest/accounts")
public class AccountRestController {
@Autowired
AccountService accountService;
@GetMapping()
public List<Account> getAccounts(@RequestParam("admin") Optional<Boolean> admin){
	if(admin.orElse(false)) {
		return accountService.getAdministrators();
	}
	System.out.println("ssss"+accountService.findAll());
	return accountService.findAll();
}

	
}
