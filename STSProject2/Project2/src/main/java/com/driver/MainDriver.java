package com.driver;

import com.dao.PostDao;
import com.dao.UserDao;
import com.models.User;

public class MainDriver {
	static UserDao udao = new UserDao();
	static PostDao pdao = new PostDao();

	public static void main(String[] args) {
		//innitTables();
		System.out.println("done");
		
		System.out.println("by username");
		System.out.println(udao.selectByUserName("A.Bailey", "pwd"));
		
		System.out.println("select all users");
		System.out.println(udao.selectAll());
		
		System.out.println("post by id");
		System.out.println(pdao.selectPostById(3));
		
		System.out.println("select all posts");
		System.out.println(pdao.selectAll());
		
		
		
	}

	static void innitTables() {
		User alex = new User("A.Bailey","pwd","Alex","Bailey","email");
		//Post p1 = new Post(alex,"Welcome to socialite Alex");
		udao.insert(alex);
		//pdao.insert(p1);
		
		User carl = new User("C.Richardson","pwd","Carl","Richardson","email");
		//Post p2 = new Post(carl,"welcome to socialite Carl");
		udao.insert(carl);
		//pdao.insert(p2);
		
		User rob = new User("R.J.Bohlman","pwd","Robert","Bohlman","email");
		//Post p3 = new Post(rob,"Welcome to socialite Robert");
		udao.insert(rob);
		//pdao.insert(p3);
		
		User ovi = new User("O.Bahnean","pwd","Ovidiu","Bahnean","email");
		//Post p4 = new Post(ovi,"Welcome to socialite Ovidiu");
		udao.insert(ovi);
		//pdao.insert(p4);
		
		User roy = new User("R.Wright","pwd","Roy","Wright","email");
		//Post p5 = new Post(roy,"Welcome to socialite Roy");
		udao.insert(roy);
		//pdao.insert(p5);
	}
}
