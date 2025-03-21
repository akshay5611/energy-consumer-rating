package com.energy.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

 class MeterReadingServiceTest {

    private MeterReadingService meterReadingService;

    @BeforeEach
    public void setUp() {
        meterReadingService = new MeterReadingService(new HashMap<>());
    }

	@Test
	void givenMeterIdThatDoesNotExistShouldReturnNull() {
		assertThat(meterReadingService.getReadings("unknown-id")).isEqualTo(Optional.empty());
	}

	@Test
	void givenMeterReadingThatExistsShouldReturnMeterReadings() {
		meterReadingService.storeReadings("random-id", new ArrayList<>());
		assertThat(meterReadingService.getReadings("random-id")).isEqualTo(Optional.of(new ArrayList<>()));
	}
}
