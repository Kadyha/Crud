package com.example.crudapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.server.endpoint.adapter.method.MarshallingPayloadMethodProcessor;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.context.ApplicationContext;

@EnableWs
@Configuration
public class SoapConfig {
    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // Only bind classes from our SOAP DTO package to avoid mixing JPA entities in the JAXB context
        marshaller.setPackagesToScan("com.example.crudapp.soap");
        return marshaller;
    }

    @Bean
    public MarshallingPayloadMethodProcessor marshallingPayloadMethodProcessor(Jaxb2Marshaller marshaller) {
        return new MarshallingPayloadMethodProcessor(marshaller, marshaller);
    }

    // Explicitly register Spring-WS servlet so POST /ws is routed to SOAP endpoints
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        ServletRegistrationBean<MessageDispatcherServlet> registration = new ServletRegistrationBean<>(servlet, "/ws/*", "/ws");
        registration.setName("messageDispatcherServlet");
        return registration;
    }

}
