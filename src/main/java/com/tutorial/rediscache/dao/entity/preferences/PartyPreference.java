package com.tutorial.rediscache.dao.entity.preferences;

import com.tutorial.rediscache.dao.EntityLabels;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;


@Node(EntityLabels.PartyPreference)
@Data
public class PartyPreference {


	@Id
	@GeneratedValue
	private Long id;

	private Long partyId;
	private List<String> categories = new ArrayList<>();

	@Relationship(type = EntityLabels.PRIVACY, direction = Relationship.Direction.INCOMING)
	private PartyPrivacy privacy;


}
