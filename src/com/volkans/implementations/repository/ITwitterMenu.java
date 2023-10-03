package com.volkans.implementations.repository;

public interface ITwitterMenu {
	void runMainPage(); //main
	int getMainPageChoice();
	void applyMainPageChoice(int key);
	
	void runAdminPage(); //admin
	int getAdminPageChoice();
	void applyAdminPageChoice(int key);
	void runAdminEditTweetPage();
	int getAdminEditTweetPageChoice();
	void applyAdminEditTweetPageChoice(int key);
	void runAdminEditUserPage();
	int getAdminEditUserPageChoice();
	void applyAdminEditUserPageChoice(int key);
	void runAdminBanUserPage();
	int getAdminBanUserPageChoice();
	void applyAdminBanUserPageChoice(int key);
	void runAdminListPage();
	int getAdminListPageChoice();
	void applyAdminListPageChoice(int key);
	
	void runUserPage();
	int getUserPageChoice();
	void applyUserPageChoice(int key);
	void runUserLoginPage();
	int getUserLoginPageChoice();
	void applyUserLoginPageChoice(int key);
	void runUserEditTweetPage();
	int getUserEditTweetPageChoice();
	void applyUserEditTweetPageChoice(int key);
	void runUserAccountManagerPage();
	int getUserAccountManagerChoice();
	void applyUserAccountManagerChoice(int key);
	
	void cyclePage(int key);

}
