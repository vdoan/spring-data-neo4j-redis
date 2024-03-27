package com.tutorial.rediscache.dao.entity.party;

import com.tutorial.rediscache.constant.ContactTypeEnum;
import com.tutorial.rediscache.constant.PartyType;
import com.tutorial.rediscache.dao.entity.contact.Contact;
import com.tutorial.rediscache.dao.entity.contact.PartyContact;
import com.tutorial.rediscache.dao.entity.contact.PostalAddress;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;


@Data
@EqualsAndHashCode(callSuper=false)
public class User extends Party {

	private String firstName="";
	private String middleName="";
	private String lastName="";
	private String dob;

	public static User create(String firstName, String middleName, String lastName, PostalAddress postalAddress, PartyContact partyContact) {
		User entity = new User();
		entity.setFirstName(firstName);
		entity.setMiddleName(middleName);
		entity.setLastName(lastName);
		entity.setPartyType(PartyType.PERSON);

		if(postalAddress!=null) {
			postalAddress.setIsPrimary(true);
			entity.getAddresses().add(postalAddress);
		}

		return entity;
	}

	public static User create(String firstName, String middleName, String lastName, String phoneNumber, String email, PostalAddress address) {
		User entity = new User();

		entity.setFirstName(firstName);
		entity.setMiddleName(middleName);
		entity.setLastName(lastName);
		entity.setCreatedDate(Instant.now().getEpochSecond());
		address.setIsPrimary(true);
		entity.getAddresses().add(address);

		PartyContact partyContact = null;
		if(phoneNumber!=null || email!=null){
			partyContact = new PartyContact();
		}

		if(phoneNumber!=null){
			Contact contact = Contact.create(ContactTypeEnum.TELECOM_NUMBER, phoneNumber, true,null);
			partyContact.getContacts().add(contact);

		}

		if(email!=null){
			Contact contact = Contact.create(ContactTypeEnum.EMAIL_ADDRESS, email, true,null);
			partyContact.getContacts().add(contact);

		}


		return entity;
	}

}
