package cc.zhengcq.eagle.uac.controller;

import cc.zhengcq.eagle.core.common.entity.JsonResult;
import cc.zhengcq.eagle.core.server.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: UacLoginController
 * @Description: todo
 * @Company: 广州市两棵树网络科技有限公司
 * @Author: zhengcq
 * @Date: 2021/1/7
 */
@Api(tags = "供应商采购单控制器")
@RestController
@RequestMapping("/api/eg-uac/")
public class UacLoginController extends BaseController {
    /**
     * TokenEndpoint
     */
    @Autowired
    private TokenEndpoint  tokenEndpoint;

    /**
     * 登陆
     * @param username 用户名称
     * @param password 密码
     * @return 结果
     */
    @ApiOperation("用户登陆")
    @PostMapping("/login")
    public JsonResult login(@RequestParam("username")String username, @RequestParam("password")String password) {

        if (username.equals("admin") && password.equals("admin")) {

            UsernamePasswordAuthenticationToken principal =  new UsernamePasswordAuthenticationToken("msyt-browser", "browser123456", Collections.emptyList());
            try {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("client_id", "msyt-browser");
                parameters.put("client_secret", "browser123456");
                parameters.put("grant_type", "client_credentials");
                ResponseEntity<OAuth2AccessToken> accessToken = tokenEndpoint.postAccessToken(principal, parameters);
                accessToken.getBody().getValue();
                return JsonResult.ok(accessToken);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return JsonResult.ok(null);
    }
}
