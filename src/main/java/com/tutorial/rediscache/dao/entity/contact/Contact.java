package com.tutorial.rediscache.dao.entity.contact;

import com.tutorial.rediscache.constant.ContactType;
import com.tutorial.rediscache.constant.ContactTypeEnum;
import com.tutorial.rediscache.dao.EntityLabels;
import com.tutorial.rediscache.dao.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Node;

import java.time.Instant;

@Node(EntityLabels.Contact)
@Data
public class Contact extends BaseEntity {
	private ContactTypeEnum type;
	private String value;
	private String note;
	private long fromDate;
	private long thruDate;
	private boolean allowSolicitation=false;
	private int extension;
	private boolean verified=false;
	private boolean isPrimary=false;
	private ContactType contactType;


	public static Contact create(ContactTypeEnum type, String value, Boolean isPrimary, String note) {
		Contact entity = new Contact();
		entity.setValue(value);
		entity.setType(type);
		entity.setPrimary(true);
		entity.setNote(note);
		entity.setFromDate(Instant.now().getEpochSecond());
		entity.setCreatedDate(Instant.now().getEpochSecond());
		return entity;
	}

	public boolean getIsPrimary() {
		return isPrimary;
	}
}
