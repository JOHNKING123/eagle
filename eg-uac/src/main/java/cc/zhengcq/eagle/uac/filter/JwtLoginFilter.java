package cc.zhengcq.eagle.uac.filter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter;

import java.io.IOException;


public class JwtLoginFilter extends ClientCredentialsTokenEndpointFilter {


    public JwtLoginFilter() {
        super("/api/eg-uac/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException  {
       return super.attemptAuthentication(request, response);
    }


}