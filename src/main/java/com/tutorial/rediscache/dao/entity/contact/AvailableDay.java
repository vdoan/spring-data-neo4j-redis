package com.tutorial.rediscache.dao.entity.contact;

import com.tutorial.rediscache.dao.EntityLabels;
import com.tutorial.rediscache.dao.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Node;

@Node(EntityLabels.AvailableDay)
@Data
public class AvailableDay extends BaseEntity {

    private int dayOfWeek;
    private int timeFrom;
    private int timeTo;
    private Boolean isClosed = false;

}
