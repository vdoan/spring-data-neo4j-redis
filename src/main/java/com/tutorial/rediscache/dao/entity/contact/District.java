package com.tutorial.rediscache.dao.entity.contact;

import com.tutorial.rediscache.dao.EntityLabels;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node(EntityLabels.District)
@Data
public class District {
	@Id
	@GeneratedValue
	private Long id;
	private String name;

}
