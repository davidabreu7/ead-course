package com.ead.course.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class AppConfig {

    // ...

    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener(
            final LocalValidatorFactoryBean factory) {
        return new ValidatingMongoEventListener(factory);
    }


//    @Bean
//    @LoadBalanced
//    UserCourseClient userCourseClient(){
//        WebClient client = WebClient.builder()
////                .clientConnector(new ReactorClientHttpConnector(httpClientReactor()))
//                .baseUrl(url)
//                .build();
//
//        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client))
//                .build();
//
//        return proxyFactory.createClient(UserCourseClient.class);
//    }
}