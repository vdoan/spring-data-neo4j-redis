package com.tutorial.rediscache.dao.entity.party;


import com.maxmind.geoip2.record.Postal;
import com.tutorial.rediscache.constant.*;
import com.tutorial.rediscache.dao.EntityLabels;
import com.tutorial.rediscache.dao.entity.BaseEntity;
import com.tutorial.rediscache.dao.entity.contact.PostalAddress;
import com.tutorial.rediscache.dao.entity.preferences.PartyPreference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.annotation.Transient;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Party extends BaseEntity {

    private String tagname = "";
    private PartyType partyType;
    private String headline = "";
    private String about = "";
    private String preferredLocale;
    @Transient
    private PostalAddress primaryAddress;

    @Transient
    private String name = "";

    @Relationship(type = EntityLabels.PREFERENCES, direction = Relationship.Direction.OUTGOING)
    private PartyPreference preferences;

    @Relationship(type = EntityLabels.ADDRESS, direction = Relationship.Direction.OUTGOING)
    private List<PostalAddress> addresses = new ArrayList<>();


    public PostalAddress getPrimaryAddress(){
        PostalAddress primaryAddress = null;
        if(getAddresses()!=null){
            for(PostalAddress postalAddress:getAddresses()){
                if(postalAddress.getIsPrimary()){
                    primaryAddress = postalAddress;
                }
            }
        }
        return primaryAddress;
    }

}
