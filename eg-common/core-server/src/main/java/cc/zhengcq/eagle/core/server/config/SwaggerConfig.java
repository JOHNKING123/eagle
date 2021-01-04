package cc.zhengcq.eagle.core.server.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 *  swagger 配置
 * @author    zhengcq
 * @date 	    2019-07-12
 * @version   v1.0.0
 * @since     2019-07-12
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 可以定义多个组，比如本类中定义把test和demo区分开了 （访问页面就可以看到效果了）
     *
     */
    @Bean
    public Docket createRestApi() {
        ParameterBuilder authorizationPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        authorizationPar.name("Authorization").description("Authorization")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        pars.add(authorizationPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cc.zhengcq.eagle"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }
//    @Bean
//    public Docket platformApi() {
//
//        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).forCodeGeneration(true)
//                .select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                .apis(RequestHandlerSelectors.basePackage("cc.zhengcq.eagle"))
//                .paths(PathSelectors.any())
//                .build()
//                .securitySchemes(securitySchemes())
//                .securityContexts(securityContexts());
//
//
//    }
//    private List<ApiKey> securitySchemes() {
//        List<ApiKey> apiKeyList= new ArrayList();
//        apiKeyList.add(new ApiKey("x-auth-token", "x-auth-token", "header"));
//        return apiKeyList;
//    }
//
//    private List<SecurityContext> securityContexts() {
//        List<SecurityContext> securityContexts=new ArrayList<>();
//        securityContexts.add(
//                SecurityContext.builder()
//                        .securityReferences(defaultAuth())
//                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
//                        .build());
//        return securityContexts;
//    }
//
//    List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        List<SecurityReference> securityReferences=new ArrayList<>();
//        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
//        return securityReferences;
//    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("洋桃 系统管理中心", // 大标题
                "msyt api", // 小标题
                "1.0", // 版本
                "NO terms of service", "service@msyt.com ", // 作者
                "msyt", // 链接显示文字
                "http://www.msyt.com/"// 网站链接
        );
        return apiInfo;
    }
}
