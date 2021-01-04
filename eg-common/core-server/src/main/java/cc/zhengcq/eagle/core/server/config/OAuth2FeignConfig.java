package cc.zhengcq.eagle.core.server.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

public class OAuth2FeignConfig implements RequestInterceptor {
 
	public static String TOKEN_HEADER = "authorization";

    @Override
	public void apply(RequestTemplate template) {
        String authorizationStr = getHeaders(getHttpServletRequest()).get(TOKEN_HEADER);
		template.header(TOKEN_HEADER, authorizationStr);
	}
 
	private HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }
	
	private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
	
}