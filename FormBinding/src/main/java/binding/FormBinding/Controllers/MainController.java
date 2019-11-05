package binding.FormBinding.Controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import binding.FormBinding.Dao.InsertEmployeeDaoImpl;
import binding.FormBinding.Model.Employee;

@Controller
public class MainController {

	@Autowired
	InsertEmployeeDaoImpl insertempdao;

	@RequestMapping("/")
	public String login(Model model) {
		model.addAttribute("employee", new Employee());
		return "login.jsp";
	}

	@PostMapping("/addEmployee")
	public ModelAndView addeduser(@Valid @ModelAttribute("employee") Employee employee, BindingResult result,
			@RequestParam("employer") String employer, ModelMap model) {

		if (result.hasErrors()) {
			System.out.println("Error occured in binding");
			return new ModelAndView();
		}
		System.out.println("----------------" + employer);

		insertempdao.InsertEmployee(employee);
		ModelAndView mv = new ModelAndView("userPage");
		mv.addObject(employee);
		mv.addObject("Employers", employer);
		return mv;
	}

}
