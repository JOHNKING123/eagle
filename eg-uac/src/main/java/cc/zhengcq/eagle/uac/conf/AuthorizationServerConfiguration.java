package cc.zhengcq.eagle.uac.conf;

import cc.zhengcq.eagle.uac.properties.OAuth2Properties;
import cc.zhengcq.eagle.uac.service.RestClientDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;


/**
 *  授权服务配置
 * @author    zhengcq
 * @date 	    2019-07-12
 * @version   v1.0.0
 * @since     2019-07-12
 */
@Configuration
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RestClientDetailsServiceImpl restClientDetailsService;

    @Autowired
    private OAuth2Properties oAuth2Properties;
    // 使用最基本的InMemoryTokenStore生成token
    @Bean
    @ConditionalOnMissingBean
    public TokenStore memoryTokenStore() {
        return new InMemoryTokenStore();
    }

    /**
     * 配置客户端详情服务
     * 客户端详细信息在这里进行初始化
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(restClientDetailsService);
    }

    /**
     * 用来配置令牌端点(Token Endpoint)的安全约束.
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    /* 配置token获取合验证时的策略 */
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    /**
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints.pathMapping("/oauth/authorize","/api/eg-uac/oauth/authorize")
                . pathMapping("/oauth/token","/api/eg-uac/oauth/token");
        // 配置tokenStore,需要配置userDetailsService，否则refresh_token会报错
        TokenStore tokenStore = tokenStore();
        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore()).tokenEnhancer(jwtTokenEnhancer()).userDetailsService(userDetailsService);
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
        //注意此处需要相应的jks文件
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("msyt-jwt.jks"), oAuth2Properties.getJwtSigningKey().toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("msyt-jwt"));
        return converter;
    }
}
