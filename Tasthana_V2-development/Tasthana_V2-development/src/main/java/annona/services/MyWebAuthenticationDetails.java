package annona.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;


public class MyWebAuthenticationDetails  {

    public MyWebAuthenticationDetails(HttpServletRequest context) {
        // the constructor of MyWebAuthenticationDetails can retrieve
        // all extra parameters given on a login form from the request
        // MyWebAuthenticationDetails is your LoginDetails class
    	String userName = context.getParameter("j_username");
    	String pwd = context.getParameter("j_password");
    	String organization = context.getParameter("j_Organization");
        //return new MyWebAuthenticationDetails(context);
    }
}