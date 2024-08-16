package edu.poly.shop.controller.site;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.poly.shop.domain.Product;
import edu.poly.shop.model.CartItem;
import edu.poly.shop.service.ProductService;
import edu.poly.shop.service.SessionService;

@Controller
@RequestMapping("/site")
public class CartController {

	@RequestMapping("/cart/view")
	public String view() {
		return "site/cartview";
	}
}
