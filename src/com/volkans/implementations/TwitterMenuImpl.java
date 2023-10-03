package com.volkans.implementations;

import java.util.Scanner;

import com.volkans.implementations.repository.Database;
import com.volkans.implementations.repository.ITwitterMenu;
import com.volkans.implementations.repository.Utils;

public final class TwitterMenuImpl implements ITwitterMenu{ // final = kimse kalıtım alamaz.
	// program boyunca daima ayakta kalacak staticler...
	private static Database database = new Database();
	private static AdminManagerImpl adminActions = new AdminManagerImpl();
	private static UserManagerImpl userActions = new UserManagerImpl();
	private static Scanner scanner = new Scanner(System.in);
	
	private int key;
	private boolean whileCheckMainPage = true;
	//-----------------Admin Page Checks-------------------------
	private boolean whileCheckAdminPage = true;
	private boolean whileCheckAdminEditTweetPage = true;
	private boolean whileCheckAdminEditUserPage = true;
	private boolean whileCheckAdminBanUserPage = true;
	private boolean whileCheckAdminListPage = true;
	//-----------------User Page Checks--------------------------
	private boolean whileCheckUserPage = true;
	private boolean whileCheckUserLoginPage = true;
	private boolean whileCheckUserAccountManagerPage = true;
	private boolean whileCheckUserEditTweetPage = true;

	@Override
	public void runMainPage() {
		System.out.println(Utils.GREEN_BOLD_BRIGHT + "------------___WELCOME TO TWITTER__------------" + Utils.RESET);
		while (isWhileCheckMainPage()==true) {
			setWhileCheckAdminPage(true);
			setWhileCheckUserPage(true);
			applyMainPageChoice(getMainPageChoice());
		}	
	}

	@Override
	public int getMainPageChoice() {
		System.out.println("1- Admin Login");
		System.out.println("2- User Page");
		System.out.println("5- Database");
		System.out.println("0- Exit Program");
		setKey(Utils.getIntegerValue(TwitterMenuImpl.getScanner(), "Input the number of the operation you want to execute: "));
		return getKey();
	}

	@Override
	public void applyMainPageChoice(int key) {
		switch (key) {
		case 1:
			if(!getAdminActions().login()) {
				break;
			}		
			runAdminPage();
			break;
		case 2:
			runUserPage();
			break;
		case 5:
			programManual();
			break;	
		case 0:
			System.out.println(Utils.RED + "Program Terminated..." + Utils.RESET);
			getScanner().close();
			System.exit(0);	
		default:
			System.out.println(Utils.RED + "Yanlış giriş yaptınız." + Utils.RESET);
			break;
		}
		cyclePage(key); 
	}

	@Override
	public void runAdminPage() {
		while(isWhileCheckAdminPage()==true) {
			setWhileCheckAdminEditTweetPage(true);
			setWhileCheckAdminEditUserPage(true);
			setWhileCheckAdminBanUserPage(true);
			setWhileCheckAdminListPage(true);
			applyAdminPageChoice(getAdminPageChoice());
		}		
	}

	@Override
	public int getAdminPageChoice() {
		System.out.println(Utils.GREEN_BOLD_BRIGHT + "Admin " + getAdminActions().getAdmin().getUsername() + Utils.RESET);
		System.out.println("1- Tweet & Comment Actions (Edit/Remove Tweet & Remove Comment)");
		System.out.println("2- Edit User Actions (Name/Surname/Username/Adress/Password/PhoneNumber)");
		System.out.println("3- Ban & Restrict Actions (Ban/Unban/Restrict/Unrestrict)");
		System.out.println("4- List Actions (ListAllUsers/ListAllProfiles/ListBannedUsers/listRestrictedUsers)");
		System.out.println("5- Show My Account Info");
		System.out.println("9- Logout");
		System.out.println("0- Exit Program");
		setKey(Utils.getIntegerValue(TwitterMenuImpl.getScanner(), "Input the number of the operation you want to execute: "));
		return getKey();
	}

	@Override
	public void applyAdminPageChoice(int key) {
		switch (key) {
		case 1:
			runAdminEditTweetPage();
			break;
		case 2:
			runAdminEditUserPage();
			break;
		case 3:
			runAdminBanUserPage();
			break;	
		case 4:
			runAdminListPage();
			break;	
		case 5:
			getAdminActions().showMyAccount();
			break;
		case 9:
			setWhileCheckAdminPage(false);
			break;	
		case 0:
			System.out.println(Utils.RED + "Program Terminated..." + Utils.RESET);
			getScanner().close();
			System.exit(0);	
		default:
			System.out.println(Utils.RED + "Yanlış giriş yaptınız." + Utils.RESET);
			break;
		}
		cyclePage(key); 
		
	}
	@Override
	public void runAdminEditTweetPage() {
		while(isWhileCheckAdminEditTweetPage()==true) {
			applyAdminEditTweetPageChoice(getAdminEditTweetPageChoice());
		}			
	}

	@Override
	public int getAdminEditTweetPageChoice() {
		System.out.println(Utils.GREEN_BOLD_BRIGHT + "Admin " + getAdminActions().getAdmin().getUsername() + Utils.RESET);
		System.out.println("1- Edit Tweet");
		System.out.println("2- Remove Tweet");
		System.out.println("3- Remove Comment");
		System.out.println("9- Return Admin Page");
		System.out.println("0- Exit Program");
		setKey(Utils.getIntegerValue(TwitterMenuImpl.getScanner(), "Input the number of the operation you want to execute: "));
		return getKey();
	}

	@Override
	public void applyAdminEditTweetPageChoice(int key) {
		switch (key) {
		case 1:
			getAdminActions().editTweetContext();
			break;
		case 2:
			getAdminActions().removeTweet();
			break;
		case 3:
			getAdminActions().removeComment();
			break;	
		case 9:
			setWhileCheckAdminEditTweetPage(false);
			break;	
		case 0:
			System.out.println(Utils.RED + "Program Terminated..." + Utils.RESET);
			getScanner().close();
			System.exit(0);	
		default:
			System.out.println(Utils.RED + "Yanlış giriş yaptınız." + Utils.RESET);
			break;
		}
		cyclePage(key); 	
	}

	@Override
	public void runAdminEditUserPage() {
		while(isWhileCheckAdminEditUserPage()==true) {
			applyAdminEditUserPageChoice(getAdminEditUserPageChoice());
		}		
	}

	@Override
	public int getAdminEditUserPageChoice() {
		System.out.println(Utils.GREEN_BOLD_BRIGHT + "Admin " + getAdminActions().getAdmin().getUsername() + Utils.RESET);
		System.out.println("1- Edit User's Name & Surname");
		System.out.println("2- Edit User's Username");
		System.out.println("3- Edit User's Adress");
		System.out.println("4- Edit User's Password");
		System.out.println("5- Edit User's PhoneNumber");
		System.out.println("6- Edit User's Photo");
		System.out.println("7- Edit User's Bio");
		System.out.println("9- Return Admin Page");
		System.out.println("0- Exit Program");
		setKey(Utils.getIntegerValue(TwitterMenuImpl.getScanner(), "Input the number of the operation you want to execute: "));
		return getKey();
	}

	@Override
	public void applyAdminEditUserPageChoice(int key) {
		switch (key) {
		case 1:
			getAdminActions().editNameAndSurname();
			break;
		case 2:
			getAdminActions().editUsername();
			break;
		case 3:
			getAdminActions().editAdress();
			break;	
		case 4:
			getAdminActions().editPassword();
			break;	
		case 5:
			getAdminActions().editPhoneNumber();
			break;	
		case 6:
			getAdminActions().editPhoto();
			break;
		case 7:
			getAdminActions().editBio();
			break;
		case 9:
			setWhileCheckAdminEditUserPage(false);
			break;	
		case 0:
			System.out.println(Utils.RED + "Program Terminated..." + Utils.RESET);
			getScanner().close();
			System.exit(0);	
		default:
			System.out.println(Utils.RED + "Yanlış giriş yaptınız." + Utils.RESET);
			break;
		}
		cyclePage(key); 	
	}

	@Override
	public void runAdminBanUserPage() {
		while(isWhileCheckAdminBanUserPage()==true) {
			applyAdminBanUserPageChoice(getAdminBanUserPageChoice());
		}	
	}

	@Override
	public int getAdminBanUserPageChoice() {
		System.out.println(Utils.GREEN_BOLD_BRIGHT + "Admin " + getAdminActions().getAdmin().getUsername() + Utils.RESET);
		System.out.println("1- Ban User");
		System.out.println("2- UnBan User");
		System.out.println("3- Restrict User");
		System.out.println("4- UnRestrict User");
		System.out.println("9- Return Admin Page");
		System.out.println("0- Exit Program");
		setKey(Utils.getIntegerValue(TwitterMenuImpl.getScanner(), "Input the number of the operation you want to execute: "));
		return getKey();
	}

	@Override
	public void applyAdminBanUserPageChoice(int key) {
		switch (key) {
		case 1:
			getAdminActions().banUser();
			break;
		case 2:
			getAdminActions().unBanUser();
			break;
		case 3:
			getAdminActions().restrictUser();
			break;	
		case 4:
			getAdminActions().unRestrictUser();
			break;	
		case 9:
			setWhileCheckAdminBanUserPage(false);
			break;	
		case 0:
			System.out.println(Utils.RED + "Program Terminated..." + Utils.RESET);
			getScanner().close();
			System.exit(0);	
		default:
			System.out.println(Utils.RED + "Yanlış giriş yaptınız." + Utils.RESET);
			break;
		}
		cyclePage(key); 
		
	}

	@Override
	public void runAdminListPage() {
		while(isWhileCheckAdminListPage()==true) {
			applyAdminListPageChoice(getAdminListPageChoice());
		}		
	}

	@Override
	public int getAdminListPageChoice() {
		System.out.println(Utils.GREEN_BOLD_BRIGHT + "Admin " + getAdminActions().getAdmin().getUsername() + "\u001B[0m");
		System.out.println("1- List All Users");
		System.out.println("2- List All Profiles");
		System.out.println("3- List Banned Users");
		System.out.println("4- List Restricted Users");
		System.out.println("9- Return Admin Page");
		System.out.println("0- Exit Program");
		setKey(Utils.getIntegerValue(TwitterMenuImpl.getScanner(), "Input the number of the operation you want to execute: "));
		return getKey();
	}

	@Override
	public void applyAdminListPageChoice(int key) {
		switch (key) {
		case 1:
			getAdminActions().listAllUser();
			break;
		case 2:
			getAdminActions().listAllProfiles();
			break;
		case 3:
			getAdminActions().listBannedUsers();
			break;	
		case 4:
			getAdminActions().listRestrictedUsers();
			break;	
		case 9:
			setWhileCheckAdminListPage(false);
			break;	
		case 0:
			System.out.println(Utils.RED + "Program Terminated..." + Utils.RESET);
			getScanner().close();
			System.exit(0);	
		default:
			System.out.println(Utils.RED + "Yanlış giriş yaptınız." + Utils.RESET);
			break;
		}
		cyclePage(key); 
		
	}

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public void runUserPage() {
		while(isWhileCheckUserPage()==true) {
			setWhileCheckUserLoginPage(true);
			applyUserPageChoice(getUserPageChoice());
		}		
	}

	@Override
	public int getUserPageChoice() {
		System.out.println("1- Register");
		System.out.println("2- Login");
		System.out.println("9- Return Main Page");
		System.out.println("0- Exit Program");
		setKey(Utils.getIntegerValue(TwitterMenuImpl.getScanner(), "Input the number of the operation you want to execute: "));
		return getKey();
	}

	@Override
	public void applyUserPageChoice(int key) {
		switch (key) {
		case 1:
			getUserActions().register();
			break;
		case 2:
			if(!getUserActions().login()) {
				break;
			}	
			cyclePage(key);	
			runUserLoginPage();
			break;
		case 9:
			setWhileCheckUserPage(false);
			break;	
		case 0:
			System.out.println(Utils.RED + "Program Terminated..." + Utils.RESET);
			getScanner().close();
			System.exit(0);	
		default:
			System.out.println(Utils.RED + "Yanlış giriş yaptınız." + Utils.RESET);
			break;
		}
		cyclePage(key);	
	}
	
	@Override
	public void runUserLoginPage() {
		while(isWhileCheckUserLoginPage()==true) {
			setWhileCheckUserEditTweetPage(true);
			setWhileCheckUserAccountManagerPage(true);
			applyUserLoginPageChoice(getUserLoginPageChoice());
		}		
		
	}

	@Override
	public int getUserLoginPageChoice() {
		System.out.println(Utils.GREEN_BOLD_BRIGHT + "User " + getUserActions().getUser().getUsername() + Utils.RESET);
		System.out.println("1- Tweet-Comment Actions (ShowMyTweet & Tweet Post/Edit/Retweet & Comment Post/Edit/Retweet) ");
		System.out.println("2- Account Manager (ShowMyAccount & Edit Name/Surname/Username/Adress/Password/PhoneNumber/Photo/Bio) ");	
		System.out.println("3- Show Profile");
		System.out.println("4- Show My Profile");
		System.out.println("5- Send DM");
		System.out.println("6- Check MessageBox");
		System.out.println("7- Block User");
		System.out.println("8- Unblock User");
		System.out.println("11- Follow User");
		System.out.println("12- Unfollow User");	
		System.out.println("9- Logout & Return User Page");
		System.out.println("0- Exit Program");
		setKey(Utils.getIntegerValue(TwitterMenuImpl.getScanner(), "Input the number of the operation you want to execute: "));
		return getKey();
	}

	@Override
	public void applyUserLoginPageChoice(int key) {
		switch (key) {
		case 1:
			runUserEditTweetPage();
			break;
		case 2:
			runUserAccountManagerPage();
			break;
		case 3:
			getUserActions().showProfile();
			break;	
		case 4:
			getUserActions().showMyProfile();
			break;	
		case 5:
			getUserActions().sendDM();
			break;	
		case 6:
			getUserActions().checkDM();
			break;	
		case 7:
			getUserActions().blockUser();
			break;	
		case 8:
			getUserActions().unBlockUser();
			break;	
		case 11:
			getUserActions().followUser();
			break;	
		case 12:
			getUserActions().unfollowUser();
			break;	
		case 9:
			setWhileCheckUserLoginPage(false);
			break;	
		case 0:
			System.out.println(Utils.RED + "Program Terminated..." + Utils.RESET);
			getScanner().close();
			System.exit(0);	
		default:
			System.out.println(Utils.RED + "Yanlış giriş yaptınız." + Utils.RESET);
			break;
		}
		cyclePage(key);
		
	}
	
	@Override
	public void runUserEditTweetPage() {
		while(isWhileCheckUserEditTweetPage()==true) {
			applyUserEditTweetPageChoice(getUserEditTweetPageChoice());
		}
		
	}

	@Override
	public int getUserEditTweetPageChoice() {
		System.out.println(Utils.GREEN_BOLD_BRIGHT + "User " + getUserActions().getUser().getUsername() + Utils.RESET);
		System.out.println("1- Post Tweet");
		System.out.println("2- Post Retweet");
		System.out.println("3- Post Comment to Tweet");
		System.out.println("4- Show My Tweets");	
		System.out.println("5- Show My ReTweets");
		System.out.println("6- Edit Tweet");
		System.out.println("7- Remove Tweet");
		System.out.println("8- Remove Comment");
		System.out.println("9- Return User Page");
		System.out.println("0- Exit Program");
		setKey(Utils.getIntegerValue(TwitterMenuImpl.getScanner(), "Input the number of the operation you want to execute: "));
		return getKey();
	}

	@Override
	public void applyUserEditTweetPageChoice(int key) {
		switch (key) {
		case 1:
			getUserActions().postTweet();
			break;
		case 2:
			getUserActions().reTweet();
			break;
		case 3:
			getUserActions().commentTweet();
			break;
		case 4:
			getUserActions().showMyTweets();
			break;
		case 5:
			getUserActions().showMyReTweets();
			break;
		case 6:
			getUserActions().editTweetContext();
			break;
		case 7:
			getUserActions().removeTweet();
			break;
		case 8:
			getUserActions().removeComment();
			break;	
		case 9:
			setWhileCheckUserEditTweetPage(false);
			break;	
		case 0:
			System.out.println(Utils.RED + "Program Terminated..." + Utils.RESET);
			getScanner().close();
			System.exit(0);	
		default:
			System.out.println(Utils.RED + "Yanlış giriş yaptınız." + Utils.RESET);
			break;
		}
		cyclePage(key);
		
	}

	@Override
	public void runUserAccountManagerPage() {
		while(isWhileCheckUserAccountManagerPage()==true) {
			applyUserAccountManagerChoice(getUserAccountManagerChoice());
		}
		
	}

	@Override
	public int getUserAccountManagerChoice() {
		System.out.println(Utils.GREEN_BOLD_BRIGHT + "User " + getUserActions().getUser().getUsername() + Utils.RESET);
		System.out.println("1- Show My Account Info");
		System.out.println("2- Edit Your Name & Surname");
		System.out.println("3- Edit Your Username");
		System.out.println("4- Edit Your Adress");
		System.out.println("5- Edit Your Password");
		System.out.println("6- Edit Your PhoneNumber");
		System.out.println("7- Edit Your Photo");
		System.out.println("8- Edit Your Bio");
		System.out.println("9- Return User Page");
		System.out.println("0- Exit Program");
		setKey(Utils.getIntegerValue(TwitterMenuImpl.getScanner(), "Input the number of the operation you want to execute: "));
		return getKey();
	}

	@Override
	public void applyUserAccountManagerChoice(int key) {
		switch (key) {
		case 1:
			getUserActions().showMyAccount();
			break;
		case 2:
			getUserActions().editNameAndSurname();
			break;
		case 3:
			getUserActions().editUsername();
			break;
		case 4:
			getUserActions().editAdress();
			break;	
		case 5:
			getUserActions().editPassword();
			break;	
		case 6:
			getUserActions().editPhoneNumber();
			break;	
		case 7:
			getUserActions().editPhoto();
			break;
		case 8:
			getUserActions().editBio();
			break;
		case 9:
			setWhileCheckUserAccountManagerPage(false);
			break;	
		case 0:
			System.out.println(Utils.RED + "Program Terminated..." + Utils.RESET);
			getScanner().close();
			System.exit(0);	
		default:
			System.out.println(Utils.RED + "Yanlış giriş yaptınız." + Utils.RESET);
			break;
		}
		cyclePage(key);
		
	}

	public void programManual() {
		System.out.println("\n\u001B[32m***HALİ HAZIRDA DATABASE SINIFINDA OLUŞTURULMUŞ BİLGİLER VAR!***\n\n******KAYITLI USERLAR******: \u001B[0m");
		getDatabase().getUsersList().forEach(u->System.out.println(u));
		System.out.println("\n\u001B[32m******KAYITLI TWEETLER******\u001B[0m");
		getDatabase().getUsersList().stream().map(u->u.getProfile().getTweetsList()).forEach(l-> l.forEach(System.out::println));
		System.out.println("\n\u001B[32m******KAYITLI ADMİNLER******: \u001B[0m");
		getDatabase().getAdminsList().forEach(a->System.out.println(a));
		System.out.println(Utils.YELLOW_BOLD_BRIGHT + "Adminler Mail Adresiyle, Userlar Username ile giriş yapabilir!" + Utils.RESET);
	}
	
	/**
	 * Kullanıcıdan alınan seçime göre geriye bir bilgilendirme mesajı döner. Aynı zamanda kullanıcı yaptığı seçimleri
	 * console üzerinden daha iyi okuyabilmesi için ENTER ile devam et bilgilendirmesi de yapar ve kullanıcıdan bir
	 * console girişi yapmasını bekler.
	 */
	@Override
	public void cyclePage(int key) {
		if (key == 9) {
			System.out.println("\u001B[33mReturned Previous Page...\u001B[0m");
		} else {
			System.out.println("\u001B[32mPress ENTER to continue...\u001B[0m");getScanner().nextLine();	
		}
		
	}
	
	// sadece get methodları
	public static Scanner getScanner() {
		return scanner;
	}

	public static Database getDatabase() {
		return database;
	}

	public static AdminManagerImpl getAdminActions() {
		return adminActions;
	}

	public static UserManagerImpl getUserActions() {
		return userActions;
	}
	
	// get-set methodları
	public boolean isWhileCheckMainPage() {
		return whileCheckMainPage;
	}

	public void setWhileCheckMainPage(boolean whileCheckMainPage) {
		this.whileCheckMainPage = whileCheckMainPage;
	}

	public boolean isWhileCheckAdminPage() {
		return whileCheckAdminPage;
	}

	public void setWhileCheckAdminPage(boolean whileCheckAdminPage) {
		this.whileCheckAdminPage = whileCheckAdminPage;
	}

	public boolean isWhileCheckAdminEditTweetPage() {
		return whileCheckAdminEditTweetPage;
	}

	public void setWhileCheckAdminEditTweetPage(boolean whileCheckAdminEditTweetPage) {
		this.whileCheckAdminEditTweetPage = whileCheckAdminEditTweetPage;
	}

	public boolean isWhileCheckAdminEditUserPage() {
		return whileCheckAdminEditUserPage;
	}

	public void setWhileCheckAdminEditUserPage(boolean whileCheckAdminEditUserPage) {
		this.whileCheckAdminEditUserPage = whileCheckAdminEditUserPage;
	}

	public boolean isWhileCheckAdminBanUserPage() {
		return whileCheckAdminBanUserPage;
	}

	public void setWhileCheckAdminBanUserPage(boolean whileCheckAdminBanUserPage) {
		this.whileCheckAdminBanUserPage = whileCheckAdminBanUserPage;
	}

	public boolean isWhileCheckAdminListPage() {
		return whileCheckAdminListPage;
	}

	public void setWhileCheckAdminListPage(boolean whileCheckAdminListPage) {
		this.whileCheckAdminListPage = whileCheckAdminListPage;
	}
	
	public boolean isWhileCheckUserPage() {
		return whileCheckUserPage;
	}

	public void setWhileCheckUserPage(boolean whileCheckUserPage) {
		this.whileCheckUserPage = whileCheckUserPage;
	}
	
	public boolean isWhileCheckUserLoginPage() {
		return whileCheckUserLoginPage;
	}

	public void setWhileCheckUserLoginPage(boolean whileCheckUserLoginPage) {
		this.whileCheckUserLoginPage = whileCheckUserLoginPage;
	}

	public boolean isWhileCheckUserAccountManagerPage() {
		return whileCheckUserAccountManagerPage;
	}

	public void setWhileCheckUserAccountManagerPage(boolean whileCheckUserAccountManagerPage) {
		this.whileCheckUserAccountManagerPage = whileCheckUserAccountManagerPage;
	}

	public boolean isWhileCheckUserEditTweetPage() {
		return whileCheckUserEditTweetPage;
	}

	public void setWhileCheckUserEditTweetPage(boolean whileCheckUserEditTweetPage) {
		this.whileCheckUserEditTweetPage = whileCheckUserEditTweetPage;
	}
	
	public int getKey() {
		return key;
	}
	
	public void setKey(int key) {
		this.key = key;
	}
}
