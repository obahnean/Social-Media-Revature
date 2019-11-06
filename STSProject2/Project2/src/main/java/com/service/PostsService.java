package com.service;

import java.util.List;

import com.models.Post;

public interface PostsService {
	
	public List<Post> selectAllPosts();
	
	public void updatePost(Post p);
	
	public Post selectPostById(int pid);
	
	public boolean submitPost(Post p);
}
