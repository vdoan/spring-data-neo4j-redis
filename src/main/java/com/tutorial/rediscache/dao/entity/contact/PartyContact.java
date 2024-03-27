package com.tutorial.rediscache.dao.entity.contact;

import com.tutorial.rediscache.constant.PartyType;
import com.tutorial.rediscache.dao.EntityLabels;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;


@Data
@Node(EntityLabels.PartyContact)
public class PartyContact {
	@Id
	@GeneratedValue
	private Long id;

	private Long createdDate;
	private Long lastUpdatedDate;
	private Long partyId;

	private PartyType type;

	@Relationship(type = EntityLabels.HAS_CONTACT, direction = Relationship.Direction.OUTGOING)
	protected List<Contact> contacts = new ArrayList<>();

}
