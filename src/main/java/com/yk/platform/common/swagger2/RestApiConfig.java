package com.yk.platform.common.swagger2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2 // 启用swagger2
@Configuration // spring自动加载配置
public class RestApiConfig extends WebMvcConfigurationSupport {
    @Value("${swagger.switch}")
    private Boolean swaggerSwitch;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("^\\/device\\/.*|^\\/version\\/.*|^\\/versionPubRecord\\/.*|^\\/stats\\/.*|^\\/location\\/.*")).build()
                .enable(swaggerSwitch == null ? false : swaggerSwitch);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("小天才电话手表OTA平台APIs").description("用于接口格式说明，以及接口调试").version("1.0").build();
    }
}
