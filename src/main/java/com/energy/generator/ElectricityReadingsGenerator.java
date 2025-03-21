package com.energy.generator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import com.energy.model.*;

public class ElectricityReadingsGenerator {

    public List<com.energy.model.ElectricityReading> generate(int number) {
        List<ElectricityReading> readings = new ArrayList<>();
        Instant now = Instant.now();

        Random readingRandomiser = new Random();
        for (int i = 0; i < number; i++) {
            double positiveRandomValue = Math.abs(readingRandomiser.nextGaussian());
            BigDecimal randomReading = BigDecimal.valueOf(positiveRandomValue).setScale(4, RoundingMode.CEILING);
            ElectricityReading electricityReading = new ElectricityReading(now.minusSeconds(i * 10), randomReading);
            readings.add(electricityReading);
        }

        readings.sort(Comparator.comparing(ElectricityReading::getTime));
        return readings;
    }


}
