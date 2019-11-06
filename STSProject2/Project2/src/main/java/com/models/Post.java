package com.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "post")
public class Post {
	@Id
	@Column(name = "postId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int postId;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "User_FK")
	private User author;
	
	@Column(name = "body")
	private String body;
	
	@Column(name = "image")
	private String image;
	
	@Column(name = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private List<User> likes;

	public Post() {
	}
	//for post creation only has author and body
	public Post(User author, String body) {
		super();
		this.author = author;
		this.body = body;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public List<User> getLikes() {
		return likes;
	}
	public void setLikes(List<User> likes) {
		this.likes = likes;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "\n\tPost [postId=" + postId + ", author=" + author + ", body=" + body + ", image=" + image + ", timestamp="
				+ timestamp + "]";
	}
	
}
