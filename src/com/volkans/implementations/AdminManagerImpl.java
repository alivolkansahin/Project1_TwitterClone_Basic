package com.volkans.implementations;

import com.volkans.implementations.repository.EUserStatus;
import com.volkans.implementations.repository.IAdminManager;
import com.volkans.implementations.repository.Utils;
import com.volkans.implementations.repository.entities.Admin;
import com.volkans.implementations.repository.entities.Comment;
import com.volkans.implementations.repository.entities.Tweet;
import com.volkans.implementations.repository.entities.User;

public final class AdminManagerImpl implements IAdminManager{ // final : kalıtım alınamaz
	private Admin admin;
	private Tweet tweetToBeChanged;
	private Comment commentFound;
	private User userToBeChanged;
	private String stringEntry;
	private int intEntry;
	private boolean check;

	@Override
	public boolean login() {
		labeledBreak:
		while(!isCheck()) {
			setStringEntry(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Mail Adresiniz: "));
			if(TwitterMenuImpl.getDatabase().getAdminsList().stream().map(a->a.getMail()).anyMatch(m->m.equalsIgnoreCase(getStringEntry()))==false) {
				System.out.println(Utils.RED + "Bu Mail Adresinde Admin Kayıtlı Değil " + Utils.RESET);
				return false;
			} else {
				TwitterMenuImpl.getDatabase().getAdminsList().forEach(a->{
					if(a.getMail().equalsIgnoreCase(getStringEntry())) {
						setAdmin(a); 
					}
				});
				while(!isCheck()) {
					setStringEntry(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Şifreniz: "));
					if(!getAdmin().getPassword().equals(getStringEntry())) {
						System.out.println(Utils.RED + "Şifreniz yanlış!" + Utils.RESET);
						continue;
					} else {
						System.out.println(Utils.GREEN_BOLD_BRIGHT + "Hoşgeldiniz Admin " + getAdmin().getUsername() + Utils.RESET);
						break labeledBreak;
					}
				}
			}
		}
		return true;
	}

	@Override
	public void editTweetContext() { 
		findTweetById();
		if(getTweetToBeChanged()==null) {
			System.out.println(Utils.RED + "Bu ID'de bir tweet bulunmamaktadır..." + Utils.RESET);
			return;
		}
		setStringEntry(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Mesajı değiştireceğiniz metini giriniz: "));
		getTweetToBeChanged().setContext(getStringEntry());	
		System.out.println(Utils.GREEN + "Tweet Editlendi!" + Utils.RESET);
		System.out.println(getTweetToBeChanged());
	}

	@Override
	public void removeTweet() {
		findTweetById();
		if(getTweetToBeChanged()==null) {
			System.out.println(Utils.RED + "Bu ID'de bir tweet bulunmamaktadır..." + Utils.RESET);
			return;
		}
		for (User user : TwitterMenuImpl.getDatabase().getUsersList()) {
			for (Tweet tweet : user.getProfile().getTweetsList()) {
				if(tweet.equals(getTweetToBeChanged())) {
					user.getProfile().getTweetsList().remove(tweet);
					user.getProfile().getPostsList().remove(tweet);
					System.out.println(Utils.GREEN + "Tweet Silindi!" + Utils.RESET);
					return;
				}
			}
		}
	}

	@Override
	public void removeComment() {
		compactCommentList();
		setStringEntry(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Silmek İstediğiniz Yorumun 5 haneli ID'sini Girin: "));
		for (User user : TwitterMenuImpl.getDatabase().getUsersList()) {
			for (Tweet tweet : user.getProfile().getTweetsList()) {
				for (Comment comment : tweet.getCommentsList()) {
					if(comment.getId().equals(getStringEntry())) {
						tweet.getCommentsList().remove(comment);
						System.out.println(Utils.GREEN + "Yorum Silindi!" + Utils.RESET);
						return;
					}
				}
			}
		}
		System.out.println(Utils.RED + "Bu ID'de bir comment bulunmamaktadır" + Utils.RESET);
	}

	@Override
	public void findUserById() {
		compactUserList();
		setUserToBeChanged(null);
		setStringEntry(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Editlemek İstediğiniz User'ın 7 haneli ID'sini Girin: ")); 
		for (User user : TwitterMenuImpl.getDatabase().getUsersList()) {
			if(user.getId().equals(getStringEntry())) {
				setUserToBeChanged(user);
				return;
			}
		}
	}
	
	@Override
	public void findTweetById() {
		compactTweetList();
		setTweetToBeChanged(null);
		setStringEntry(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Silmek İstediğiniz Tweet'in 6 haneli ID'sini Girin: "));
		for (User user : TwitterMenuImpl.getDatabase().getUsersList()) {
			for (Tweet tweet : user.getProfile().getTweetsList()) {
				if(tweet.getId().equals(getStringEntry())) {
					setTweetToBeChanged(tweet);
					return;
				}
			}
		}
	} 
	
	@Override
	public void findCommentById() {
		compactCommentList();
		setCommentFound(null);
		setStringEntry(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Yorumu Görüntülemek İstediğiniz Yorumun 5 haneli ID'sini Girin: "));
		for (User user : TwitterMenuImpl.getDatabase().getUsersList()) {
			for (Tweet tweet : user.getProfile().getTweetsList()) {
				for (Comment comment : tweet.getCommentsList()) {
					if(comment.getId().equals(getStringEntry())) {
						setCommentFound(comment);
						return;
					}
				}
			}
		}
	}
	
	@Override
	public void showMyAccount() {
		System.out.println(getAdmin().toString());	
	}
	
	@Override
	public void editNameAndSurname() {
		findUserById();
		if(getUserToBeChanged()==null) {
			System.out.println(Utils.RED + "Bu ID'de bir user bulunmamaktadır..." + Utils.RESET);
			return;
		}
		getUserToBeChanged().setName(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Yeni İsim: "));
		getUserToBeChanged().setSurname(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Yeni Soyisim: "));
		System.out.println(Utils.GREEN + "İşlem başarılı!" + Utils.RESET);
		System.out.println(getUserToBeChanged().toString());	
	}

	@Override
	public void editUsername() {
		findUserById();
		if(getUserToBeChanged()==null) {
			System.out.println(Utils.RED + "Bu ID'de bir user bulunmamaktadır..." + Utils.RESET);
			return;
		}
		while (!isCheck()) {
			setStringEntry(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Yeni Username: "));
			if(TwitterMenuImpl.getDatabase().getUsersList().stream().map(u->u.getUsername()).anyMatch(un->un.equals(getStringEntry()))==true) {
				System.out.println(Utils.RED + "Böyle bir username'e sahip kullanıcı var!" + Utils.RESET);
				continue;
			}
			getUserToBeChanged().setUsername(getStringEntry());
			System.out.println(Utils.GREEN + "İşlem başarılı!" + Utils.RESET);
			System.out.println(getUserToBeChanged().toString());
			return;
		}			
	}

	@Override
	public void editAdress() {
		findUserById();
		if(getUserToBeChanged()==null) {
			System.out.println(Utils.RED + "Bu ID'de bir user bulunmamaktadır..." + Utils.RESET);
			return;
		}
		getUserToBeChanged().getAdress().setCountry(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Yeni Ülke: "));
		getUserToBeChanged().getAdress().setCity(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Yeni Şehir: "));
		getUserToBeChanged().getAdress().setStreet(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Yeni Cadde: "));
		getUserToBeChanged().getAdress().setPostalCode(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Yeni Posta Kodu: "));
		System.out.println(Utils.GREEN + "İşlem başarılı!" + Utils.RESET);
		System.out.println(getUserToBeChanged().getAdress().toString());			
	}

	@Override
	public void editPassword() { // admin için repassword kontrolü yapmaya gerek yok, admin o !
		findUserById();
		if(getUserToBeChanged()==null) {
			System.out.println(Utils.RED + "Bu ID'de bir user bulunmamaktadır..." + Utils.RESET);
			return;
		}
		getUserToBeChanged().setPassword(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Yeni Şifre: "));
		System.out.println(Utils.GREEN + "İşlem başarılı!" + Utils.RESET);
		System.out.println("Yeni Şifre: " + getUserToBeChanged().getPassword());		
	}

	@Override
	public void editPhoneNumber() {
		findUserById();
		if(getUserToBeChanged()==null) {
			System.out.println(Utils.RED + "Bu ID'de bir user bulunmamaktadır..." + Utils.RESET);
			return;
		}
		getUserToBeChanged().setPhoneNumber(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Yeni Telefon Numarası: "));
		System.out.println(Utils.GREEN + "İşlem başarılı!" + Utils.RESET);
		System.out.println(getUserToBeChanged().toString());		
	}
	
	@Override
	public void editPhoto() {
		findUserById();
		if(getUserToBeChanged()==null) {
			System.out.println(Utils.RED + "Bu ID'de bir user bulunmamaktadır..." + Utils.RESET);
			return;
		}
		getUserToBeChanged().getProfile().setPhoto(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Yeni Profil Resmi: "));
		System.out.println(Utils.GREEN + "İşlem başarılı!" + Utils.RESET);
		System.out.println(getUserToBeChanged().getProfile().toString());	
	}

	@Override
	public void editBio() {
		findUserById();
		if(getUserToBeChanged()==null) {
			System.out.println(Utils.RED + "Bu ID'de bir user bulunmamaktadır..." + Utils.RESET);
			return;
		}
		getUserToBeChanged().getProfile().setBio(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Yeni Bio: "));
		System.out.println(Utils.GREEN + "İşlem başarılı!" + Utils.RESET);
		System.out.println(getUserToBeChanged().getProfile().toString());	
	}

	@Override
	public void banUser() {
		findUserById();
		if(getUserToBeChanged()==null) {
			System.out.println(Utils.RED + "Bu ID'de bir user bulunmamaktadır..." + Utils.RESET);
			return;
		} else if(getUserToBeChanged().getUserStatus().equals(EUserStatus.BANNED)) {
			System.out.println(Utils.RED + "Kullanıcı zaten banlı !" + Utils.RESET);
			return;
		}
		getUserToBeChanged().setUserStatus(EUserStatus.BANNED);
		System.out.println(Utils.GREEN + "Kullanıcı banlanmıştır!" + Utils.RESET);
		System.out.println(getUserToBeChanged().toString());		
	}

	@Override
	public void unBanUser() {
		findUserById();
		if(getUserToBeChanged()==null) {
			System.out.println(Utils.RED + "Bu ID'de bir user bulunmamaktadır..." + Utils.RESET);
			return;
		} else if(!getUserToBeChanged().getUserStatus().equals(EUserStatus.BANNED)) {
			System.out.println(Utils.RED + "Kullanıcı zaten banlı değil !" + Utils.RESET);
			return;
		}
		getUserToBeChanged().setUserStatus(EUserStatus.ACTIVE);
		System.out.println(Utils.GREEN + "Kullanıcının banı kaldırılmıştır!" + Utils.RESET);
		System.out.println(getUserToBeChanged().toString());		
	}

	@Override
	public void restrictUser() {
		findUserById();
		if(getUserToBeChanged()==null) {
			System.out.println(Utils.RED + "Bu ID'de bir user bulunmamaktadır..." + Utils.RESET);
			return;
		} else if(getUserToBeChanged().getUserStatus().equals(EUserStatus.RESTRICTED)) {
			System.out.println(Utils.RED + "Kullanıcı zaten daha önceden kısıtlanmış !" + Utils.RESET);
			return;
		}
		getUserToBeChanged().setUserStatus(EUserStatus.RESTRICTED);
		System.out.println(Utils.GREEN + "Kullanıcı kısıtlanmıştır!" + Utils.RESET);
		System.out.println(getUserToBeChanged().toString());			
	}

	@Override
	public void unRestrictUser() {
		findUserById();
		if(getUserToBeChanged()==null) {
			System.out.println(Utils.RED + "Bu ID'de bir user bulunmamaktadır..." + Utils.RESET);
			return;
		} else if(!getUserToBeChanged().getUserStatus().equals(EUserStatus.RESTRICTED)) {
			System.out.println(Utils.RED + "Bu kullanıcı zaten kısıtlı değil !" + Utils.RESET);
			return;
		}
		getUserToBeChanged().setUserStatus(EUserStatus.ACTIVE);
		System.out.println(Utils.GREEN + "Kullanıcının kısıtlanması kaldırılmıştır!" + Utils.RESET);
		System.out.println(getUserToBeChanged().toString());			
	}
	
	

	@Override
	public void listAllUser() {
		TwitterMenuImpl.getDatabase().getUsersList().stream().forEach(System.out::println);
	}

	@Override
	public void listAllProfiles() { 
		TwitterMenuImpl.getDatabase().getUsersList().stream().forEach(u-> System.out.println("\n" + Utils.YELLOW + u.getUsername() + "\n" + Utils.RESET + u.getProfile()));
	}

	@Override
	public void listBannedUsers() { 
		if(!TwitterMenuImpl.getDatabase().getUsersList().stream().anyMatch(u->u.getUserStatus().equals(EUserStatus.BANNED))) {
			System.out.println(Utils.RED + "Banlı Kimse Yok!" + Utils.RESET);
			return;
		}
		TwitterMenuImpl.getDatabase().getUsersList().stream().filter(u->u.getUserStatus().equals(EUserStatus.BANNED)).forEach(System.out::println);	
	}

	@Override
	public void listRestrictedUsers() { 
		if(!TwitterMenuImpl.getDatabase().getUsersList().stream().anyMatch(u->u.getUserStatus().equals(EUserStatus.RESTRICTED))) {
			System.out.println(Utils.RED + "Kısıtlı Kimse Yok!" + Utils.RESET);
			return;
		}
		TwitterMenuImpl.getDatabase().getUsersList().stream().filter(u->u.getUserStatus().equals(EUserStatus.RESTRICTED)).forEach(System.out::println);
	}
	
	@Override
	public void compactUserList() {
		System.out.println(Utils.GREEN + "Yardımcı Olması Amaçlı Compact User List" + Utils.RESET);
		TwitterMenuImpl.getDatabase().getUsersList().forEach(u-> System.out.println("Username: " + u.getUsername() + " - RegisterDate: " + u.getRegisterDate() + " - Status: " + u.getUserStatus() + " - ID: " + u.getId()));
	}
	
	@Override
	public void compactTweetList() {
		System.out.println(Utils.GREEN + "Yardımcı Olması Amaçlı Compact Tweet List" + Utils.RESET);
		TwitterMenuImpl.getDatabase().getUsersList().stream().map(u->u.getProfile().getTweetsList()).forEach(tweetList->{
			for (Tweet tweet : tweetList) {
				System.out.println("Sender: " + tweet.getSender().getUsername() + " - Context: " + tweet.getContext() + " - ID: " + tweet.getId());//+ " - Context: " + tweet.getContext() + " - ID: " + tweet.getId()
			}
		});
	}
	
	@Override
	public void compactCommentList() {
		System.out.println(Utils.GREEN + "Yardımcı Olması Amaçlı Compact Comment List" + Utils.RESET);
		TwitterMenuImpl.getDatabase().getUsersList().stream().map(u->u.getProfile().getTweetsList()).forEach(tweetList->{
			for (Tweet tweet : tweetList) {
				for (Comment comment : tweet.getCommentsList()) {
					System.out.println("Sender: " + comment.getSender().getUsername() + " - Context: " + comment.getContext() + " - ID: " + comment.getId());
				}
			}
		});
	}
	
	// getters-setters

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Tweet getTweetToBeChanged() {
		return tweetToBeChanged;
	}

	public void setTweetToBeChanged(Tweet tweetToBeChanged) {
		this.tweetToBeChanged = tweetToBeChanged;
	}

	public Comment getCommentFound() {
		return commentFound;
	}

	public void setCommentFound(Comment commentFound) {
		this.commentFound = commentFound;
	}

	public User getUserToBeChanged() {
		return userToBeChanged;
	}

	public void setUserToBeChanged(User userToBeChanged) {
		this.userToBeChanged = userToBeChanged;
	}

	public String getStringEntry() {
		return stringEntry;
	}

	public void setStringEntry(String stringEntry) {
		this.stringEntry = stringEntry;
	}

	public int getIntEntry() {
		return intEntry;
	}

	public void setIntEntry(int intEntry) {
		this.intEntry = intEntry;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}		
	
}
