package com.goochou.p2b.app.conf.swagger;

import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.constant.TestEnum;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 *
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket flowerDocket() {

        if(TestEnum.DEBUG.getFeatureName().equals(Constants.TEST_SWITCH)){
            return new Docket(DocumentationType.SWAGGER_2)
                    .groupName("bengfu")
                    .apiInfo(flowerInfo()).select()
                    .apis(RequestHandlerSelectors.basePackage("com.goochou.p2b.app.controller"))
                    .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                    .paths(PathSelectors.any())
                    .build();
        }else {
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfoOnline())
                    .select()
                    .paths(PathSelectors.none())//如果是线上环境，添加路径过滤，设置为全部都不符合
                    .build();
        }

    }

    private ApiInfo flowerInfo() {
        return new ApiInfoBuilder()
                .title("奔富")
                .description("奔富接口")
                .termsOfServiceUrl("https://www.bfmuchang.com/")
                .contact(new Contact("奔富", "baidu.com", "test@tes.com"))
                .version("1.00")
                .build();
    }


    private ApiInfo apiInfoOnline() {
        return new ApiInfoBuilder()
                .title("")
                .description("")
                .license("")
                .licenseUrl("")
                .termsOfServiceUrl("")
                .version("")
                .contact(new Contact("","", ""))
                .build();
    }

//    public static Predicate<RequestHandler> basePackage(final String basePackage) {
//        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
//    }
//
//    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage)     {
//        return input -> {
//            // 循环判断匹配
//            for (String strPackage : basePackage.split(",")) {
//                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
//                if (isMatch) {
//                    return true;
//                }
//            }
//            return false;
//        };
//    }
//
//    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
//        return Optional.fromNullable(input.declaringClass());
//    }

}
