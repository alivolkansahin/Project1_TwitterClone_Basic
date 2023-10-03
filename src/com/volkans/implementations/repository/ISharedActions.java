package com.volkans.implementations.repository;

public sealed interface ISharedActions permits IAdminManager, IUserManager{ // sealed interface (sadece izin verdikleri kalıtım alabilsin)
	boolean login();
	void editTweetContext();
	void removeTweet();
	void removeComment();
	void findUserById();
	void findTweetById();
	void findCommentById();
	void showMyAccount();
	void editNameAndSurname();
	void editUsername();
	void editAdress();
	void editPassword();
	void editPhoneNumber();
	void editPhoto();
	void editBio();
	void compactUserList();
	void compactTweetList();
	void compactCommentList();
}
