package com.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Deprecated
public class RequestHelper {
	public static String process(HttpServletRequest request) throws JsonProcessingException,IOException {
		System.out.println(request.getRequestURI());
		switch (request.getRequestURI()) {
		
		/*case "/Project2/login.MasterServlet":
			System.out.println("in login.do requesthelper");
			return UserController.doLogin(request);
			
		case "/Project2/logout.MasterServlet":
			System.out.println("in logout.do requesthelper");
			return UserController.doLogout(request);
			
		case "/Project2/register.MasterServlet":
			return UserController.doRegister(request);
			
		case "/Project2/getProfiles.MasterServlet":
			return ProfileController.getProfiles();
			
		case "/Project2/makePost.MasterServlet":
			return PostsController.makePost(request);
			
		case "/Project2/getPosts.MasterServlet":
			return PostsController.getPosts(request);*/

		default:
			System.out.println("Default");
			return new ObjectMapper().writeValueAsString("bad endpoint");
		}
	}

}
