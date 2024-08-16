package edu.poly.shop.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.poly.shop.domain.Role;
import edu.poly.shop.service.RoleSevice;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/roles")
public class RoleRestController {
@Autowired
RoleSevice roleService;
	
@GetMapping()
public List<Role> getall() {
	return roleService.findAll();
}
}
