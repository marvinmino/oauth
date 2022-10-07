package com.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@SpringBootApplication
public class OauthClient {
	@Value("#{ @environment['okta.oauth2.audience'] }")
	private String resourceServerUrl;

	public static void main(String[] args) {
		SpringApplication.run(OauthClient.class, args);
	}

	@GetMapping("/")
	String home(@AuthenticationPrincipal OidcUser user) {
		return "Hello " + user.getFullName();
	}

	@GetMapping("/api")
	ResponseEntity<Void> api() {
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8081/api")).build();
	}
}
