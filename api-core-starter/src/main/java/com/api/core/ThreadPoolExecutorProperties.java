package com.api.core;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("threadpoolexecutor")
public class ThreadPoolExecutorProperties {

	private Integer corePoolSize = 5;
	private Integer maxPoolSize = 10;
	private Integer queueCapacity = 25;

}
