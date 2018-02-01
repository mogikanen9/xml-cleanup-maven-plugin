package com.github.mogikanen9.maven.plugins.xml.cleanup.service;

/**
 * @author mogikanen9
 */
public interface Service {

	Response cleanup(Request request) throws CleanupException;
}
