package com.example.jwtdemo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.jwtdemo.model.Players;
import com.example.jwtdemo.model.Role;
import com.example.jwtdemo.repository.RoleRepository;
import com.example.jwtdemo.service.PlayersService;
import com.example.jwtdemo.service.RolesResponsibilitiesService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	RolesResponsibilitiesService rolesResponsibilitiesService;
	
	/*
	 * @PostMapping public ResponseEntity<?> addRole(@RequestBody Role role){ return
	 * ResponseEntity.ok(roleRepository.save(role)); }
	 */
	
	@PostMapping("/addRole")
	public ModelAndView addRole(@Valid Role role,BindingResult bindingResult) {
		System.err.println(":::Admin.addRole::::::");
		ModelAndView modelAndView =new ModelAndView();
		System.err.println(":::Admin.addRole::::::");
		if(role.getRole()!=null) {
			Role data=roleRepository.findByRole(role.getRole());
			System.out.println("Role::::::"+data);
			if(data!=null) {
				bindingResult.rejectValue("role","error.role","Role already exists");
			}
			if(bindingResult.hasErrors()) {
				modelAndView.setViewName("/roles/addRole");
		} else {
			role.setActive(true);
			roleRepository.save(role);
			modelAndView.addObject("roleList", roleRepository.findAllRole());
			modelAndView.setViewName("roles/rolelist");
			}
			return modelAndView;
	}else {
		modelAndView.setViewName("admin/403");
		return modelAndView;
	}
	}
	@GetMapping("/addRole")
	public ModelAndView addRole() {
		ModelAndView modelAndView = new ModelAndView();
			String rolename="";
		if (rolename.equals("")) {
			Role role = new Role();
			modelAndView.addObject("role", role);
			modelAndView.setViewName("roles/addRole");
			return modelAndView;
		} else {
			modelAndView.setViewName("admin/403");
			return modelAndView;
		}
	}
	
	@GetMapping("/viewRoles")
	public String showRoles(Model model, HttpServletRequest request) {
		System.err.println("::: AdminController.showRoles :::");
	List<Role> roledata=roleRepository.findAllRole();
		if (!roledata.isEmpty()) {
			model.addAttribute("roleList", roledata);
			return "roles/rolelist";
		} else {
			return "index";
		}
	}

	@GetMapping({ "/getRoleById/{id}" })
	public ModelAndView editRole(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView();
		System.err.println("::: AdminController.getRoleById :::");
		Role roleId=rolesResponsibilitiesService.getRoleById(id);
		if (roleId!=null) {
			modelAndView.addObject("role",roleId);
			modelAndView.setViewName("roles/updateRole");
			return modelAndView;
		} else {
			modelAndView.setViewName("admin/403");
			return modelAndView;
		}
	}
	
	@GetMapping(value = "/updateRoleStatus/{id}")
	@ResponseBody
	public String updateRoleStatus(@PathVariable("id") Long id) {
		System.err.println("::: AdminController.updateRoleStatus :::");
		Role role=roleRepository.findById(id).orElse(null);
		if(role!=null) {
			if(role.isActive()) 
				role.setActive(false);
			 else 
				role.setActive(true);
				roleRepository.save(role);
	}
		return "Role Status Updated";
	}
	
	@PostMapping("/updateRole")
	public String updateRole(Role role) {
		System.out.println(":::::AdminController.updateRole");
		if(role!=null) {
			rolesResponsibilitiesService.updateRole(role);
			return "redirect:/admin/viewRoles";
	}else {
		return "admin/403";
	}
	}
	
}
