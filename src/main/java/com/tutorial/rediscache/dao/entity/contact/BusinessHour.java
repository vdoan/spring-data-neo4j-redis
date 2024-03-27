package com.tutorial.rediscache.dao.entity.contact;

import com.tutorial.rediscache.constant.PartyType;
import com.tutorial.rediscache.constant.Status;
import com.tutorial.rediscache.dao.EntityLabels;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tutorial.rediscache.dao.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node(EntityLabels.BusinessHour)
@Getter
@Setter
public class BusinessHour extends BaseEntity {

    private PartyType type;

    @Relationship(type = EntityLabels.AVAILABLE_DAY)
    private List<AvailableDay> days = new ArrayList<>();


    public void addDay(AvailableDay day){
        this.getDays().add(day);
    }
}
