package com.api.core.auditor;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class SimpleAuditorAwareImpl implements AuditorAware<String> {

	@Override
    public Optional<String> getCurrentAuditor(){
		String userName = "API";
		return Optional.of(userName);
    }
}
