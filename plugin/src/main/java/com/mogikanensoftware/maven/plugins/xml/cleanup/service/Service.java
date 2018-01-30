package com.mogikanensoftware.maven.plugins.xml.cleanup.service;

/**
 * @author mogikanen9
 */
public interface Service {

	Response cleanup(Request request) throws CleanupException;
}
