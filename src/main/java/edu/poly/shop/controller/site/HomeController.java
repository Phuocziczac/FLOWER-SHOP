package edu.poly.shop.controller.site;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Category;

import edu.poly.shop.domain.Order;
import edu.poly.shop.domain.OrderDetail;
import edu.poly.shop.domain.Product;
import edu.poly.shop.model.CartItem;
import edu.poly.shop.model.CategoryDto;

import edu.poly.shop.model.OrderDto;
import edu.poly.shop.model.ProductDto;
import edu.poly.shop.service.AccountService;
import edu.poly.shop.service.CategoryService;

import edu.poly.shop.service.OrderDetailService;
import edu.poly.shop.service.OrderService;
import edu.poly.shop.service.ParamService;
import edu.poly.shop.service.ProductService;
import edu.poly.shop.service.SessionService;


@Controller
@RequestMapping("/site")
public class HomeController {
	@Autowired
	private ProductService productService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	ParamService paramService;
	@Autowired
	OrderService orderService;

	@Autowired
	OrderDetailService detailService;
	@Autowired
	CategoryService categoryService;

	

	@GetMapping("/product")
	public String home(Model model) {
		 org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String username = authentication.getName();
	        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	        Boolean isAdmin ;
	        model.addAttribute("username", username);
	        model.addAttribute("roles", authorities);
	       
       return "forward:/";
	}

	@RequestMapping("/default/homeadmin")
	public String admin() {
		return "site/default/homeadmin";
	}

	@RequestMapping("/aboutus")
	public String aboutus() {
		return "site/aboutus";
	}


	@RequestMapping("/order")
	public String order(Model model) {
	
		List<Order> listorder = orderService.findAll();
		model.addAttribute("orders", listorder);
		return "site/order";
	}

	@RequestMapping("/detailproduct/{productID}")
	public String detail(Model model, @PathVariable("productID") int productID) {
		
		Optional<Product> opt = productService.findById(productID);
		ProductDto dto = new ProductDto();
		if (opt.isPresent()) {
			Product entity = opt.get();

			BeanUtils.copyProperties(entity, dto);
			System.out.println();
			dto.setImgurl(entity.getImage());
		
			List<Product> proByCate = productService.findByCategoryId(dto.getCategoryId());
		
			model.addAttribute("proByCate",proByCate);
			model.addAttribute("product", dto);
			return "site/detailproduct";
		}
		
		model.addAttribute("message", "Product is not existed");
		return "forward:/site/product";
	}

	@Autowired
	SessionService service;

	
	@PostMapping("/search")
	public String searchProducts(@RequestParam("keyword") String keyword, @RequestParam(required = false) Long category,
			Model model, @RequestParam(defaultValue = "0") int page) {
				Pageable pageable = PageRequest.of(page, 10);
		List<Category> categoryid = categoryService.findAll();
		model.addAttribute("categories", categoryid);
		Page<Product> productPage = null;
		String status = "AVAILABLE";
			 productPage = productService.searchProducts(keyword, category, pageable);
		
			
			

		
		model.addAttribute("productPage", productPage);
		return "site/product";
	}

}
