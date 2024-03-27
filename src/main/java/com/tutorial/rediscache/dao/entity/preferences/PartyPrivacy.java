package com.tutorial.rediscache.dao.entity.preferences;

import com.tutorial.rediscache.constant.PrivilegeType;
import com.tutorial.rediscache.dao.EntityLabels;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

import org.springframework.data.annotation.Transient;

@Node(EntityLabels.PartyPrivacy)
@Data
public class PartyPrivacy {
	@Id
	@GeneratedValue
	private Long id;

	private PrivilegeType connection = PrivilegeType.PUBLIC;
	private PrivilegeType message = PrivilegeType.CONNECTION;
	private PrivilegeType follow = PrivilegeType.PUBLIC;
	private PrivilegeType viewAvailable = PrivilegeType.CONNECTION;
	private PrivilegeType viewEmail = PrivilegeType.PRIVATE;
	private PrivilegeType viewPhone = PrivilegeType.PRIVATE;
	private PrivilegeType viewProfile = PrivilegeType.PUBLIC;
	private PrivilegeType viewOthersProfile = PrivilegeType.PUBLIC;
	private PrivilegeType searchProfile = PrivilegeType.PUBLIC;


	@Transient
	private Boolean requiredEmail = false;
	@Transient
	private Boolean allowConnect = true;
	@Transient
	private Boolean allowMessage = false;
	@Transient
	private Boolean allowFollow = true;
	@Transient
	private Boolean allowViewOnline = false;
	@Transient
	private Boolean allowViewEmail = false;
	@Transient
	private Boolean allowViewPhone = false;
	@Transient
	private Boolean allowViewProfile = true;


	@JsonIgnore
	public PrivilegeType getConnection() {
		return connection;
	}
	@JsonIgnore
	public PrivilegeType getMessage() {
		return message;
	}
	@JsonIgnore
	public PrivilegeType getFollow() {
		return follow;
	}
	@JsonIgnore
	public PrivilegeType getViewAvailable() {
		return viewAvailable;
	}
	@JsonIgnore
	public PrivilegeType getViewEmail() {
		return viewEmail;
	}
	@JsonIgnore
	public PrivilegeType getViewPhone() {
		return viewPhone;
	}
	@JsonIgnore
	public PrivilegeType getViewProfile() {
		return viewProfile;
	}
}
