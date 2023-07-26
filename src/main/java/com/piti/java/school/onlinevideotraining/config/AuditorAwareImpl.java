package com.piti.java.school.onlinevideotraining.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String>{

	@Override
	public Optional<String> getCurrentAuditor() {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		return Optional.ofNullable(name);
	}

}
