package com.tutorial.rediscache.dao.entity.party;

import com.tutorial.rediscache.constant.ContactTypeEnum;
import com.tutorial.rediscache.constant.PartyType;
import com.tutorial.rediscache.dao.EntityLabels;
import com.tutorial.rediscache.dao.entity.contact.Contact;
import com.tutorial.rediscache.dao.entity.contact.PartyContact;
import com.tutorial.rediscache.dao.entity.contact.PostalAddress;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper=false)
public class User extends Party {

	private String firstName="";  // sort 1
	private String middleName="";
	private String lastName=""; // sort 2
	private String dob;

	@Relationship(type = EntityLabels.Contact, direction = Relationship.Direction.OUTGOING)
	private List<Contact> contacts;

	public static User createForSearchExample() {
		User user = new User();
		user.setFirstName(null);
		user.setMiddleName(null);
		user.setLastName(null);
		user.setName(null);
		user.setTagname(null);
		user.setHeadline(null);
		user.setAbout(null);
		user.setStatus(null);
		user.setCreatedDate(null);

		return user;
	}
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

		List<Contact> partyContact = null;
		if(phoneNumber!=null || email!=null){
			partyContact = new ArrayList<>();
		}

		if(phoneNumber!=null){
			Contact contact = Contact.create(ContactTypeEnum.TELECOM_NUMBER, phoneNumber, true,null);
			partyContact.add(contact);

		}

		if(email!=null){
			Contact contact = Contact.create(ContactTypeEnum.EMAIL_ADDRESS, email, true,null);
			partyContact.add(contact);

		}
		entity.setContacts(partyContact);

		return entity;
	}

}
