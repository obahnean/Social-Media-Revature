package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.PostDao;
import com.models.Post;

@Service
public class PostsServiceImpl implements PostsService {
	
	private PostDao postDao;
	
	@Autowired
	public PostsServiceImpl(PostDao postDao) {
		this.postDao = postDao;
	}

	// posts
	public List<Post> selectAllPosts() {
		return postDao.selectAll();
	}
	public void updatePost(Post p) {
		postDao.update(p);
	}
	public boolean submitPost(Post p) {
		return postDao.insert(p);
	}

	public Post selectPostById(int pid) {
		
		return postDao.selectPostById(pid);
	}

}
