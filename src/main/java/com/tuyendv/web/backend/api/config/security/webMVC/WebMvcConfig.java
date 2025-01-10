//package com.tuyendv.web.backend.api.config.security.webMVC;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.tuyendv.web.backend.api.config.security.webMVC.characterEscapes.HTMLCharacterEscapes;
//import com.tuyendv.web.backend.api.config.security.webMVC.interceptor.CommonInterceptor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.List;
//
///**
// * use 1 in 2: WebSecurityConfiguration or WebMvcConfig
// *
// * @author khoinm
// * class for config add Interceptor form practical project
// * convert escapes body request or response (use encode HTMLCharacterEscapes)
// **/
//
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//
//    private static final String[] EXCLUDED_PATHS = {"/auth/**"};
//
//    @Value ("${upload.path}")
//    private String uploadPath;
//
//    @Bean
//    public CommonInterceptor commonInterceptor() {
//        return new CommonInterceptor();
//    }
//
//    //add Interceptor
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(commonInterceptor()).addPathPatterns("/**").excludePathPatterns(EXCLUDED_PATHS);
//    }
//
//    //add CORS
////    @Override
////    public void addCorsMappings(CorsRegistry registry) {
////        registry.addMapping("*")
////                .allowedOrigins("*");
////    }
//
//    //add convert escapes when receive request from FE or send response to FE
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
////        add many convert, use convert 1 when convert 0 not consistent with the request/response content
////        converters.add(0, escapingConverter());
////        converters.add(1, customConverter());
//        converters.addFirst(escapingConverter());
//    }
//
//    private HttpMessageConverter<?> escapingConverter() {
//        //init objectMapper and add encoding character escapes
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.getFactory().setCharacterEscapes(new HTMLCharacterEscapes());
//
//        //MappingJackson2HttpMessageConverter: use convert between object and json
//        MappingJackson2HttpMessageConverter escapingConverter = new MappingJackson2HttpMessageConverter();
//        escapingConverter.setObjectMapper(objectMapper);
//
//        return escapingConverter;
//    }
//
//    /*
//      find file and return it when receive request url start /files/**
//      SneakyThrows: from lib Lombok, auto handle exception without declare in method
//      code run ok but not used at present
//     */
////    @SneakyThrows
////    @Override
////    public void addResourceHandlers(ResourceHandlerRegistry registry) {
////        Path filePath = Paths.get(uploadPath).normalize();
////        Resource resource = new UrlResource(filePath.toUri());
////        registry.addResourceHandler("/files/**").addResourceLocations(resource.getURI().toString());
////    }
//
//}
