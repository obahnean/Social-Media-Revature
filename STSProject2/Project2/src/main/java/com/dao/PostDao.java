package com.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.models.Post;

@Repository("posts")
@Transactional
public class PostDao {

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	private SessionFactory sesFact;

	public PostDao() {
	}

	public SessionFactory getSesFact() {
		return sesFact;
	}

	@Autowired
	public void setSesFact(SessionFactory sesFact) {
		this.sesFact = sesFact;
	}

	// register
	public boolean insert(Post u) {
		System.out.println("Inserting new post");
		try {
			sesFact.getCurrentSession().save(u);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// update
	public void update(Post p) {
		sesFact.getCurrentSession().merge(p);
	}

	// select statement that takes postname and password used for logging in
	public Post selectPostById(int id) {
		List<Post> posts = sesFact.getCurrentSession().createQuery("from Post where postId=" + id + "", Post.class)
				.list();
		if (posts.size() == 0) {
			System.out.println("No such post");
			return null;
		}
		return posts.get(0);
	}

	// posts
	public List<Post> selectAll() {
		return sesFact.getCurrentSession().createQuery("from Post order by timestamp desc", Post.class).list();
	}
}