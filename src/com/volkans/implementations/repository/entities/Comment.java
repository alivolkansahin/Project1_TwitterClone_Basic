package com.volkans.implementations.repository.entities;

import java.util.ArrayList;
import java.util.List;

import com.volkans.implementations.repository.EHashtag;
													   
public non-sealed final class Comment extends Message{ // final : kimse miras alamasın          
	private List<EHashtag> hashtagsList;               // yoruma da yorum yapılabilsin istemedim işler çok karışmasın diye...     
	private List<User> likersList;
	private List<User> reTweetersList;
	private String id;

	
	// hashtagsiz constructor
	public Comment(String dateCreated, String context, User sender) {
		super(dateCreated, context, sender);
		setHashtagsList(new ArrayList<>());
		setReTweetersList(new ArrayList<>());
		setLikersList(new ArrayList<>());
		setId(Integer.toString(Person.rnd.nextInt(10_000,100_000))); // 5 haneli random bir sayı olsun ID'si
	}
	
	// hashtagli constructor
	public Comment(String dateCreated, String context, User sender, List<EHashtag> hashtagList) {
		super(dateCreated, context, sender);
		setHashtagsList(hashtagList); // arrays.aslist
		setReTweetersList(new ArrayList<>());
		setLikersList(new ArrayList<>());
		setId(Integer.toString(Person.rnd.nextInt(10_000,100_000))); // 5 haneli random bir sayı olsun ID'si
	}

	public List<EHashtag> getHashtagsList() {
		return hashtagsList;
	}

	public void setHashtagsList(List<EHashtag> hashtagsList) {
		this.hashtagsList = hashtagsList;
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
			return "__Comment__\nSender: " + getSender().getUsername() + "\n'" + getContext() + "'\nSent: " + getDateCreated() 
			+ "\tRetweets: " + getReTweetersList().size() + "\tLikes: " + getLikersList().size() + "\tID: " + getId();
		}
		return "__Comment__\nSender: " + getSender().getUsername() + "\n'" + getContext() + "'\nHashtags: " + getHashtagsList() + "\nSent: " + getDateCreated() 
						+ "\tRetweets: " + getReTweetersList().size() + "\tLikes: " + getLikersList().size() + "\tID: " + getId();
	}
	
}
