package com.example.security.SecurityExample;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String Home() {
		return "Home.jsp";
	}

	@RequestMapping("/login")
	public String LoginPage() {
		return "Login.jsp";
	}

	@RequestMapping("/logout-success")
	public String LogoutPage() {
		return "Logout.jsp";
	}

	@RequestMapping("/user")
	@ResponseBody
	public Principal user(Principal principal) {
		return principal;
	}

}
