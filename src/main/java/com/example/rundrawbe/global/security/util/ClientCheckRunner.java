package com.example.rundrawbe.global.security.util;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientCheckRunner implements CommandLineRunner {

    private final ClientRegistrationRepository clientRegistrationRepository;

    @Override
    public void run(String... args) {
        ClientRegistration kakao = clientRegistrationRepository.findByRegistrationId("kakao");
        System.out.println("kakao client-id = " + kakao.getClientId());
        System.out.println("kakao client-secret = " + kakao.getClientSecret());
    }
}
