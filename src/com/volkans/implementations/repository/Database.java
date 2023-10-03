package com.volkans.implementations.repository;

import java.util.ArrayList;
import java.util.List;

import com.volkans.implementations.repository.entities.Admin;
import com.volkans.implementations.repository.entities.Comment;
import com.volkans.implementations.repository.entities.Tweet;
import com.volkans.implementations.repository.entities.User;

public final class Database { // hali hazırda kayıtlı olan adminler, userler ve onların tweetleri vs gibi şeyleri tutan bir Database sınıfı (final - kalıtım alamaz kimse)
	private List<Admin> adminsList;
	private List<User> usersList;
	
	public Database() {
		super();
		setAdminsList(new ArrayList<>());
		setUsersList(new ArrayList<>());
		createAdmins();
		createUsers();
		fillUsersAdress();
		fillUsersPhotoAndBio();
		addFollowersToUsers();
		addTweetsToUsers();
		usersInteractionsBetween();	
	}
	
	public void createAdmins() {
		// 2 admin - barış demirci & berkin yardımcı
		getAdminsList().add(new Admin("Barış", "Demirci", "barisdemirci", "barisd123", "5421112233", "2019-08-07"));
		getAdminsList().add(new Admin("Berkin", "Yardımcı", "berkinyardimci", "berkiny456", "5321112233", "2021-11-23"));
	}
	
	public void createUsers() {
		// 2 user - muhittin okan
		getUsersList().add(new User("Muhittin", "Ülker", "muhittinulker", "muhittin123", "5058838250", "2022-02-06"));
		getUsersList().add(new User("Okan", "Şenol", "okansenol", "okan456", "5397213345", "2023-04-28"));
	}
	
	public void fillUsersAdress() {
		// muhittin
		getUsersList().get(0).getAdress().setCity("Ankara");
		getUsersList().get(0).getAdress().setCountry("Türkiye");
		getUsersList().get(0).getAdress().setStreet("Etiler");
		getUsersList().get(0).getAdress().setPostalCode("12345");
		// okan
		getUsersList().get(1).getAdress().setCity("İstanbul");
		getUsersList().get(1).getAdress().setCountry("Türkiye");
		getUsersList().get(1).getAdress().setStreet("Maslak");
		getUsersList().get(1).getAdress().setPostalCode("34567");
	}
	
	public void fillUsersPhotoAndBio() {
		//muhittin
		getUsersList().get(0).getProfile().setBio("Çılgın Yazılımcı");
		getUsersList().get(0).getProfile().setPhoto("çılgınbirphoto.jpeg");
		// okan
		getUsersList().get(1).getProfile().setBio("Deneyimli Mühendis");
		getUsersList().get(1).getProfile().setPhoto("karizmatikbirfoto.jpeg");
	}
	
	public void addFollowersToUsers() {
		// karşılıklı takipleşsinler
		getUsersList().get(0).getProfile().getFollowing().add(getUsersList().get(1)); // muhittin okanı takip ediyor
		getUsersList().get(1).getProfile().getFollowers().add(getUsersList().get(0)); 
		getUsersList().get(0).getProfile().getFollowers().add(getUsersList().get(1)); // okan muhittini takip ediyor
		getUsersList().get(1).getProfile().getFollowing().add(getUsersList().get(0)); 
	}
	
	public void addTweetsToUsers() {
		// muhittin tweetleri
		getUsersList().get(0).getProfile().getTweetsList().add(new Tweet("2023-08-17T17:20:23", "Yeni dönem yeni hedefler" ,getUsersList().get(0)));  // hashtagsiz constructor
		getUsersList().get(0).getProfile().getPostsList().add(getUsersList().get(0).getProfile().getTweetsList().get(0)); // bütün postlarına ekle
		List<EHashtag> muhittinTweet2HashtagList = new ArrayList<>();
		muhittinTweet2HashtagList.add(EHashtag.BILGEADAM);
		muhittinTweet2HashtagList.add(EHashtag.JAVA);
		getUsersList().get(0).getProfile().getTweetsList().add(new Tweet("2023-08-26T14:30:45", "Kursumuz güzel ilerliyor" ,getUsersList().get(0), muhittinTweet2HashtagList)); // hashtagli constructor (varargs)
		getUsersList().get(0).getProfile().getPostsList().add(getUsersList().get(0).getProfile().getTweetsList().get(1)); // bütün postlarına ekle
		// okan tweeti
		List<EHashtag> okanTweet1HashtagList = new ArrayList<>();
		okanTweet1HashtagList.add(EHashtag.YAZILIM);
		getUsersList().get(1).getProfile().getTweetsList().add(new Tweet("2023-06-10T11:15:10", "Bence geleceğin en parlak sektörü" ,getUsersList().get(1), okanTweet1HashtagList));  // hashtagli constructor
		getUsersList().get(1).getProfile().getPostsList().add(getUsersList().get(1).getProfile().getTweetsList().get(0)); // bütün postlarına ekle
//		getUserHashtagList().clear();
	}
	
	public void usersInteractionsBetween() {
		// okan muhittinin 2.twitine comment,like ve retweet yapsın
		getUsersList().get(0).getProfile().getTweetsList().get(1).getLikersList().add(getUsersList().get(1)); // like
		getUsersList().get(0).getProfile().getTweetsList().get(1).getCommentsList().add(new Comment("2023-08-26T16:03:10", "Katılıyorum harika gidiyor",getUsersList().get(1))); // comment - hashtagsiz constructor 
		getUsersList().get(0).getProfile().getTweetsList().get(1).getReTweetersList().add(getUsersList().get(1));            // retweet (okanı bu twiti retwitleyenler listesine ekle)
		getUsersList().get(1).getProfile().getReTweetsList().add(getUsersList().get(0).getProfile().getTweetsList().get(1)); // retweet (okanın retweetler listesine ekle)
		getUsersList().get(1).getProfile().getPostsList().add(getUsersList().get(1).getProfile().getReTweetsList().get(0)); // retweet (okanın bütün postları listesine de ekle)
		// muhittin de okanın bu yorumunu beğensin
		getUsersList().get(0).getProfile().getTweetsList().get(1).getCommentsList().get(0).getLikersList().add(getUsersList().get(0));
	}

	public List<Admin> getAdminsList() {
		return adminsList;
	}

	public void setAdminsList(List<Admin> adminsList) {
		this.adminsList = adminsList;
	}

	public List<User> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<User> usersList) {
		this.usersList = usersList;
	}

}
