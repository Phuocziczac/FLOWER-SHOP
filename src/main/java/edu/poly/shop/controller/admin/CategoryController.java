package edu.poly.shop.controller.admin;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.servlet.ModelAndView;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Category;

import edu.poly.shop.model.CategoryDto;
import edu.poly.shop.service.AccountService;
import edu.poly.shop.service.CategoryService;
import edu.poly.shop.service.SessionService;
import edu.poly.shop.service.impl.CategoryServiceImpl;


@Controller

@RequestMapping("admin/categories")
public class CategoryController {
	@Autowired
	CategoryService categoryServiceImpl;
	@Autowired
	SessionService sessionService;
	@Autowired
	AccountService accountService;

	@GetMapping("/add")
	public String add(Model model) {
		
		model.addAttribute("category", new Category());
		return "admin/categories/addOrEdit";
	}

	@GetMapping("/edit/{categoryId}")
	public ModelAndView edit(ModelMap model, @PathVariable("categoryId") Long categoryId) {
		
		Optional<Category> opt = categoryServiceImpl.findById(categoryId);
		CategoryDto dto = new CategoryDto();
		if (opt.isPresent()) {
			Category entity = opt.get();
			BeanUtils.copyProperties(entity, dto);
			dto.setIsEdit(true);

			model.addAttribute("category", dto);
			return new ModelAndView("admin/categories/addOrEdit", model);
		}
		model.addAttribute("message", "Category is not existed");
		return new ModelAndView("redirect:/admin/categories", model);
	}

	@GetMapping("delete/{categoryId}")
	public ModelAndView delete(ModelMap model, @PathVariable("categoryId") Long categoryId) {
	
		categoryServiceImpl.deleteById(categoryId);
		model.addAttribute("message", "Category is deleted");

		return new ModelAndView("forward:/admin/categories/search", model);
	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("category") CategoryDto dto,
			BindingResult result) {
	
		if (result.hasErrors()) {

			return new ModelAndView("admin/categories/addOrEdit");
		}
		Category entity = new Category();
		BeanUtils.copyProperties(dto, entity);

		categoryServiceImpl.save(entity);

		model.addAttribute("message", "Category is save");
		return new ModelAndView("redirect:/admin/categories/searchpaginated", model);
	}

	@RequestMapping("")
	public String list(ModelMap model) {

		List<Category> list = categoryServiceImpl.findAll();

		model.addAttribute("categories", list);
		return "admin/categories/list";
	}

	@GetMapping("search")
	public String search(ModelMap model, @RequestParam(name = "name", required = false) String name) {
		
		List<Category> list = null;
		if (StringUtils.hasText(name)) {
			list = categoryServiceImpl.findByNameContaining(name);
		} else {
			list = categoryServiceImpl.findAll();
		}
		model.addAttribute("categories", list);
		return "admin/categories/search";
	}

	@GetMapping("searchpaginated")
	
	public String search(ModelMap model,
	                     @RequestParam(name = "name", required = false) String name,
	                     @RequestParam("p") Optional<Integer> p) {
		String username = sessionService.get("account");
		if (username != null) {
			Optional<Account> userOpt = accountService.findById(username);
			if (userOpt.isPresent()) {
				Account user = userOpt.get();
				boolean isLogin = true;
				model.addAttribute("account", user.getUsername());
				model.addAttribute("isLogin", isLogin);
				
			}
		}
	    Pageable pageable = PageRequest.of(p.orElse(0), 5);
	    Page<Category> page = categoryServiceImpl.findAll(pageable);
	    model.addAttribute("page", page);

	    Page<Category> resultPage;
	    if (StringUtils.hasText(name)) {
	        resultPage = categoryServiceImpl.findByNameContaining(name, pageable);
	        model.addAttribute("name", name);
	    } else {
	        resultPage = categoryServiceImpl.findAll(pageable);
	    }
	    model.addAttribute("categoryPage", resultPage);

	    return "admin/categories/searchpaginated";
	}

}
