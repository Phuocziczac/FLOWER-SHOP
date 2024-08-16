package edu.poly.shop.interceptor;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


import edu.poly.shop.domain.Account;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private edu.poly.shop.service.SessionService sessionService;

    @Autowired
    private edu.poly.shop.service.AccountService accountService;

    @Override
  
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String username = getLoggedInUser();
        if (username != null) {
            Optional<Account> userOpt = accountService.findById(username);
            if (userOpt.isPresent()) {
                Account user = userOpt.get();
                sessionService.set("account", user.getUsername());
                sessionService.set("imgurl", user.getImage());
                request.setAttribute("account", user.getUsername());
                request.setAttribute("isLogin", true);
                request.setAttribute("imgurl", user.getImage());
            } else {
                request.setAttribute("isLogin", false);
            }
        } else {
            request.setAttribute("isLogin", false);
        }
        return true;
    }

    public String getLoggedInUser() {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            } else {
                return principal.toString();
            }
        }
        return null;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            modelAndView.addObject("isLogin", request.getAttribute("isLogin"));
            modelAndView.addObject("account", request.getAttribute("account"));
          
        }
    }
}