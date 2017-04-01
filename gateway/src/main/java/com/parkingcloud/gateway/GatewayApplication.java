package com.parkingcloud.gateway;

import com.parkingcloud.gateway.filter.ErrorRequestFilter;
import com.parkingcloud.gateway.filter.PreRequestLogFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
public class GatewayApplication {

	@Bean
	public PreRequestLogFilter preRequestLogFilter() {
		return new PreRequestLogFilter();
	}

    @Bean
    public ErrorRequestFilter errorRequestFilter() {
        return new ErrorRequestFilter();
    }

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
}
