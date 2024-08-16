package edu.poly.shop.controller.site;

import java.util.Collection;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.shop.domain.Order;
import edu.poly.shop.service.OrderDetailService;
import edu.poly.shop.service.OrderService;
import edu.poly.shop.service.impl.OrderDetailServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/site")
public class SiteOrderController {
@Autowired
OrderService orderService;
@Autowired
OrderDetailService detailService; 
	@RequestMapping("/order/checkout")
	public String checkout(Model model) {
		 org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String username = authentication.getName();
	        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

	        model.addAttribute("username", username);
	        model.addAttribute("roles", authorities);
		return "site/order/checkout";
	}	
	  @RequestMapping("/order/list")
	    public String list(
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "5") int size,
	            Model model, 
	            HttpServletRequest request) {

	        String username = request.getRemoteUser();
	        Pageable pageable = PageRequest.of(page, size, Sort.by("orderDate").descending());
	        Page<Order> ordersPage = orderService.findByUsername(username, pageable);

	        model.addAttribute("ordersPage", ordersPage);
	        return "site/order/list";
	    }
	@RequestMapping("/order/detail/{orderId}")
	public String detail(@PathVariable("orderId")Long id,Model model) {

		model.addAttribute("order",orderService.findById(id));
		model.addAttribute("orderDetails",detailService.findByOrderId(id));
		System.out.print(detailService.findByOrderId(id));

		return "site/order/detail";
	}
}
