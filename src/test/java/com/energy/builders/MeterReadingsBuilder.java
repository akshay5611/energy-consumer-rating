package com.energy.builders;


import java.util.ArrayList;
import java.util.List;

import com.energy.generator.ElectricityReadingsGenerator;
import com.energy.model.ElectricityReading;
import com.energy.model.MeterReadings;

public class MeterReadingsBuilder {

	private static final String DEFAULT_METER_ID = "id";

	private String smartMeterId = DEFAULT_METER_ID;
	private List<ElectricityReading> electricityReadings = new ArrayList<>();

	public MeterReadingsBuilder setSmartMeterId(String smartMeterId) {
		this.smartMeterId = smartMeterId;
		return this;
	}

	public MeterReadingsBuilder generateElectricityReadings() {
		return generateElectricityReadings(5);
	}

	public MeterReadingsBuilder generateElectricityReadings(int number) {
		ElectricityReadingsGenerator readingsBuilder = new ElectricityReadingsGenerator();
		this.electricityReadings = readingsBuilder.generate(number);
		return this;
	}

	public MeterReadings build() {
		return new MeterReadings(smartMeterId, electricityReadings);
	}
}
