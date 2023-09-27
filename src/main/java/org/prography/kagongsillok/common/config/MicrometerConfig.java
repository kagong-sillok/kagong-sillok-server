package org.prography.kagongsillok.common.config;

import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MicrometerConfig {

    @Bean
    public TimedAspect timedAspect(final MeterRegistry meterRegistry) {
        return new TimedAspect(meterRegistry);
    }

    @Bean
    public CountedAspect countedAspect(final MeterRegistry meterRegistry) {
        return new CountedAspect(meterRegistry);
    }
}
