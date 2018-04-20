package com.hongzhou.readinglist.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class AmazonHealth implements HealthIndicator {

	@Override
	public Health health() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getForObject("http://www.amazon.com", String.class);
			return Health.up().build();
		} catch (RestClientException e) {
			return Health.down().withDetail("Exception", e.getMessage()).build();
		}
	}

}
