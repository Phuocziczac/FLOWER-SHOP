	package edu.poly.shop.service;
	
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
	import java.util.stream.Collectors;
	
	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.context.annotation.Bean;
	import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
	import org.springframework.security.core.Authentication;
	import org.springframework.security.core.GrantedAuthority;
	import org.springframework.security.core.authority.SimpleGrantedAuthority;
	import org.springframework.security.core.context.SecurityContextHolder;
	import org.springframework.security.core.userdetails.User;
	import org.springframework.security.core.userdetails.UserDetails;
	import org.springframework.security.core.userdetails.UserDetailsService;
	import org.springframework.security.core.userdetails.UsernameNotFoundException;
	import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
	import org.springframework.security.crypto.password.PasswordEncoder;
	import org.springframework.stereotype.Service;



import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
	
	import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Authority;
import edu.poly.shop.domain.Role;
import edu.poly.shop.repository.AccountRepository;
	
	@Service
	public class CustomUserDetailsService implements UserDetailsService {

	    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	    @Autowired
	    private AccountRepository accountRepository;
	    @Autowired
	    private AccountService accountService;
	    @Autowired
	    UploadService uploadService;

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        logger.info("Đang cố gắng tải người dùng theo tên người dùng: {}", username);

	        Account account = accountRepository.findById(username)
	                .orElseThrow(() -> {
	                    logger.error("Người dùng không tồn tại: {}", username);
	                    return new UsernameNotFoundException("Người dùng không tồn tại");
	                });

	        logger.info("Đã tìm thấy người dùng: {}", account);

	        List<GrantedAuthority> authorities = account.getAuthorities().stream()
	                .map(auth -> new SimpleGrantedAuthority(auth.getRole().getName()))
	                .collect(Collectors.toList());

	        authorities.forEach(authority -> logger.info("Vai trò: {}", authority.getAuthority()));

	        return User.builder()
	                .username(account.getUsername())
	                .password(account.getPassword())
	                .authorities(authorities)
	                .build();
	    }

	    public void loginFromOAuth2(OAuth2AuthenticationToken oauth2) {
	        String registrationId = oauth2.getAuthorizedClientRegistrationId(); // Lấy ID của nhà cung cấp (google, facebook, etc.)
	    
	    

	        // Lấy các thuộc tính từ OAuth2User dựa trên nhà cung cấp (Google hoặc Facebook)
	        if (registrationId.equals("google")) {
	        	  String   username = oauth2.getPrincipal().getAttribute("name"); // Google ID
	        	  String   email = oauth2.getPrincipal().getAttribute("email");
	          String  fullname = oauth2.getPrincipal().getAttribute("name");
	          String  image = oauth2.getPrincipal().getAttribute("picture"); 
	            // Kiểm tra tài khoản đã tồn tại chưa
		        Account account = accountRepository.findById(username).orElseGet(() -> {
		            // Nếu account không tồn tại, tạo mới
		            Account newAccount = new Account();
		            newAccount.setUsername(username);
		            newAccount.setFullname(fullname);
		            newAccount.setEmail(email);
		            
		            // Download and save the image
		            String imageName = username + ".jpg"; // Customize the naming convention
		            try {
		                // Create directory if it doesn't exist
		                Path uploadDir = Paths.get("H:\\Springbootws\\FLOWER-SHOP-main\\src\\main\\webapp\\images\\uploads\\images");
		                if (Files.notExists(uploadDir)) {
		                    Files.createDirectories(uploadDir);
		                }

		                // Download and save image
		                InputStream in = new URL(image).openStream();
		                Path imagePath = uploadDir.resolve(imageName);
		                Files.copy(in, imagePath, StandardCopyOption.REPLACE_EXISTING);
		                in.close();

		                newAccount.setImage(imageName); 
		                System.out.println("Saving image to: " + imagePath.toString());
		            } catch (Exception e) {
		                throw new RuntimeException("Failed to download or save image", e);
		            }

		            newAccount.setPassword(passwordEncoder().encode(Long.toHexString(System.currentTimeMillis()))); // Mã hóa mật khẩu
		            return accountService.save(newAccount); // Lưu vào cơ sở dữ liệu
		            
		        });

		        // Tạo UserDetails từ tài khoản
		        UserDetails user = User.withUsername(account.getUsername())
		                .password(account.getPassword())
		                .roles("CUSTOMER")
		                .build();

		        // Xác thực người dùng
		        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		        SecurityContextHolder.getContext().setAuthentication(auth);

		        System.out.println("OAuth2 user logged in: " + username);
	        } else if (registrationId.equals("facebook")) {
	       String     username = oauth2.getPrincipal().getAttribute("id"); // Facebook ID
	        String    email = oauth2.getPrincipal().getAttribute("email"); // Có thể bị null nếu không yêu cầu trong scope
	     String       fullname = oauth2.getPrincipal().getAttribute("name");
	      String      image = "https://graph.facebook.com/" + username + "/picture?type=large"; 
	            // Kiểm tra tài khoản đã tồn tại chưa
		        Account account = accountRepository.findById(username).orElseGet(() -> {
		            // Nếu account không tồn tại, tạo mới
		            Account newAccount = new Account();
		            newAccount.setUsername(username);
		            newAccount.setFullname(fullname);
		            newAccount.setEmail(email);
		            
		            // Download and save the image
		            String imageName = username + ".jpg"; // Customize the naming convention
		            try {
		                // Create directory if it doesn't exist
		                Path uploadDir = Paths.get("H:\\Springbootws\\FLOWER-SHOP-main\\src\\main\\webapp\\images\\uploads\\images");
		                if (Files.notExists(uploadDir)) {
		                    Files.createDirectories(uploadDir);
		                }

		                // Download and save image
		                InputStream in = new URL(image).openStream();
		                Path imagePath = uploadDir.resolve(imageName);
		                Files.copy(in, imagePath, StandardCopyOption.REPLACE_EXISTING);
		                in.close();

		                newAccount.setImage(imageName); 
		                System.out.println("Saving image to: " + imagePath.toString());
		            } catch (Exception e) {
		                throw new RuntimeException("Failed to download or save image", e);
		            }

		            newAccount.setPassword(passwordEncoder().encode(Long.toHexString(System.currentTimeMillis()))); // Mã hóa mật khẩu
		            return accountService.save(newAccount); // Lưu vào cơ sở dữ liệu
		        });// URL hình ảnh của Facebook

		        // Tạo UserDetails từ tài khoản
		        UserDetails user = User.withUsername(account.getUsername())
		                .password(account.getPassword())
		                .roles("CUSTOMER")
		                .build();

		        // Xác thực người dùng
		        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		        SecurityContextHolder.getContext().setAuthentication(auth);

		        System.out.println("OAuth2 user logged in: " + username);
	        }

	      
	      

	    }


	}

