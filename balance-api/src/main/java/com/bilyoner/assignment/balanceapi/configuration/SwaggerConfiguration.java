package com.bilyoner.assignment.balanceapi.configuration;

import com.bilyoner.assignment.balanceapi.configuration.properties.SwaggerProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@AllArgsConstructor
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

    public static final String TAG_BALANCE = "Balance Management Screen";
    public static final String DESC_BALANCE = "Search and manage balances";

    private static final String BASE_PACKAGE_NAME = "com.bilyoner";

    private final SwaggerProperties properties;

    @Bean
    public Docket api(ApiInfo apiInfo) {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE_NAME))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo)
                .tags(new Tag(TAG_BALANCE, DESC_BALANCE));
    }

    @Bean
    public ApiInfo apiInfo() {
        SwaggerProperties.Contact contact = properties.getContact();
        List<SwaggerProperties.VendorExtension> vendorExtensions = properties.getVendorExtensions();

        return new ApiInfo(
                properties.getTitle(),
                properties.getDescription(),
                properties.getVersion(),
                properties.getTermsOfServiceUrl(),
                new Contact(contact.getName(), contact.getUrl(), contact.getEmail()),
                properties.getLicense(),
                properties.getLicenseUrl(),
                vendorExtensions.stream()
                        .map(extension -> new StringVendorExtension(extension.getName(), extension.getValue()))
                        .collect(Collectors.toList()));
    }

}
