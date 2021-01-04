//package cc.zhengcq.eagle.uac.filter;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.jwt.server.util.JwtUtils;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
///**
// * 验证用户名密码正确后，生成一个token，并将token返回给客户端
// * 该类继承自UsernamePasswordAuthenticationFilter，重写了其中的2个方法 attemptAuthentication
// * ：接收并解析用户凭证。 successfulAuthentication ：用户成功登录后，这个方法会被调用，我们在这个方法里生成token。
// *
// */
//public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
//
//    private AuthenticationManager authenticationManager;
//
//    public JwtLoginFilter(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//            throws AuthenticationException {
//        try {
////            UserInfo user = new ObjectMapper().readValue(request.getInputStream(), UserInfo.class);
//            String username = this.obtainUsername(request);
//            String password = this.obtainPassword(request);
//            if (username == null) {
//                username = "";
//            }
//
//            if (password == null) {
//                password = "";
//            }
//            username = username.trim();
//            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
//            this.setDetails(request, authRequest);
//            return this.getAuthenticationManager().authenticate(authRequest);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // 用户成功登录后，这个方法会被调用，我们在这个方法里生成token
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
//                                            Authentication authResult) throws IOException, ServletException {
//        String token = Jwts.builder().setSubject(((User) authResult.getPrincipal()).getUsername())
//                .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
//                .signWith(SignatureAlgorithm.HS512, "PrivateSecret").compact();
//        response.addHeader("Authorization", JwtUtils.getTokenHeader(token));
//    }
//
//}