package io.github.mths0x5f.keycloak.providers.sms.representations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.keycloak.models.utils.KeycloakModelUtils;

import java.security.SecureRandom;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenCodeRepresentation {

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

	public void setExpiresAt(Date expiresAt) {
		this.expiresAt = expiresAt;
	}

	public Date getExpiresAt() {
		return expiresAt;
	}

	private String phoneNumber;
	private String code;
	private String type;
	private Date createdAt;
	private Date expiresAt;
	private Boolean confirmed;

	public static TokenCodeRepresentation forPhoneNumber(String phoneNumber) {

		TokenCodeRepresentation tokenCode = new TokenCodeRepresentation();

		tokenCode.id = KeycloakModelUtils.generateId();
		tokenCode.phoneNumber = phoneNumber;
		tokenCode.code = generateTokenCode();
		tokenCode.confirmed = false;

		return tokenCode;
	}

	private static String generateTokenCode() {
		SecureRandom secureRandom = new SecureRandom();
		Integer code = secureRandom.nextInt(999_999);
		return String.format("%06d", code);
	}
}
