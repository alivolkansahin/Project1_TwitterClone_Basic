package com.volkans.implementations.repository;

public non-sealed interface IUserManager extends ISharedActions{
	void register();
	void postTweet();
	void reTweet();
	void commentTweet();
	void showMyTweets();
	void showMyReTweets();
	void showProfile();
	void showMyProfile();
	void followUser();
	void unfollowUser();
	void sendDM();
	void checkDM();
	void blockUser();
	void unBlockUser();
}
