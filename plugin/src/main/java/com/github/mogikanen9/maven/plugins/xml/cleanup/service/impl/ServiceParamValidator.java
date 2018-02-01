package com.github.mogikanen9.maven.plugins.xml.cleanup.service.impl;

import org.codehaus.plexus.util.StringUtils;

import com.github.mogikanen9.maven.plugins.xml.cleanup.service.CleanupException;
import com.github.mogikanen9.maven.plugins.xml.cleanup.service.Request;
import com.github.mogikanen9.maven.plugins.xml.cleanup.service.Response;
import com.github.mogikanen9.maven.plugins.xml.cleanup.service.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ServiceParamValidator implements Service {

	private Service target;	

	@Override
	public Response cleanup(Request request) throws CleanupException {
		if (this.target == null) {
			throw new CleanupException("Target service cannot be null.");
		} else if (request == null) {
			throw new CleanupException("Request param cannot be null.");
		} else {
			if (StringUtils.isEmpty(request.getSrcFilePath())) {
				throw new CleanupException("Param value of 'srcFilePath' cannot be empty or null.");
			} else if (StringUtils.isEmpty(request.getDestFilePath())) {
				throw new CleanupException("Param value of 'destFilePath' cannot be empty or null.");
			} else if (request.getRules() == null || request.getRules().isEmpty()) {
				throw new CleanupException("No rules provided - collection of rules is empty or null.");
			}
		}
		return target.cleanup(request);
	}

}
