package cc.zhengcq.eagle.uac.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *  读取配置信息
 * @author    zhengcq
 * @date 	    2019-07-14
 * @version   v1.0.0
 * @since     2019-07-14
 */
@Data
@ConfigurationProperties(prefix = "eagle.security.oauth2")
@Component
public class OAuth2Properties {

	/**
	 * 使用jwt时为token签名的秘钥
	 */
	private String jwtSigningKey = "xxx";

	private String tokenStore = "jwt";
	/**
	 * 客户端配置
	 */
	private OAuth2ClientProperties[] clients = {};

}
