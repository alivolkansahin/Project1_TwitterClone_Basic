package com.volkans.implementations.repository.entities;

/**
 * Bu sınıfın bazı özellikleri hem Tweet hem Comment sınıfları için kullanılacak.
 * Bu yüzden Message sınıfı Sealed keywordü ile sadece bu 2 sınıfın miras almasına izin verir.
 */
public sealed class Message permits Tweet, Comment{
	private String dateCreated;  // LocalDateTime
	private String context;
	private User sender;
	
	public Message(String dateCreated, String context, User sender) {
		super();
		setDateCreated(dateCreated);
		setContext(context);
		setSender(sender);
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	@Override
	public String toString() {
		return "Sender: " + getSender().getUsername() + "\n'" + getContext() + "'\nSent: " + getDateCreated();
	}
	
}
