package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.models.User;
import com.service.MailService;
import com.service.UserService;
import com.util.OpResult;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class UserController {

	private UserService userServ;
	private MailService mail;

	@Autowired
	public UserController(UserService userServ,  MailService mail) {
		this.userServ = userServ;
		this.mail = mail;
	}

	@PostMapping(value = "/login.MasterServlet")
	public @ResponseBody User doLogin(HttpServletRequest request, HttpSession session) throws JsonProcessingException {
		String uname = request.getParameter("username");
		String pword = request.getParameter("password");
		System.out.println(request);
		System.out.println("Attempting login with cred:");
		System.out.println(uname);
		System.out.println(pword);

		return userServ.verifyLogin(session, uname, pword);
	}

	@GetMapping(value = "/testSession.MasterServlet")
	public @ResponseBody OpResult testSession(HttpSession request) {
		User user = (User) request.getAttribute("currentUser");
		System.out.println("Checking for current user session");
		System.out.println(user);
		return new OpResult(true);
	}

	/**
	 * Check for active user in session
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/getSessionUser.MasterServlet")
	public @ResponseBody User getUserFromSession(HttpServletRequest request) {
		return (User) request.getSession().getAttribute("currentUser");
	}

	@PostMapping(value = "/logout.MasterServlet")
	public @ResponseBody OpResult doLogout(HttpServletRequest request) throws JsonProcessingException {
		request.getSession().invalidate();
		return new OpResult(true);
	}

	/**
	 * Create a user object to be passed to hibernate
	 * 
	 * @param request
	 * @return
	 * @throws JsonProcessingException
	 */
	@PostMapping(value = "/register.MasterServlet")
	public @ResponseBody OpResult doRegister(HttpServletRequest request) throws JsonProcessingException {
		String uname = request.getParameter("username");
		String pword = request.getParameter("password");
		String fname = request.getParameter("firstname");
		String lname = request.getParameter("lastname");
		String email = request.getParameter("email");
		String pic = request.getParameter("picture");
		System.out.println(uname);
		System.out.println(pword);
		System.out.println(fname);
		System.out.println(lname);
		System.out.println(email);
		System.out.println(pic);

		User user = new User(uname, pword, fname, lname, email);
		user.setPicture(pic);
		// Attempt insert
		return (userServ.registerUser(user));
	}
	
	/**
	 * Updating Profile
	 * @param request
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/updateProfile.MasterServlet", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody User doUpdateProfile(HttpServletRequest request) throws JsonProcessingException {
		String uname = request.getParameter("username");
		String fname = request.getParameter("firstName");
		String lname = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		System.out.println("In controller user");
		System.out.println(uname);
		System.out.println(fname);
		System.out.println(lname);
		System.out.println(email);

		return userServ.updateProfile(request, uname, fname, lname, email);
	}
		/**
		 * Getting one user
		 * @param request
		 * @return user JSON
		 * @throws JsonProcessingException
		 */
	@RequestMapping(value = "/userJSON.MasterServlet", produces = "application/json", method = RequestMethod.GET)
	public @ResponseBody User doUserJSON(HttpServletRequest request) throws JsonProcessingException {
		System.out.println("In User JSON");
		System.out.println(userServ.getProfile(request));
		
		return userServ.getProfile(request);
	}

	/**
	 * Getting all users
	 * @param request
	 * @return User List JSON
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/getAllUsers.MasterServlet", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody List<User> doGetAll(HttpServletRequest request) throws JsonProcessingException {
		System.out.println("In Get all User JSON");
		return userServ.getUsers();
	}
	
	/**
	 * Updating password of current User
	 * 
	 * @param request
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/updatPswd.MasterServlet", method = RequestMethod.POST)
	public @ResponseBody OpResult doUpdatPswd(HttpServletRequest request) throws JsonProcessingException {
		String newPword = request.getParameter("password");
		
		System.out.println(newPword);
		
		System.out.println("In update Pwd");
		System.out.println(request);
	
		//System.out.println(userServ.updatepswd(request, newPword));
		
		if (userServ.updatepswd(request, newPword)) {
			System.out.println("resetsucessfull");
			return new OpResult(true);
		} else {
			System.out.println("no sucsess");
			return new OpResult(false);
		}	
	}
	
	/**
	 * 
	 * @param request
	 * @return boolean confirm email sent 
	 * @throws JsonProcessingException
	 */
	
	@RequestMapping(value = "/resetPswd.MasterServlet", method = RequestMethod.POST)
	public @ResponseBody User doRestPwd(HttpServletRequest request) throws JsonProcessingException {
		
		System.out.println("in the resetmapper");
		
		//generating random password
		String randString =  generateAlphaNumericPassword();
		
		// Creating message 
		String senderEmailId = "greenmonkeys83@gmail.com";
		String receiverEmailId = request.getParameter("email");
		String subject = "Your Password Has Been Successfully Reset";
		String message = "Your account is at serious thread. On our end it looks like "
				+ "you have forgotten your password. If that is not the case, please "
				+ "consider that someone decided to prank you a little bit. In any case, "
				+ "we suggest to change your password once you firstly log in."
				+ "\n\n Your new Password is: \t"+ randString
				+"\n\n Have a shitty day!!! ";
		
		//sending email 
		mail.sendEmail(senderEmailId, receiverEmailId, subject, message);
		
		//Updating password in database
		System.out.println("in the updat eemail just before the getting the user");
		System.out.println(request.getParameter("username"));
		System.out.println(receiverEmailId);
		System.out.println(userServ.randomPswd(request.getParameter("username"), receiverEmailId, randString));
		return userServ.randomPswd(request.getParameter("username"), receiverEmailId, randString);
		
	}
	
	public static String generateAlphaNumericPassword() {
		final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		int len = 8;
		StringBuilder builder = new StringBuilder();
		while (len-- != 0) {
		int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
		builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString().toLowerCase();
	}

	
}
