package edu.poly.shop.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Authority;
import edu.poly.shop.service.AccountService;
import edu.poly.shop.service.AuthorityService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/authorities")
public class AuthorityRestController {
	@Autowired
	AuthorityService authorityService;
	@Autowired
	AccountService accountService;

	@GetMapping()
	public List<Authority> findAll(@RequestParam("admin") Optional<Boolean> admin) {

		if (admin.orElse(false)) {
			return authorityService.findAuthoritiesOfAdministrators();
		}
		return authorityService.findAll();
	}
	@PostMapping("/account")
	public List<Authority> findIdbyUser(@RequestBody String username) {
	    return authorityService.findRoleByUser(username);
	}


	@PostMapping()
	public Authority post(@RequestBody Authority auth) {
		return authorityService.create(auth);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer id) {
		authorityService.deleteById(id);
	}

}
