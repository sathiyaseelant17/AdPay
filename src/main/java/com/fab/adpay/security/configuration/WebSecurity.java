package com.fab.adpay.security.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration
public class WebSecurity {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebSecurity.class);

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		LOGGER.info("Configuring HttpSecurity ...");
		http.csrf().disable().headers().defaultsDisabled().frameOptions().deny()
				.addHeaderWriter(new StaticHeadersWriter("Expect-CT", "max-age=3600,enforce"))
				.addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy", "script-src 'self'"))
				.addHeaderWriter(new StaticHeadersWriter("X-WebKit-CSP", "default-src 'self'"))
				.httpStrictTransportSecurity().includeSubDomains(true).maxAgeInSeconds(31536000).and().xssProtection();
		return http.build();
	}
}