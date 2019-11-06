package com.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.models.User;
import com.util.OpResult;

public interface UserService {

	public User verifyLogin(HttpSession session, String uname, String pword);

	public OpResult registerUser(User u);

	public List<User> getUsers();

	User getUserByEmail(String email);

	public boolean updatepswd(HttpServletRequest request, String password);

	public User updateProfile(HttpServletRequest request, String username, String firstname, String lastname,
			String email);
	public void updateUser(User u);
	public User getProfile(HttpServletRequest request);
	
	public User getUserInfo(HttpServletRequest request);
	
	public User randomPswd(String username, String email, String password);

}
