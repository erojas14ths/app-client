package com.erojas.lab.appclient.controller;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.erojas.lab.appclient.properties.ServiceProperties;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RestController
@RequestMapping("/client")
public class HelloController {

    @Autowired
    private WebClient client;

    @Autowired
    private ServiceProperties serviceProperties;

    @GetMapping("/hello")
    public Mono<String> sayHello(@RequestParam(name = "name", required = false) String name) {
        String url;
        if (name == null) {
            url = serviceProperties.getAppServer() + "/hello";
        } else {
            url = serviceProperties.getAppServer() + "/hello" + "?name=" + name;
        }
        var uri = UriComponentsBuilder.fromUriString(url).build().toUri();
        log.info("Current profile is: {}", serviceProperties.getProfile());
        log.info("App Server URI: {}", uri);
        return client.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class);

    }
}
