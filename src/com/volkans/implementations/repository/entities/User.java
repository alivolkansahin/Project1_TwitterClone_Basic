package com.volkans.implementations.repository.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.volkans.implementations.repository.EUserStatus;
import com.volkans.implementations.repository.Utils;

public non-sealed final class User extends Person { // final class : burdan kimse kalıtım alamasın
	private Profile profile;
	private Map<User,List<Message>> dmBox;
	private EUserStatus userStatus;
	private List<User> blockedList;
	
	public User(String name, String surname, String username, String password, String phoneNumber, String registerDate) {
		super(name, surname, username, password, phoneNumber, registerDate);
		setProfile(new Profile()); // boş profil oluşturuyorum.
		setDmBox(new HashMap<>());
		setUserStatus(EUserStatus.ACTIVE);
		setBlockedList(new ArrayList<>());
	}
	
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Map<User, List<Message>> getDmBox() {
		return dmBox;
	}

	public void setDmBox(Map<User, List<Message>> dmBox) {
		this.dmBox = dmBox;
	}

	public EUserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(EUserStatus userStatus) {
		this.userStatus = userStatus;
	}
	
	public List<User> getBlockedList() {
		return blockedList;
	}

	public void setBlockedList(List<User> blockedList) {
		this.blockedList = blockedList;
	}

	@Override
	public String toString() {
		String status = "";
		if (getUserStatus().equals(EUserStatus.ACTIVE)) {
			status = " - Status: " + Utils.GREEN_BOLD_BRIGHT + getUserStatus() + Utils.RESET;
		} else if (getUserStatus().equals(EUserStatus.RESTRICTED)) {
			status = " - Status: " + Utils.YELLOW_BOLD_BRIGHT + getUserStatus() + Utils.RESET;
		} else {
			status = " - Status: " + Utils.RED_BOLD_BRIGHT + getUserStatus() + Utils.RESET;
		}
		return "------------------------------------------------------------------------------------------------\nUser: " + getName() +" " + getSurname() + " - Username: " + getUsername() + getAdress() 
		+ "\nPhone: " + getPhoneNumber() + "\nPosts: " + getProfile().getPostsList().size() + " - Tweets: " + getProfile().getTweetsList().size() + " - ReTweets: " + getProfile().getReTweetsList().size() + " - Followers: " + getProfile().getFollowers().size() + " - Following: " + getProfile().getFollowing().size() 
		+ "\nRegister Date: " + getRegisterDate() + status + " - ID: " + getId() + "\n------------------------------------------------------------------------------------------------";
	}
}
