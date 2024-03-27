package com.tutorial.rediscache.dao.entity.contact;

import com.tutorial.rediscache.constant.ContactTypeEnum;
import com.tutorial.rediscache.dao.EntityLabels;
import com.tutorial.rediscache.dao.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.time.Instant;

@Node(EntityLabels.City)
@Data
public class City {
	@Id
	@GeneratedValue
	private Long id;
	private String name;

}
