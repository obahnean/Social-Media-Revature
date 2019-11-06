package com.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.dao.UserDao;
import com.models.User;
import com.util.OpResult;

@Service
public class UserServiceImpl implements UserService {
	private UserDao userDao;
	private JavaMailSender mailSender;
	
	
	@Autowired
	public UserServiceImpl(UserDao userDao, JavaMailSender mailSender) {
		this.userDao = userDao;
		this.mailSender = mailSender;
	}
	
	/**
	 * Attempts to authenticate a user in the system and set up their session
	 * 
	 * @param request 
	 * @param uname The username
	 * @param pword The password
	 * @return the result of the login attempt
	 */
	public User verifyLogin(HttpSession session, String uname, String pword) {
		User user = userDao.selectByUserName(uname, pword);
		System.out.println(user);
		if (user != null) {
			System.out.println("Init session");
			initUserSession(session, user);
		}
		return user;
	}
	//Set up the current session with this user
	private void initUserSession(HttpSession session, User user) {
		session.setAttribute("currentUser", user);
	}
	
	public OpResult registerUser(User u) {
		try {
		userDao.insert(u);
		return new OpResult(true);
		}catch(Exception e) {
		}return new OpResult(false);
	}
	
	public User resetPassword(String email){
		//User user = userDao.selectByEmail(email);
		return null;
	}
	
	/**
	 * 
	 * @return All user objects in the database
	 */
	public List<User> getUsers() {
		return userDao.selectAll();
	}

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Updates Specific User Password
	 * 
	 */
	public boolean  updatepswd(HttpServletRequest request, String password) {
		boolean b = false;
		
		System.out.println("In password service");
		System.out.println(request.getParameter("oldPassword"));
		
		//getting user to update password
		User update = userDao.selectByUserName(request.getParameter("username"), request.getParameter("oldPassword"));
		 System.out.println(request.getSession().getAttribute("currentUser"));
		 
		//checking to see if not null
		System.out.println("In update password service");
		System.out.println(update);
		System.out.println("Update Pswd Service");

		update.setPassword(password);
		System.out.println(update.getPassword());
		
		// updating password
		userDao.update(update);
		System.out.println(update);
		//Check Update
		User user = userDao.selectByUserName(update.getUsername(), password);
		if (user == null) {
		b = false;
		}else {
			b = true;
		}
		System.out.println(b);
		return b;
	}	
	
	/**
	 * Updating Profile
	 * @param userSet user object 
	 * @param username
	 * @param firstname
	 * @param lastname
	 * @param email
	 * @return
	 */
	public User updateProfile(HttpServletRequest request, String username, String firstname, String lastname, String email) {
	
		//updating profile
		//Throws a null pointer with no session 
		/* User userSet = (User)request.getSession().getAttribute("currentUser"); */
		/* User userSet = userDao.selectUser(username); */
		
		
		//gsetting user password to invoke a instance
		//String password = request.getParameter("password");
		User userSet = userDao.selectByUserName(request.getParameter("username"), request.getParameter("password"));

	
	System.out.println(request.getSession());
	userSet.setLastname(username);	
	userSet.setFirstname(firstname); 
	userSet.setLastname(lastname);
	userSet.setEmail(email);
	
	//update with current user
	userDao.update(userSet);
	System.out.println(userSet);
	
	return userSet;
	} 	
	
	/**
	 * getting user JSON
	 * @param request
	 * @return
	 */
	public User getProfile(HttpServletRequest request) {
		//getting username for user obj
		
	User userGet = (User)request.getSession().getAttribute("currentUser");
	String username = userGet.getUsername();
	System.out.println(username);
	System.out.println(userGet);
	System.out.println("in user profile");
	userGet = userDao.selectByUserName(username, username);
	// this is a test username util server works
	//String testUsername = "TestUser2";
		/* System.out.println(userDao.selectByUserName(username)); */
	
	//selecting one user
	return userGet;
	}
	
	
	/**
	 *  Geting Current User In the current session 
	 * @param request
	 * @return User
	 */
	public User getUserInfo(HttpServletRequest request) {
		User userGet = (User)request.getSession().getAttribute("currentUser");
		return userGet;
	
	}
	
	/** 
	 * Getting User and updating password with rest
	 * carl change this to password function later
	 */
	public User randomPswd(String username, String email, String password) {
		System.out.println("IN USER EMAIL IMMPL FINDER");
		System.out.println(username);
		System.out.println(email);
		 User userE = userDao.selectByEmail(username, email);
		 System.out.println(userE);
		 //see if has worked
		 System.out.println("Checking update");
		 userE.setPassword(password);
		 System.out.println(userE);
		 
		 userDao.update(userE);
		 
		 return userE;
	}

	@Override
	public void updateUser(User u) {
		userDao.update(u);
	}
}
