package annona.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import annona.dao.EndUserDAO;
import annona.domain.Branch;
import annona.domain.EndUser;
import annona.domain.ManageMenuPreference;
import annona.domain.Menu;
import annona.domain.Role;
import annona.exception.CustomException;

@ControllerAdvice
public class GlobalExceptionController {
	
	@Autowired
	EndUserDAO endUserDAO;
//	
//	@Autowired
//	EndUserForm endUserForm;
	

	
	/*@Autowired
	Newclient  newclient;*/
	
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionController.class.getClass());
	
    @RequestMapping(value = "/{type:.+}", method = RequestMethod.GET)
	public ModelAndView getPages(@PathVariable("type") String type)
		throws Exception {

	  if ("error".equals(type)) {
		// go handleCustomException
		throw new CustomException("errorMessage", "Check in CustomGenericException.class");
		  } else if ("io-error".equals(type)) {
				// go handleAllException
				throw new IOException();
			  } 
	  else {
		return new ModelAndView("index").addObject("msg", type);
	  }

	}
 
	@ExceptionHandler(CustomException.class)
	public ModelAndView handleCustomException(CustomException ex) {

		// ModelAndView model = new ModelAndView("generic_error");
		// model.addObject("errorCode", ex.getErrorCode());
		// model.addObject("errorMessage", ex.getErrorMessage());
		ModelAndView model = null;
		logger.error("Exceptions occured:: " + ex);
		logger.error("Exceptions occured: " + ex);

		StackTraceElement[] elements = ex.getStackTrace();
		
		// Get the current Role
		String loggedInRole = System.getProperty("loggedInRoleId");
		List<Menu> menus = this.getCurrentRoleMenus(Long.parseLong(loggedInRole));
		
		for (int iterator = elements.length - 1; iterator > 0; iterator--) {
			if (elements[iterator - 1].getFileName().equalsIgnoreCase("CommonController.java")) {
				model = new ModelAndView("generic_error_BankEmp");
				model.addObject("menus", menus);
				//model.getModelMap().addAttribute("menus", menus);
				model.addObject("errorMessage", ex.getMessage()==null? ex.getErrorMessage(): ex.getMessage());
				model.addObject("stackTrace", ex.getStackTrace());
				
				model.addObject("controller", "common");
				break;
			} else if (elements[iterator - 1].getFileName().equalsIgnoreCase("CustomerController.java")) {
				model = new ModelAndView("generic_error_Customer");
			//	model.addObject("menus", menus);
				model.addObject("errorMessage", ex.getMessage()==null? ex.getErrorMessage(): ex.getMessage());
				model.addObject("stackTrace", ex.getStackTrace());
				model.addObject("controller", "Customer");
				break;
			} else if (elements[iterator - 1].getFileName().equalsIgnoreCase("AdminController.java")) {
				model = new ModelAndView("generic_error_Admin");
				//model.addObject("menus", menus);
				model.addObject("errorMessage", ex.getMessage()==null? ex.getErrorMessage(): ex.getMessage());
				model.addObject("stackTrace", ex.getStackTrace());
				model.addObject("controller", "Admin");
				break;
			} 
			// } else {
			// model = new ModelAndView("generic_error_Common");
			// model.addObject("errorMessage", ex.getMessage());
			// model.addObject("stackTrace", ex.getStackTrace());
			// model.addObject("controller", "Common");
			// break;
			// }
		}
		if (model == null) {
			model.addObject("errorMessage", ex.getMessage()==null? ex.getErrorMessage(): ex.getMessage());
			model.addObject("stackTrace", ex.getStackTrace());
			//model.addObject("menus", ex.getMenus());
			model.addObject("controller", "common");
		}

		return model;

	}
	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(Exception ex) {

		ModelAndView model = null;
		logger.error("Exceptions occured: " + ex);

		StackTraceElement[] elements = ex.getStackTrace();

		for (int iterator = elements.length - 1; iterator > 0; iterator--) {
			if (elements[iterator - 1].getFileName().equalsIgnoreCase("BankEmpController.java")) {
				model = new ModelAndView("generic_error_BankEmp");
				model.addObject("errorMessage", ex.getMessage());
				model.addObject("stackTrace", ex.getStackTrace());
				model.addObject("controller", "BankEmployee");
				break;
			} else if (elements[iterator - 1].getFileName().equalsIgnoreCase("CustomerController.java")) {
				model = new ModelAndView("generic_error_Customer");
				model.addObject("errorMessage", ex.getMessage());
				model.addObject("stackTrace", ex.getStackTrace());
				model.addObject("controller", "Customer");
				break;
			} else if (elements[iterator - 1].getFileName().equalsIgnoreCase("AdminController.java")) {
				model = new ModelAndView("generic_error_Admin");
				model.addObject("errorMessage", ex.getMessage());
				model.addObject("stackTrace", ex.getStackTrace());
				model.addObject("controller", "Admin");
				break;
			} else if (elements[iterator - 1].getFileName().equalsIgnoreCase("ApprovalMngController.java")) {
				model = new ModelAndView("generic_error_ApprovalManager");
				model.addObject("errorMessage", ex.getMessage());
				model.addObject("stackTrace", ex.getStackTrace());
				model.addObject("controller", "ApprovalManager");
				break;
			}
			// else
			// {
			// model = new ModelAndView("generic_error_Common");
			// model.addObject("errorMessage", ex.getMessage());
			// model.addObject("stackTrace", ex.getStackTrace());
			// model.addObject("controller", "Common");
			// break;
			// }
		}

		if (model == null) {
			model.addObject("errorMessage", ex.getMessage());
			model.addObject("stackTrace", ex.getStackTrace());
			model.addObject("controller", "BankEmployee");
		}

		return model;

	}
	
	
	private List<Menu> getCurrentRoleMenus(Long roleId) {
		
		List<Menu> menus = endUserDAO.findByRoleId(roleId);
		Menu sortedMenuList[] = new Menu[menus.size()];
		for (int i = 0; i < menus.size(); i++) {
			if (menus.get(i).getMenu() == null) {
				ManageMenuPreference manageMenuPreferences = endUserDAO
						.findByMenuIdAndRoleIdManageMenuPreference(menus.get(i).getId(), roleId);
				if (manageMenuPreferences != null) {
					Menu menuOrdered = menus.get(i);
					if (menuOrdered.getChildMenuItems().size() > 0) {
						for (int subMenuIndex = 0; subMenuIndex < manageMenuPreferences.getSubMenuId()
								.size(); subMenuIndex++) {
							Menu subMenu = endUserDAO
									.findBySubMenuId(menuOrdered.getChildMenuItems().get(subMenuIndex).getId());
							menuOrdered.getChildMenuItems().set(subMenuIndex, subMenu);
						}

					}

					sortedMenuList[manageMenuPreferences.getMenuPrefrenceIndex()] = menus.get(i);
				} else {
					
					return menus;
				}

			}
		}
		if (sortedMenuList.length > 0) {
			List<Menu> menuPreference = Arrays.asList(sortedMenuList);

			return menuPreference;
		} else {
			return menus;
		}


	}
}
