package com.volkans.implementations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.volkans.implementations.repository.EHashtag;
import com.volkans.implementations.repository.EUserStatus;
import com.volkans.implementations.repository.IUserManager;
import com.volkans.implementations.repository.Utils;
import com.volkans.implementations.repository.entities.Comment;
import com.volkans.implementations.repository.entities.Message;
import com.volkans.implementations.repository.entities.Tweet;
import com.volkans.implementations.repository.entities.User;

public final class UserManagerImpl implements IUserManager{  // final : kalıtım alınamaz
	private List<EHashtag> hashtagList = Arrays.asList(EHashtag.values());
	private User user;
	private Tweet tweetToBeChanged;
	private User userToBeShown;
	private String stringEntry;
	private Comment commentFound;
	private int intEntry;
	private int hashtagIntEntry;
	private boolean check;

	@Override
	public void register() {
		String name = Utils.getStringValue(TwitterMenuImpl.getScanner(), "İsminiz: ");
		String surname = Utils.getStringValue(TwitterMenuImpl.getScanner(), "Soyisminiz: ");
		String username = "";
		String password = "";
		String repassword = "";
		long phoneNumber = 0;
		
		labeledContinue:
		while (!isCheck()) {
			username = Utils.getStringValue(TwitterMenuImpl.getScanner(), "Username: ").toLowerCase();
			for (User user : TwitterMenuImpl.getDatabase().getUsersList()) {
				if(user.getUsername().equals(username)) {
					System.out.println(Utils.RED + "Bu Username Sistemde Kayıtlı - Başka bir Username Seçiniz" + Utils.RESET);
					continue labeledContinue;
				}
			}
			break;
		}	
		while(!isCheck()) {
			password = Utils.getStringValue(TwitterMenuImpl.getScanner(), "Şifre giriniz: ");
			if(password.length()<6) {
				System.out.println(Utils.RED + "Şifreniz 6 karakterden kısa olamaz!" + Utils.RESET);
				continue;
			}
			repassword = Utils.getStringValue(TwitterMenuImpl.getScanner(), "Şifrenizi tekrar giriniz: ");
			if(!password.equals(repassword)) {
				System.out.println(Utils.RED + "Şifreleriniz uyuşmuyor!" + Utils.RESET);
				continue;
			}
			break;
		}
		while(!isCheck()) {
			phoneNumber = Utils.getLongValue(TwitterMenuImpl.getScanner(), "Telefon numarası: ");
			if (String.valueOf(phoneNumber).length()!=10) {
				System.out.println(Utils.RED + "Telefon numaranızı 10 haneli giriniz (Başta 0 yok)" + Utils.RESET);
				continue;
			}
			break;
		}
		
		TwitterMenuImpl.getDatabase().getUsersList().add(new User(name, surname, username, password, String.valueOf(phoneNumber), LocalDate.now().toString()));
		System.out.println(Utils.GREEN_BOLD_BRIGHT + "Register işlemi başarılı! Twitter'a Hoşgeldiniz " + username + Utils.RESET);	
	}
	
	@Override
	public boolean login() {
		labeledBreak:
		while(!isCheck()) {
			setStringEntry(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Username: "));
			if(TwitterMenuImpl.getDatabase().getUsersList().stream().map(u->u.getUsername()).anyMatch(un->un.equalsIgnoreCase(getStringEntry()))==false) {
				System.out.println(Utils.RED + "Böyle bir username kayıtlı değil!" + Utils.RESET);
				return false;
			} else {
				for (User u : TwitterMenuImpl.getDatabase().getUsersList()) {
					if(u.getUsername().equalsIgnoreCase(getStringEntry())) {
						setUser(u);
						if(getUser().getUserStatus().equals(EUserStatus.BANNED)) {
							System.out.println(Utils.RED_BOLD_BRIGHT + "Hesabınız banlanmış! Giriş yapılamaz!" + Utils.RESET);
							return false;
						}
					}
				}
				int sayac = 5;
				while(!isCheck()) {
					setStringEntry(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Şifreniz: "));
					if(!getUser().getPassword().equals(getStringEntry())) {
						System.out.println(Utils.RED + "Şifreniz yanlış!" + Utils.RESET);
						sayac--;
						if(sayac==0) {
							getUser().setUserStatus(EUserStatus.RESTRICTED);
							System.out.println(Utils.YELLOW_BOLD_BRIGHT + "Şifrenizi 5 defa yanlış girdiğiniz için hesabınız kısıtlandı, login olursanız tweet atamazsınız!" + Utils.RESET);
						}
						continue;
					} else {
						System.out.println(Utils.GREEN_BOLD_BRIGHT + "Hoşgeldiniz User " + getUser().getUsername() + Utils.RESET);
						if(getUser().getUserStatus().equals(EUserStatus.RESTRICTED)) {
							System.out.println(Utils.YELLOW_BOLD_BRIGHT + "Hesabınız kısıtlanmış! Tweet Atamazsınız!" + Utils.RESET);
						}
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
			System.out.println(Utils.RED + "Bu ID'de bir tweetiniz bulunmamaktadır..." + Utils.RESET);
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
			System.out.println(Utils.RED + "Bu ID'de bir tweetiniz bulunmamaktadır..." + Utils.RESET);
			return;
		}
		while(!isCheck()) {
			System.out.println("Silmek istediğiniz tweet: " + getTweetToBeChanged().toString());
			setStringEntry(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Onaylıyor musunuz?(Y/N) : ").toUpperCase());
			if(getStringEntry().equals("N")) {
				System.out.println(Utils.RED + "İşlemden vazgeçtiniz." + Utils.RESET);
				return;
			} else if(getStringEntry().equals("Y")){
				for (Tweet tweet : getUser().getProfile().getTweetsList()) {
					if(tweet.equals(getTweetToBeChanged())) {
						getUser().getProfile().getTweetsList().remove(tweet);
						getUser().getProfile().getPostsList().remove(tweet);
						System.out.println(Utils.GREEN + "Tweet Silindi!" + Utils.RESET);
						return;
					}
				}	
			} else {
				System.out.println(Utils.RED + "Yanlış giriş yaptınız." + Utils.RESET);
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
					if(comment.getId().equals(getStringEntry()) && !comment.getSender().equals(getUser())) {
						System.out.println(Utils.RED + "Bu Yorum Size Ait Değil - Silemezsiniz!" + Utils.RESET);
						return;
					} else if(comment.getId().equals(getStringEntry())) {
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
		setUserToBeShown(null);
		setStringEntry(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Profilini Görüntülemek İstediğiniz User'ın 7 haneli ID'sini Girin: "));
		for (User user : TwitterMenuImpl.getDatabase().getUsersList()) {
			if(user.getId().equals(getStringEntry())) {
				setUserToBeShown(user);
				return;
			}
		}
	}
	
	@Override
	public void findTweetById() {
		setTweetToBeChanged(null);
		if(getUser().getProfile().getTweetsList().isEmpty()) {
			System.out.println(Utils.RED + "Tweetiniz yok!" + Utils.RESET);
			return;
		}
		System.out.println("Tweetlerinizin İçeriği ve IDsi: ");
		getUser().getProfile().getTweetsList().forEach(t->{
			System.out.println(t.getContext() + "\n-- Sent: " + t.getDateCreated() + "\n-- ID : " + t.getId());
		});
		setStringEntry(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Editlemek İstediğiniz Tweet'in 6 haneli ID'sini Girin: ")); 
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
		System.out.println(getUser().toString());
	}
			
	@Override
	public void editNameAndSurname() {
		getUser().setName(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Yeni İsim: "));
		getUser().setSurname(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Yeni Soyisim: "));
		System.out.println(Utils.GREEN + "İşlem başarılı!" + Utils.RESET);
		System.out.println(getUser().toString());		
	}

	@Override
	public void editUsername() {
		while (!isCheck()) {
			String username = Utils.getStringValue(TwitterMenuImpl.getScanner(), "Yeni Username: ");
			if(TwitterMenuImpl.getDatabase().getUsersList().stream().map(u->u.getUsername()).anyMatch(un->un.equals(username))==true) {
				System.out.println(Utils.RED + "Böyle bir username'e sahip kullanıcı var!" + Utils.RESET);
				continue;
			}
			getUser().setUsername(username);
			System.out.println(Utils.GREEN + "İşlem başarılı!" + Utils.RESET);
			System.out.println(getUser().toString());	
			return;
		}			
	}

	@Override
	public void editAdress() {
		getUser().getAdress().setCountry(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Yeni Ülke: "));
		getUser().getAdress().setCity(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Yeni Şehir: "));
		getUser().getAdress().setStreet(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Yeni Cadde: "));
		getUser().getAdress().setPostalCode(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Yeni Posta Kodu: "));
		System.out.println(Utils.GREEN + "İşlem başarılı!" + Utils.RESET);
		System.out.println(getUser().getAdress().toString());		
		
	}

	@Override
	public void editPassword() {
		while(!isCheck()) {
			String password = Utils.getStringValue(TwitterMenuImpl.getScanner(), "Yeni Şifreniz: ");
			if(password.length()<6) {
				System.out.println(Utils.RED + "Şifreniz 6 karakterden kısa olamaz!" + Utils.RESET);
				continue;
			}
			String repassword = Utils.getStringValue(TwitterMenuImpl.getScanner(), "Yeni Şifrenizi Tekrar Giriniz: ");		
			if(!password.equals(repassword)) {
				System.out.println(Utils.RED + "Şifreleriniz uyuşmuyor!" + Utils.RESET);
				continue;
			}
			getUser().setPassword(repassword);
			System.out.println(Utils.GREEN + "İşlem başarılı!" + Utils.RESET);
			System.out.println("Yeni Şifre: " + getUser().getPassword());		
			return;
		}
	}

	@Override
	public void editPhoneNumber() { //////////
		getUser().setPhoneNumber(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Yeni Telefon Numarası: "));
		System.out.println(Utils.GREEN + "İşlem başarılı!" + Utils.RESET);
		System.out.println(getUser().toString());
	}
	
	@Override
	public void editPhoto() {
		getUser().getProfile().setPhoto(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Yeni Profil Resmi: "));
		System.out.println(Utils.GREEN + "İşlem başarılı!" + Utils.RESET);
		System.out.println(getUser().getProfile().toString());
		
	}

	@Override
	public void editBio() {
		getUser().getProfile().setBio(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Yeni Bio: "));
		System.out.println(Utils.GREEN + "İşlem başarılı!" + Utils.RESET);
		System.out.println(getUser().getProfile().toString());
	}

	@Override
	public void postTweet() {
		if(getUser().getUserStatus().equals(EUserStatus.RESTRICTED)) {
			System.out.println(Utils.YELLOW_BOLD_BRIGHT + "Hesabınız kısıtlanmış! Tweet Atamazsınız!" + Utils.RESET);
			return;
		}
		String context = Utils.getStringValue(TwitterMenuImpl.getScanner(), "Tweet İçeriği Giriniz: ");
		while(!isCheck()) {	
			String hashtagApproval = Utils.getStringValue(TwitterMenuImpl.getScanner(), "Hashtag girilecek mi?(Y/N) :  ").toUpperCase();
			if(hashtagApproval.equals("N")) {
				getUser().getProfile().getTweetsList().add(new Tweet(LocalDateTime.now().withNano(0).toString(), context, getUser()));
				getUser().getProfile().getPostsList().add(getUser().getProfile().getTweetsList().get(getUser().getProfile().getTweetsList().size()-1));
				System.out.println(Utils.GREEN + "Tweet Oluşturuldu" + Utils.RESET);
				System.out.println(getUser().getProfile().getPostsList().get(getUser().getProfile().getPostsList().size()-1));
				return;
			} else if(hashtagApproval.equals("Y")){
				List<EHashtag> tweetHashtagList = new ArrayList<>();		
				labeledContinue:
				while(!isCheck()) {
					getHashtagList().forEach(hashtag->System.out.println(hashtag.ordinal() + "-" + hashtag.name()));
					try {
						tweetHashtagList.add(getHashtagList().get(Utils.getIntegerValue(TwitterMenuImpl.getScanner(), "Girmek istediğiniz hashtag numarası: ")));
					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.println(Utils.RED + "Sistemde kayıtlı olan Hashtag Numarası Giriniz!" + Utils.RESET);
						continue;
					}	
					while (!isCheck()) {
						setHashtagIntEntry(Utils.getIntegerValue(TwitterMenuImpl.getScanner(), "Başka girilecek mi? (1:Evet, 0:Hayır): "));
						if(getHashtagIntEntry()==1) {
							continue labeledContinue;
						} else if(getHashtagIntEntry()==0) {
							getUser().getProfile().getTweetsList().add(new Tweet(LocalDateTime.now().withNano(0).toString(), context, getUser(),tweetHashtagList));
							getUser().getProfile().getPostsList().add(getUser().getProfile().getTweetsList().get(getUser().getProfile().getTweetsList().size()-1));
							System.out.println(Utils.GREEN + "Tweet Oluşturuldu" + Utils.RESET);
							System.out.println(getUser().getProfile().getPostsList().get(getUser().getProfile().getPostsList().size()-1));
							return;
						} else {
							System.out.println(Utils.RED + "Yanlış Seçim Yaptınız" + Utils.RESET);
							continue;
						}
					}	
				}			
			} else {
				System.out.println(Utils.RED + "Yanlış giriş yaptınız." + Utils.RESET);
			}
		}
	}
	
	@Override
	public void reTweet() {
		if(getUser().getUserStatus().equals(EUserStatus.RESTRICTED)) {
			System.out.println(Utils.YELLOW_BOLD_BRIGHT + "Hesabınız kısıtlanmış! Tweet Atamazsınız!" + Utils.RESET);
			return;
		}
		compactTweetList();
		setTweetToBeChanged(null);
		setStringEntry(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Retweet Yapacağınız Tweet'in 6 haneli ID'sini Girin: ")); 
		labeledBreak:
		for (User user : TwitterMenuImpl.getDatabase().getUsersList()) {
			for (Tweet tweet : user.getProfile().getTweetsList()) {
				if(tweet.getId().equals(getStringEntry())) {
					setUserToBeShown(tweet.getSender());
					if(checkUserCondition()) {
						return;
					}
					setTweetToBeChanged(tweet);
					break labeledBreak;
				}
			}
		}
		if(getTweetToBeChanged()==null) {
			System.out.println(Utils.RED + "Bu ID'de bir Tweet bulunmamaktadır..." + Utils.RESET);
			return;
		}
		getUser().getProfile().getReTweetsList().add(getTweetToBeChanged());
		getUser().getProfile().getPostsList().add(getTweetToBeChanged());
		System.out.println(Utils.GREEN + "ReTweet Oluşturuldu" + Utils.RESET);
		System.out.println(getUser().getProfile().getPostsList().get(getUser().getProfile().getPostsList().size()-1));
	}
	
	@Override
	public void commentTweet() {
		if(getUser().getUserStatus().equals(EUserStatus.RESTRICTED)) {
			System.out.println(Utils.YELLOW_BOLD_BRIGHT + "Hesabınız kısıtlanmış! Tweet Atamazsınız!" + Utils.RESET);
			return;
		}
		compactTweetList();
		setTweetToBeChanged(null);
		setStringEntry(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Yorum Yapılacak Tweet'in 6 haneli ID'sini Girin: ")); 
		labeledBreak:
		for (User user : TwitterMenuImpl.getDatabase().getUsersList()) {
			for (Tweet tweet : user.getProfile().getTweetsList()) {
				if(tweet.getId().equals(getStringEntry())) {
					setUserToBeShown(tweet.getSender());
					if(checkUserCondition()) {
						return;
					}
					setTweetToBeChanged(tweet);
					break labeledBreak;
				}
			}
		}
		if(getTweetToBeChanged()==null) {
			System.out.println(Utils.RED + "Bu ID'de bir Tweet bulunmamaktadır..." + Utils.RESET);
			return;
		}
		String context = Utils.getStringValue(TwitterMenuImpl.getScanner(), "Comment İçeriği Giriniz: ");	
		while(!isCheck()) {	
			String hashtagApproval = Utils.getStringValue(TwitterMenuImpl.getScanner(), "Hashtag girilecek mi?(Y/N) :  ").toUpperCase();
			if(hashtagApproval.equals("N")) {
				for (User user : TwitterMenuImpl.getDatabase().getUsersList()) {
					for (Tweet tweet : user.getProfile().getTweetsList()) {
						if(tweet.getId().equals(getStringEntry())) {
							tweet.getCommentsList().add(new Comment(LocalDateTime.now().withNano(0).toString(), context, getUser()));
							System.out.println(Utils.GREEN + "Comment Oluşturuldu" + Utils.RESET);
							System.out.println(tweet.getCommentsList().get(tweet.getCommentsList().size()-1)); 
							return;
						}
					}
				}	
			} else if(hashtagApproval.equals("Y")){
				List<EHashtag> commentHashtagList = new ArrayList<>();
				labeledContinue:
				while(!isCheck()) {
					getHashtagList().forEach(hashtag->System.out.println(hashtag.ordinal() + "-" + hashtag.name()));
					try {
						commentHashtagList.add(getHashtagList().get(Utils.getIntegerValue(TwitterMenuImpl.getScanner(), "Girmek istediğiniz hashtag numarası: ")));
					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.println(Utils.RED + "Sistemde kayıtlı olan Hashtag Numarası Giriniz!" + Utils.RESET);
						continue;
					}
					while(!isCheck()) {
						setHashtagIntEntry(Utils.getIntegerValue(TwitterMenuImpl.getScanner(), "Başka girilecek mi? (1:Evet, 0:Hayır): "));
						if(getHashtagIntEntry()==1) {
							continue labeledContinue;
						} else if(getHashtagIntEntry()==0) {
							for (User user : TwitterMenuImpl.getDatabase().getUsersList()) {
								for (Tweet tweet : user.getProfile().getTweetsList()) {
									if(tweet.getId().equals(getStringEntry())) {
										tweet.getCommentsList().add(new Comment(LocalDateTime.now().withNano(0).toString(), context, getUser(),commentHashtagList));
										System.out.println(Utils.GREEN + "Comment Oluşturuldu" + Utils.RESET);
										System.out.println(tweet.getCommentsList().get(tweet.getCommentsList().size()-1)); 
										return;
									}
								}
							}
						} else {
							System.out.println(Utils.RED + "Yanlış Seçim Yaptınız" + Utils.RESET);
							continue;
						}
					}
					
				}	
			} else {
				System.out.println(Utils.RED + "Yanlış giriş yaptınız." + Utils.RESET);
			}
		}	
	}

	@Override
	public void showMyTweets() {
		if(getUser().getProfile().getTweetsList().isEmpty()) {
			System.out.println(Utils.RED + "Tweetiniz yok!" + Utils.RESET);
			return;
		}
		getUser().getProfile().getTweetsList().forEach(System.out::println);
	}
	
	@Override
	public void showMyReTweets() {
		if(getUser().getProfile().getReTweetsList().isEmpty()) {
			System.out.println(Utils.RED + "ReTweetiniz yok!"+ Utils.RESET);
			return;
		}
		getUser().getProfile().getReTweetsList().forEach(System.out::println);
	}

	@Override
	public void showProfile() {
		findUserById();
		if(checkUserCondition()) {
			return;
		}
		System.out.println(getUserToBeShown().toString());
		System.out.println(getUserToBeShown().getProfile().toString());
	}
	
	@Override
	public void showMyProfile() {
		System.out.println(getUser().getProfile().toString());	
	}

	@Override
	public void followUser() {
		findUserById();
		if(checkUserCondition()) {
			return;
		}
		getUser().getProfile().getFollowing().add(getUserToBeShown());
		getUserToBeShown().getProfile().getFollowers().add(getUser());
		System.out.println(Utils.GREEN  + getUserToBeShown().getUsername() + " Takip Edildi!"+ Utils.RESET);
	}

	@Override
	public void unfollowUser() {
		if(getUser().getProfile().getFollowing().isEmpty()){
			System.out.println(Utils.RED + "Kimseyi takip etmiyorsun!"+ Utils.RESET);
			return;
		}
		findUserById();
		if(getUserToBeShown()==null) {
			System.out.println(Utils.RED + "Bu ID'de bir user bulunmamaktadır..."+ Utils.RESET);
			return;
		}
		if(!getUser().getProfile().getFollowing().contains(getUserToBeShown())) {
			System.out.println(Utils.RED + "Bu kullanıcıyı zaten takip etmiyorsun!" + Utils.RESET);
			return;
		}
		getUser().getProfile().getFollowing().remove(getUserToBeShown());
		getUserToBeShown().getProfile().getFollowers().remove(getUser());
		System.out.println(Utils.GREEN  + getUserToBeShown().getUsername() + " Takipten Çıkarıldı!"+ Utils.RESET);
	}
	
	@Override
	public void sendDM() {
		findUserById();
		if(checkUserCondition()) {
			return;
		}
		getUserToBeShown().toString();
		setStringEntry(Utils.getStringValue(TwitterMenuImpl.getScanner(), "Mesaj İçeriği Giriniz: "));
		getUserToBeShown().getDmBox().put(getUser(), getUserToBeShown().getDmBox().getOrDefault(getUser(), new ArrayList<>())); // .add(new Message(LocalDateTime.now().toString(), getStringEntry(), getUser()))
		getUserToBeShown().getDmBox().get(getUser()).add(new Message(LocalDateTime.now().withNano(0).toString(), getStringEntry(), getUser()));
		System.out.println(Utils.GREEN + "Mesaj Yollandı!"+ Utils.RESET);	
	}

	@Override
	public void checkDM() {
		if(getUser().getDmBox().isEmpty()) {
			System.out.println(Utils.RED + "Mesaj kutunuz boş!"+ Utils.RESET);
			return;
		}
		getUser().getDmBox().forEach((user,mesajListesi)-> mesajListesi.forEach(mesaj-> System.out.println("\n" + mesaj)));	

	}

	@Override
	public void blockUser() {
		findUserById();
		if(checkUserCondition()) {
			return;
		}
		getUser().getBlockedList().add(getUserToBeShown());
		if(getUser().getProfile().getFollowing().contains(getUserToBeShown())) {
			getUser().getProfile().getFollowing().remove(getUserToBeShown());
			getUserToBeShown().getProfile().getFollowers().remove(getUser());
		}
		if(getUser().getProfile().getFollowers().contains(getUserToBeShown())) {
			getUser().getProfile().getFollowers().remove(getUserToBeShown());
			getUserToBeShown().getProfile().getFollowing().remove(getUser());
		}
		System.out.println(Utils.GREEN + getUserToBeShown().getUsername() + " nicknameli userı engellediniz!"+ Utils.RESET);
		System.out.println(Utils.GREEN + "Aynı Zamanda Takip Edilen ve Takipçiler Listesinden de çıkarıldı! "+ Utils.RESET);
		
	}

	@Override
	public void unBlockUser() {
		if(getUser().getBlockedList().isEmpty()) {
			System.out.println(Utils.RED + "Engellediğiniz Kimse Yok!"+ Utils.RESET);
			return;
		}
		System.out.println("Blok Listenizdeki Userlar:");
		getUser().getBlockedList().forEach(u->System.out.println(u.getUsername() + " - ID:" + u.getId()));
		String id = Utils.getStringValue(TwitterMenuImpl.getScanner(), "Blockunu Kaldırmak İstediğiniz User'ın 7 haneli ID'sini Girin: ");
		for (User user : getUser().getBlockedList()) {
			if(user.getId().equals(id)) {
				getUser().getBlockedList().remove(user);
				System.out.println(Utils.GREEN  + user.getUsername() + " Nickname'li Userın Engeli Kaldırıldı!"+ Utils.RESET);
				return;
			}
		}
		System.out.println(Utils.RED + "Bu ID'de bir user bulunmamaktadır..."+ Utils.RESET);
	}
	
	@Override
	public void compactUserList() {
		System.out.println(Utils.GREEN + "Yardımcı Olması Amaçlı Compact User List"+ Utils.RESET);
		TwitterMenuImpl.getDatabase().getUsersList().forEach(u-> System.out.println("Username: " + u.getUsername() + " - RegisterDate: " + u.getRegisterDate() + " - Status: " + u.getUserStatus() + " - ID: " + u.getId()));
	}
	
	@Override
	public void compactTweetList() {
		System.out.println(Utils.GREEN + "Yardımcı Olması Amaçlı Compact Tweet List"+ Utils.RESET);
		TwitterMenuImpl.getDatabase().getUsersList().stream().map(u->u.getProfile().getTweetsList()).forEach(tweetList->{
			for (Tweet tweet : tweetList) {
				System.out.println("Sender: " + tweet.getSender().getUsername() + " - Context: '" + tweet.getContext() + "' - ID: " + tweet.getId());
			}
		});
	}
	
	@Override
	public void compactCommentList() {
		System.out.println(Utils.GREEN + "Yardımcı Olması Amaçlı Compact Comment List"+ Utils.RESET);
		TwitterMenuImpl.getDatabase().getUsersList().stream().map(u->u.getProfile().getTweetsList()).forEach(tweetList->{
			for (Tweet tweet : tweetList) {
				for (Comment comment : tweet.getCommentsList()) {
					System.out.println("Sender: " + comment.getSender().getUsername() + " - Context: '" + comment.getContext() + "' - ID: " + comment.getId());
				}
			}
		});
	}
	
	/**
	 * User bulunmuş mu, bloklanmış mı, banlanmış mı şartlarını kontrol eder. 
	 * Eğer bunlardan birisi varsa true döner, yoksa false döner. Çağrıldığı Method içerisinde
	 * bir if kontrolüne alınarak if'in içine girmesi durumunda methodu erken sonlandırıp 
	 * performans kazancı sağlamaktadır.
	 * @return
	 */
	public boolean checkUserCondition() {
		if(getUserToBeShown()==null) {
			System.out.println(Utils.RED + "Bu ID'de bir user bulunmamaktadır..."+ Utils.RESET);
			return true;
		} else if(getUserToBeShown().getUserStatus().equals(EUserStatus.BANNED)) {
			System.out.println(Utils.RED + "Bu Kullanıcı Banlanmış!"+ Utils.RESET);
			return true;
		} else if(getUser().getBlockedList().contains(getUserToBeShown())) {
			System.out.println(Utils.RED + "Bu User Sizin Block Listenizde!"+ Utils.RESET);
			return true;
		} else if(getUserToBeShown().getBlockedList().contains(getUser())) {
			System.out.println(Utils.RED + "Bu User Sizi Blocklamış!"+ Utils.RESET);
			return true;
		} else {
			return false;
		}
	}
	
	// getters-setters

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<EHashtag> getHashtagList() {
		return hashtagList;
	}
	
	public Tweet getTweetToBeChanged() {
		return tweetToBeChanged;
	}

	public void setTweetToBeChanged(Tweet tweetToBeChanged) {
		this.tweetToBeChanged = tweetToBeChanged;
	}

	public User getUserToBeShown() {
		return userToBeShown;
	}

	public void setUserToBeShown(User userToBeShown) {
		this.userToBeShown = userToBeShown;
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

	public Comment getCommentFound() {
		return commentFound;
	}

	public void setCommentFound(Comment commentFound) {
		this.commentFound = commentFound;
	}
		
	public int getHashtagIntEntry() {
		return hashtagIntEntry;
	}

	public void setHashtagIntEntry(int hashtagIntEntry) {
		this.hashtagIntEntry = hashtagIntEntry;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

}
