package edu.poly.shop.controller.site;

import java.io.File;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Category;
import edu.poly.shop.model.AccountDto;
import edu.poly.shop.model.CategoryDto;
import edu.poly.shop.service.AccountService;
import edu.poly.shop.service.CustomUserDetailsService;
import edu.poly.shop.service.MailerService;
import edu.poly.shop.service.ParamService;
import edu.poly.shop.service.SessionService;
import groovy.cli.Option;


@Controller
@RequestMapping("/site")
public class AccountController {
	@Autowired
	private AccountService accountService;
	@Autowired
	private MailerService mailerService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private ParamService paramService;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@RequestMapping("/account/login")
	public String login(Model model) {
	    
	    return "site/account/login";
	}

	 @Autowired
	    private PasswordEncoder passwordEncoder;

	    @GetMapping("/account/Register")
	    public String registerForm(Model model) {
	        model.addAttribute("account", new AccountDto());
	        return "site/account/Register";
	    }

	    @PostMapping("/account/Register/add")
	    public String register(@ModelAttribute("account") @javax.validation.Valid AccountDto accountDto, BindingResult result, Model model) {
	        if (result.hasErrors()) {
	            return "site/account/Register";
	        }

	        Account account = new Account();
	        BeanUtils.copyProperties(accountDto, account);
	        account.setUsername(accountDto.getUsername());
	        account.setPassword(passwordEncoder.encode(accountDto.getPassword())); // Mã hóa mật khẩu
	        accountService.save(account);

	        model.addAttribute("message", "Account registered successfully");
	        return "redirect:/site/account/login"; // Chuyển hướng đến trang đăng nhập sau khi đăng ký thành công
	    }
	    @PostMapping("/account/login")
	    public String login(@RequestParam String username, @RequestParam String password, Model model) {
	        Optional<Account> accountOpt = accountService.findById(username);
	        if (accountOpt.isEmpty()) {
	            model.addAttribute("error", "Invalid username or password");
	            return "site/account/login";
	        }

	        Account account = accountOpt.get();
	        if (!passwordEncoder.matches(password, account.getPassword())) {
	            model.addAttribute("error", "Invalid username or password");
	            return "site/account/login";
	        }

	        // Xử lý đăng nhập thành công, lưu thông tin người dùng vào session hoặc thực hiện các thao tác khác
	        sessionService.set("account", account.getUsername());
	        return "redirect:/site/home"; // Chuyển hướng đến trang chủ hoặc trang khác sau khi đăng nhập thành công
	    }


	@RequestMapping("/account/changepassword")
	public String changepassword(Model model) {

		return "site/account/changepassword";
	}

	@RequestMapping("/account/fogotpassword")
	public String fogotpassword(Model model) {

		return "site/account/fogotpassword";
	}

	

	

	@PostMapping("/account/fogotpassword")
	public String sendPass(@RequestParam("username") String username, @RequestParam("email") String to, Model model) {
		Optional<Account> user = accountService.findById(username);
		if (user.isEmpty()) {
			model.addAttribute("error", "Account not found");
			return "site/account/fogotpassword";
		}
		mailerService.sendSimpleEmail(to, "Mail fogot", "Mật khẩu của bạn là:" +user.get().getPassword());
		model.addAttribute("message", "Zo check mail di ma con gui roi do ");
		return "site/account/fogotpassword";
	}

	@PostMapping("/account/changepassword")
	public String changepass(@ModelAttribute("newPassword") String newpassword,
			@ModelAttribute("confirmPassword") String confirmpassword,
			@ModelAttribute("oldPassword") String oldPassword, Model model) {
		String username = sessionService.get("account");

		// Retrieve the user from the database using the username
		Optional<Account> optionalUser = accountService.findById(username);

		Account user = optionalUser.get();

		if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
			model.addAttribute("error", "Old password is incorrect");

		} else {
			if (!newpassword.equals(confirmpassword)) {
				model.addAttribute("error", "New password and confirm password do not match");
				return "site/account/changepassword";
			} else {
				user.setPassword(passwordEncoder.encode(newpassword));
				accountService.save(user);
				model.addAttribute("message", "Password changed successfully");
			}
		}

		return "site/account/changepassword";
	}

	@RequestMapping("/oauth2/login/success")
	public String success(OAuth2AuthenticationToken oauth2) {
	    Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
	    
	    if (currentAuth instanceof OAuth2AuthenticationToken) {
	        customUserDetailsService.loginFromOAuth2(oauth2);
	        return "redirect:/site/product"; // Điều hướng về trang chủ sau khi đăng nhập thành công
	    } else {
	        // Xử lý lỗi hoặc điều hướng nếu không phải OAuth2AuthenticationToken
	        throw new IllegalStateException("Current user principal is not of type OAuth2AuthenticationToken");
	    }
	}




}
