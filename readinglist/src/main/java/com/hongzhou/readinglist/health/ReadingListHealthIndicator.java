package com.hongzhou.readinglist.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

public class ReadingListHealthIndicator implements HealthIndicator{

	@Override
	public Health health() {
		// TODO Auto-generated method stub
		return  Health.up().build();
	}

}
