package annona.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.LocaleUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.theme.CookieThemeResolver;

import annona.dao.EndUserDAO;
import annona.dao.LogDetailsDAO;
import annona.dao.ModeOfPaymentDAO;
import annona.domain.EndUser;
import annona.domain.ModeOfPayment;
import annona.domain.Role;
import annona.exception.CustomException;
import annona.services.DateService;

@Controller
@RequestMapping("/main")
public class AuthController {

	@Autowired
	EndUserDAO endUserDAO;

	@Autowired
	LogDetailsDAO logDetailsDAO;
	
	@Autowired
	ModeOfPaymentDAO mopDAO;

	private CookieLocaleResolver localeResolver;
	private CookieThemeResolver themeResolver;

	static Logger log = Logger.getLogger(AdminController.class.getName());

	@RequestMapping(value = "/default", method = RequestMethod.GET)
	public ModelAndView getDefaultPage(Model model, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes attributes) throws CustomException {
		EndUser endUser = endUserDAO.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
				.getSingleResult();

		if (endUser.getTheme() != null) {
			themeResolver = new CookieThemeResolver();
			themeResolver.setThemeName(request, response, endUser.getTheme());
		}

		if (endUser.getPrefferedLanguage() != null) {

			log.info("Changing to default language " + endUser.getPrefferedLanguage());
			localeResolver = new CookieLocaleResolver();
			String defaultLocale = endUser.getPrefferedLanguage();
			Locale currentLanguage = LocaleUtils.toLocale(defaultLocale);
			localeResolver.setLocale(request, response, currentLanguage);
		}
		if ((endUser.getPasswordFlag() == 0) || (endUser.getPwdExpiryDate() != null
				&& endUser.getPwdExpiryDate().compareTo(Calendar.getInstance().getTime()) < 0)) {

			attributes.addFlashAttribute("error", "Password expired!!Please change the password");
			return new ModelAndView("redirect:/auth/loginChangePassword?id=" + endUser.getId());

		} else {

			DateService.loginDate = logDetailsDAO.findLoginDate();
			System.out.println("LoginDate: " + DateService.loginDate);
			System.out.println("CurrentDate: " + DateService.getCurrentDate());
			
			
			//Insert The Default MOPs in the database table
			//---------------------------------------------
			if((mopDAO.getAllModeOfPaymentDetails() == null) || (mopDAO.getAllModeOfPaymentDetails()!=null && mopDAO.getAllModeOfPaymentDetails().size()==0))
			{
				 //Format: paymentMode:IsVisibleInBankSide:IsVisibleInCustomerSide:IsLinkedWithCASA
				 String strArray[] = {"Cash:1:0:0", "Cheque:1:0:0", "DD:1:0:0", "Net Banking:0:1:0", "Fund Transfer:1:1:1", "Debit Card:0:1:1", "Credit Card:0:1:0", "Internal Transfer:0:0:0"};
		          			              
				 for (int i = 0; i < strArray.length; i++) { 
					 
					ModeOfPayment modeOfPayment = new ModeOfPayment();
					String mop = strArray[i];
				
					modeOfPayment.setPaymentMode(mop.substring(0, mop.indexOf(":")));
					String strAferPaymentMode = mop.substring(mop.indexOf(":")+1);
					modeOfPayment.setIsVisibleInBankSide(strAferPaymentMode.substring(0, strAferPaymentMode.indexOf(":")));
					
					String strAferVisibilityInBank = strAferPaymentMode.substring(strAferPaymentMode.indexOf(":")+1);
					modeOfPayment.setIsVisibleInCustomerSide(strAferVisibilityInBank.substring(0, strAferVisibilityInBank.indexOf(":")));
					 
					String strAfterVisibilityInCustomer = strAferVisibilityInBank.substring(strAferVisibilityInBank.indexOf(":")+1);
					modeOfPayment.setIsLinkedWithCASAAccount(Integer.parseInt(strAfterVisibilityInCustomer));
					 
					mopDAO.save(modeOfPayment);
					
				 } 
			}
			//------------------------------------------------

			if (endUser.getCurrentRole() != null) {
				/*
				 * if (request.isUserInRole("ROLE_ADMIN")) { return new
				 * ModelAndView("redirect:/admin/adminPage"); } else if
				 * (request.isUserInRole("ROLE_VP")) { return new
				 * ModelAndView("redirect:/vp/vpPage"); } else if
				 * (request.isUserInRole("ROLE_BANKEMP")) { return new
				 * ModelAndView("redirect:/bnkEmp/dashboard"); } else if
				 * (request.isUserInRole("ROLE_APPROVALMNG")) { return new
				 * ModelAndView("redirect:/appMng/apprMng"); } else { return new
				 * ModelAndView("redirect:/bnkEmp/dashboard"); }
				 */

			}
			@SuppressWarnings("unused")
			List<Role> roles = endUser.getRoles();
			String ROLE;
			if (roles.size() > 1) {
				model.addAttribute("roles", roles);
				model.addAttribute("endUser", endUser);
				return new ModelAndView("selectLoggedInRole", "model", model);
			} else {
				ROLE = roles.get(0).getRole();
			}
			if (ROLE.equals("ROLE_ADMIN")) {
				return new ModelAndView("redirect:/admin/adminPage");
			} else if (ROLE.equals("ROLE_BANKEMP")) {
				return new ModelAndView("redirect:/bnkEmp/dashboard");
			} else if (ROLE.equals("ROLE_VP")) {
				return new ModelAndView("redirect:/vp/vpPage");
			} else if (ROLE.equals("ROLE_APPROVALMNG")) {
				return new ModelAndView("redirect:/appMng/apprMng");
			} else {
				// return new ModelAndView("redirect:/users/user");
				System.setProperty("loggedInRoleId", roles.get(0).getId().toString());
				attributes.addAttribute("roleId", roles.get(0).getId());
				attributes.addFlashAttribute("roleId", roles.get(0).getId());
				return new ModelAndView("redirect:/common/dashboard");
			}

		}
	}

	@RequestMapping(value = "selectLoggedInRole", method = RequestMethod.GET)
	private ModelAndView selectLoggedInRole(Model model, HttpServletRequest request, String role,
			RedirectAttributes attributes, HttpServletResponse response) {
		EndUser endUser = endUserDAO.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
				.getSingleResult();
		if (endUser.getTheme() != null) {
			themeResolver = new CookieThemeResolver();
			themeResolver.setThemeName(request, response, endUser.getTheme());
		}
		if (endUser.getPrefferedLanguage() != null) {
			log.info("Changing to default language " + endUser.getPrefferedLanguage());
			localeResolver = new CookieLocaleResolver();
			String defaultLocale = endUser.getPrefferedLanguage();
			Locale currentLanguage = LocaleUtils.toLocale(defaultLocale);
			localeResolver.setLocale(request, response, currentLanguage);
		}
		if ((endUser.getPasswordFlag() == 0) || (endUser.getPwdExpiryDate() != null
				&& endUser.getPwdExpiryDate().compareTo(Calendar.getInstance().getTime()) < 0)) {
			attributes.addFlashAttribute("error", "Password expired!!Please change the password");
			return new ModelAndView("redirect:/auth/loginChangePassword?id=" + endUser.getId());

		} else {

			DateService.loginDate = logDetailsDAO.findLoginDate();
			System.out.println("LoginDate: " + DateService.loginDate);
			System.out.println("CurrentDate: " + DateService.getCurrentDate());
			List<GrantedAuthority> updatedAuthorities = new ArrayList<>();
			for (Role role_ : endUser.getRoles()) {
				if (role_.getRole().equals(role))
					updatedAuthorities.add(new SimpleGrantedAuthority(role_.getRole()));
			}
			Authentication newAuth = new UsernamePasswordAuthenticationToken(
					SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
					SecurityContextHolder.getContext().getAuthentication().getCredentials(), updatedAuthorities);
			SecurityContextHolder.getContext().setAuthentication(newAuth);
			endUser.setCurrentRole(role);
			System.setProperty("loggedInRoleId", role);
			
			if (request.isUserInRole("ROLE_ADMIN")) {
				return new ModelAndView("redirect:/admin/adminPage");
			} else if (request.isUserInRole("ROLE_VP")) {
				return new ModelAndView("redirect:/vp/vpPage");
			} else if (request.isUserInRole("ROLE_BANKEMP")) {
				return new ModelAndView("redirect:/bnkEmp/dashboard");
			} else if (request.isUserInRole("ROLE_APPROVALMNG")) {
				return new ModelAndView("redirect:/appMng/apprMng");
			} else {
				
				// model.addAttribute("information", "New dashboard coming soon !");
				// model.addAttribute("roles", endUser.getRoles());
				// model.addAttribute("endUser", endUser);
				// return new ModelAndView("selectLoggedInRole", "model", model);
				//return new ModelAndView("redirect:/bnkEmp/dashboard");
				
				attributes.addAttribute("roleId", role);
				return new ModelAndView("redirect:/common/dashboard");
			}

		}

	}

}
