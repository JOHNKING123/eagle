package cc.zhengcq.eagle.uac.service;

import cc.zhengcq.eagle.uac.properties.OAuth2ClientProperties;
import cc.zhengcq.eagle.uac.properties.OAuth2Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 *  配置客户端
 * @author    zhengcq
 * @date 	    2019-07-14
 * @version   v1.0.0
 * @since     2019-07-14
 */
@Slf4j
@Component("restClientDetailsService")
public class RestClientDetailsServiceImpl implements ClientDetailsService {

	private ClientDetailsService clientDetailsService;

	@Autowired
	private OAuth2Properties oAuth2Properties;

	/**
	 * Init.
	 */
	@PostConstruct
	public void init() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		InMemoryClientDetailsServiceBuilder builder = new InMemoryClientDetailsServiceBuilder();
		for (OAuth2ClientProperties client : oAuth2Properties.getClients()) {
			builder.withClient(client.getClientId())
					.secret(bCryptPasswordEncoder.encode(client.getClientSecret()))
					.authorizedGrantTypes("refresh_token", "password", "client_credentials")
					.accessTokenValiditySeconds(client.getAccessTokenValidateSeconds())
					.refreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds())
					.scopes(client.getScope());
		}
		try {
			clientDetailsService = builder.build();
		} catch (Exception e) {
			log.error("init={}", e.getMessage(), e);
		}
	}

	/**
	 * Load client by client id client details.
	 *
	 * @param clientId the client id
	 *
	 * @return the client details
	 *
	 * @throws ClientRegistrationException the client registration exception
	 */
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		return clientDetailsService.loadClientByClientId(clientId);
	}
}
