package ca.ghandalf.urban.mobility.restapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class SwaggerConfiguration extends WebMvcConfigurationSupport {

  @Bean
  public Object productApi() {
//  public Docket productApi() {
//    return new Docket(DocumentationType.SWAGGER_2).select()
//        .apis(RequestHandlerSelectors.basePackage("ca.ghandalf.urban.mobility"))
//        .paths(PathSelectors.regex("/um.*"))
//        .build()
//        .apiInfo(metaData());
    return null;
  }
  
  private String metaData() {
//  private ApiInfo metaData() {
    return null;
//            new ApiInfoBuilder()
//        .title("Urban Mobility REST API")
//        .description("\"Urbam Mobility REST API for Cities transportation\"")
//        .version("0.0.9")
//        .license("Apache License Version 2.0")
//        .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
//        .contact(new Contact("Francis Ouellet", "https://localhost:8020/about/", "Ouellet.Francis@gmail.com"))
//        .build();
  }
  
//  @Override
  protected void addResourceHandlers() {
//          ResourceHandlerRegistry registry) {
//      registry.addResourceHandler("swagger-ui.html")
//              .addResourceLocations("classpath:/META-INF/resources/");
//
//      registry.addResourceHandler("/webjars/**")
//              .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }
}
