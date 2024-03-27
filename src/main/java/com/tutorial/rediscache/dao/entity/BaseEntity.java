package com.tutorial.rediscache.dao.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redis.om.spring.annotations.Indexed;
import com.tutorial.rediscache.constant.Constants;
import com.tutorial.rediscache.constant.Status;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

import java.time.Instant;

@Data
@JsonIgnoreProperties
public abstract class BaseEntity {

    @Id
    @GeneratedValue
    @Indexed
    private Long id;

    private Long createdDate;

    @CreatedBy
    private Long createdBy;

    @Setter
    private Long lastModifiedDate;

    @LastModifiedBy
    private Long lastModifiedBy;
    @Setter
    private Status status;



    public BaseEntity() {
        init();
    }

    @Transient
    public boolean isActive() {
        return Status.ACTIVE.equals(status);
    }


    @Transient
    public void active() {
        status = Status.ACTIVE;
    }

    @Transient
    public void inactive() {
        this.setStatus(Status.INACTIVE);
    }

    @Transient
    public void deactivate() {
        this.setStatus(Status.DEACTIVATED);
    }

    @Transient
    private void init() {
        status = Status.ACTIVE;

        Instant timeStamp= Instant.now();
        timeStamp.atZone(Constants.TIMEZONE);
        createdDate = timeStamp.getEpochSecond();
    }
}
