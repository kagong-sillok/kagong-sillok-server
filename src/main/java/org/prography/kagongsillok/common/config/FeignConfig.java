package org.prography.kagongsillok.common.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "org.prography.kagongsillok")
@Configuration
public class FeignConfig {

}
