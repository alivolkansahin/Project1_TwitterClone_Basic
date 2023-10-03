package com.volkans.implementations.repository.entities;

import java.util.ArrayList;
import java.util.List;

import com.volkans.implementations.repository.EHashtag;

public non-sealed final class Tweet extends Message{ // final : kimse miras alamasın
	private List<EHashtag> hashtagsList;
	private List<Comment> commentsList;
	private List<User> likersList;
	private List<User> reTweetersList;
	private String id;
	
	//hashtagsiz constructor
	public Tweet(String dateCreated, String context, User sender) {
		super(dateCreated, context, sender);
		setHashtagsList(new ArrayList<>());
		setCommentsList(new ArrayList<>());
		setLikersList(new ArrayList<>());
		setReTweetersList(new ArrayList<>());
		setId(Integer.toString(Person.rnd.nextInt(100_000,1_000_000))); // 6 haneli random bir sayı olsun ID'si
	}
	
	//hashtagli constructor
	public Tweet(String dateCreated, String context, User sender,List<EHashtag> hashtagList) { 
		super(dateCreated, context, sender);
		setHashtagsList(hashtagList);
		setCommentsList(new ArrayList<>());
		setLikersList(new ArrayList<>());
		setReTweetersList(new ArrayList<>());
		setId(Integer.toString(Person.rnd.nextInt(100_000,1_000_000))); // 6 haneli random bir sayı olsun ID'si
	}

	public List<EHashtag> getHashtagsList() {
		return hashtagsList;
	}

	public void setHashtagsList(List<EHashtag> hashtagsList) {
		this.hashtagsList = hashtagsList;
	}

	public List<Comment> getCommentsList() {
		return commentsList;
	}

	public void setCommentsList(List<Comment> commentsList) {
		this.commentsList = commentsList;
	}

	public List<User> getLikersList() {
		return likersList;
	}

	public void setLikersList(List<User> likersList) {
		this.likersList = likersList;
	}

	public List<User> getReTweetersList() {
		return reTweetersList;
	}

	public void setReTweetersList(List<User> reTweetersList) {
		this.reTweetersList = reTweetersList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		if (getHashtagsList().isEmpty()) {
			return "\n-----------------------------------------------------------------\n|Sender: " 
					+ getSender().getUsername() + "\n|'" + getContext()+ "'\n|Sent: " + getDateCreated() 
					+ "\n|Comments: " + getCommentsList().size() + "\tRetweets: " + getReTweetersList().size() 
					+ "\tLikes: " + getLikersList().size() + "\tID: " + getId() 
					+ "\n-----------------------------------------------------------------";
		}
		return "\n-----------------------------------------------------------------\n|Sender: " 
				+ getSender().getUsername() + "\n|'" + getContext() + "'\n|Hashtags: " + getHashtagsList()
				+ "\n|Sent: " + getDateCreated() + "\n|Comments: " + getCommentsList().size() + "\tRetweets: " 
				+ getReTweetersList().size() + "\tLikes: " + getLikersList().size() + "\tID: " + getId() 
				+ "\n-----------------------------------------------------------------";
	}
	
}
