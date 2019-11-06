package com.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.models.User;

@Repository("users")
@Transactional
public class UserDao {

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private SessionFactory sesFact;

	public UserDao() {
		System.out.println("hi");
	}

	public SessionFactory getSesFact() {
		return sesFact;
	}

	@Autowired
	public void setSesFact(SessionFactory sesFact) {
		System.out.println("set");
		this.sesFact = sesFact;
	}

	// register
	public boolean insert(User u) {
		try {
			sesFact.getCurrentSession().save(u);
			return true;
		} catch (Exception e) {
			System.out.println("Failed on insert");
			return false;
		}

	}

	// update
	public void update(User u) {
		sesFact.getCurrentSession().merge(u);
	}

	// for searching users
	public List<User> searchSelect(String username) {

		List<User> users = sesFact.getCurrentSession()
				.createQuery("from User where username='%" + username + "%'", User.class).list();

		if (users.size() == 0) {
			System.out.println("No users");
			return null;
		}
		return users;
	}

	// select statement that takes username and password used for logging in
	public User selectByUserName(String username, String password) {

		List<User> users = sesFact.getCurrentSession()
				.createQuery("from User where username='" + username
						+ "' and password = get_hash('" + username + "','" + password + "')",
						User.class)
				.list();

		if (users.size() == 0) {
			System.out.println("No Login");
			return null;
		}
		return users.get(0);
	}

	// all users
	public List<User> selectAll() {
		return sesFact.getCurrentSession().createQuery("from User", User.class).list();
	}

	// for one users by email and username
	public User selectByEmail(String username, String email) {

		List<User> users = sesFact.getCurrentSession()
				.createQuery("from User where username='" + username + "' and email ='" + email + "'", User.class)

				.list();

		if (users.size() == 0) {
			System.out.println("No Login");
			return null;
		}
		return users.get(0);
	}

	public User selectByUserName1(String username) {

		List<User> users = sesFact.getCurrentSession()
				.createQuery("from User where username='" + username+"'", User.class).list();

		if (users.size() == 0) {
			System.out.println("No Login");
			return null;
		}
		return users.get(0);
	}
}
