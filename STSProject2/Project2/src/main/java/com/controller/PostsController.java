package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.models.Post;
import com.models.User;
import com.service.PostsService;
import com.service.UserService;
import com.util.OpResult;

@CrossOrigin(origins="http://localhost:4200")
@Controller
public class PostsController {
	
	private PostsService postServ;
	private UserService userServ;
	
	@Autowired
	public PostsController(PostsService postServ,UserService userServ) {
		this.postServ = postServ;
		this.userServ = userServ;
	}
	
	@GetMapping(value="/getPosts.MasterServlet")
	public @ResponseBody List<Post> getPosts(HttpServletRequest request) 
			throws JsonProcessingException, IOException {
		
		List<Post> posts = postServ.selectAllPosts();
		return posts;
	}
	//update post (for likes)
	@PostMapping(value="/updatePost.MasterServlet")
	public @ResponseBody OpResult updatePost(HttpServletRequest request, @RequestBody int pid) {
		System.out.println("hello");
		Post p = postServ.selectPostById(pid);
		User u = (User) request.getSession().getAttribute("currentUser");
		System.out.println(p);
		List<User> list = p.getLikes();
		list.add(u);
		p.setLikes(list);
		System.out.println(p.getLikes());
		postServ.updatePost(p);
		return new OpResult(true);
	}
	//making a post includes updating the associated author object
	@PostMapping(value="/makePost.MasterServlet")
	public @ResponseBody OpResult makePost(HttpSession session, @RequestParam("body") String postBody, @RequestParam("image") String postImg) throws JsonProcessingException {
		User postAuthor = (User) session.getAttribute("currentUser");
		System.out.println(postAuthor);
		System.out.println(postBody);
		System.out.println(postImg);
		//String postBody = request.getParameter("body");
		Post post = new Post(postAuthor, postBody);
		post.setImage(postImg);
		
		if (postServ.submitPost(post)) {
			return new OpResult(true);
		} else {
			return new OpResult(false);
		}
	}

}
