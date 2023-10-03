package com.volkans.implementations.repository;

public non-sealed interface IAdminManager extends ISharedActions {
	void banUser();
	void unBanUser();
	void restrictUser();
	void unRestrictUser();
	void listAllUser(); 
	void listAllProfiles();
	void listBannedUsers();
	void listRestrictedUsers();

}
