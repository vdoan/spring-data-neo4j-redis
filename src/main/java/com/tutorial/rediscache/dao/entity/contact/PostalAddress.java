package com.tutorial.rediscache.dao.entity.contact;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tutorial.rediscache.constant.PostalAddressType;

import com.tutorial.rediscache.dao.EntityLabels;
import com.tutorial.rediscache.dao.entity.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.Instant;

@Node(EntityLabels.PostalAddress)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostalAddress extends BaseEntity {

	private String name;
	private String address1;
	private String address2;
	private PostalAddressType type;
	private String phoneNumber;
	private String phoneNumberExt;
	private String city;
	private String state;
	private String country;
	private String postalCode;
	private String district;
	private String ward;
	private Double longitude;
	private Double latitude;
	private String timezone;
	private String placeId;
	private Boolean isPrimary = false;
	private Boolean noHoursAvailable = true;
	private Boolean alwaysOpen = false;
	private Boolean permanentlyClosed = false;
	private Boolean isClosed = false;


	public static PostalAddress create(String name, String address1, String address2, PostalAddressType type, String phoneNumber, String phoneNumberExt, String city, String state, String ward, String district, String country, String postalCode, Boolean isPrimary, Double longitude, Double latitude, String timezone) {
		PostalAddress entity = new PostalAddress();
		entity.setCreatedDate(Instant.now().getEpochSecond());
		entity.setName(name);
		entity.setAddress1(address1);
		entity.setAddress2(address2);
		entity.setType(type);
		entity.setPhoneNumber(phoneNumber);
		entity.setPhoneNumberExt(phoneNumberExt);
		entity.setCity(city);
		entity.setState(state);
		entity.setCountry(country);
		entity.setPostalCode(postalCode);
		entity.setIsPrimary(isPrimary);
		entity.setLongitude(longitude);
		entity.setLatitude(latitude);
		entity.setTimezone(timezone);
		entity.setWard(ward);
		entity.setDistrict(district);
		return entity;
	}



}

