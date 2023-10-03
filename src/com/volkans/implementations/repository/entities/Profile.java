package com.volkans.implementations.repository.entities;

import java.util.ArrayList;
import java.util.List;

public final class Profile { // kimse miras alamasın
	private String photo;
	private String bio;
//	private String registerDate; // zaten user ve adminin baba sınıfı Personda verdim bunu
	private List<Person> followers;
	private List<Person> following;
	private List<Tweet> tweetsList;
	private List<Tweet> reTweetsList;
	private List<Tweet> postsList; // tweets + retweets beraber
	
	public Profile() {
		super();
		setPhoto("");
		setBio("");
		setFollowers(new ArrayList<>());
		setFollowing(new ArrayList<>());
		setTweetsList(new ArrayList<>());
		setReTweetsList(new ArrayList<>());
		setPostsList(new ArrayList<>());
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public List<Person> getFollowers() {
		return followers;
	}

	public void setFollowers(List<Person> followers) {
		this.followers = followers;
	}

	public List<Person> getFollowing() {
		return following;
	}

	public void setFollowing(List<Person> following) {
		this.following = following;
	}

	public List<Tweet> getTweetsList() {
		return tweetsList;
	}

	public void setTweetsList(List<Tweet> tweetsList) {
		this.tweetsList = tweetsList;
	}

	public List<Tweet> getReTweetsList() {
		return reTweetsList;
	}

	public void setReTweetsList(List<Tweet> reTweetsList) {
		this.reTweetsList = reTweetsList;
	}

	public List<Tweet> getPostsList() {
		return postsList;
	}

	public void setPostsList(List<Tweet> postsList) {
		this.postsList = postsList;
	}

	@Override
	public String toString() {
		return "Photo: " + getPhoto() + "\nBio: " + getBio() + "\nFollowers: " + getFollowers().stream().map(p->p.getUsername()).toList() + "\nFollowing: " + getFollowing().stream().map(p->p.getUsername()).toList()
				+ "\nTweets:" + getTweetsList() + "\nRetweets:\n " + getReTweetsList();
	}
	
}
