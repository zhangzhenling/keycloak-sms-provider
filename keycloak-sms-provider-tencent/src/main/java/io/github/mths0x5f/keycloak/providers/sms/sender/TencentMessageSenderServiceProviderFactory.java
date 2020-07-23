package io.github.mths0x5f.keycloak.providers.sms.sender;

import io.github.mths0x5f.keycloak.providers.sms.spi.MessageSenderService;
import io.github.mths0x5f.keycloak.providers.sms.spi.MessageSenderServiceProviderFactory;


import org.keycloak.Config.Scope;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class TencentMessageSenderServiceProviderFactory implements MessageSenderServiceProviderFactory {
	private Scope config;

	@Override
	public MessageSenderService create(KeycloakSession session) {
		return new TencentMessageSenderService(config);
	}

	@Override
	public void init(Scope config) {
		this.config = config;
	}

	@Override
	public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
	}

	@Override
	public void close() {
	}

	@Override
	public String getId() {
		return "Tencent";
	}
}
