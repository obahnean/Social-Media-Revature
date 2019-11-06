package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.models.User;
import com.service.UserService;

@CrossOrigin(origins="http://localhost:4200")
@Controller
//@RequestMapping(value="/Project2")
public class ProfileController {
	
	private UserService profileServ;
	
	@Autowired
	public ProfileController(UserService profileserv) {
		this.profileServ = profileserv;
	}
	
	/** Currently used for debugging
	 * 
	 * @return a json string of all profiles in the system
	 * @throws JsonProcessingException 
	 */
	@GetMapping(value="/getProfiles.MasterServlet")
	public @ResponseBody List<User> getProfiles() throws JsonProcessingException {
		List<User> users = profileServ.getUsers();
		System.out.println("Getting users");
		return users;
	}
	
//	@GetMapping(value="/lookupProfile.MasterServlet")
//	public @ResponseBody User getProfileById(HttpServletRequest request) {
//		int id = Integer.parseInt(request.getParameter("id"));
//		return profileServ.lookupUser(id);
//	}
}
