package com.mogikanensoftware.maven.plugins.xml.cleanup.service;

/**
 * @author mogikanen9
 */
public final class CleanupException extends Exception {

	private static final long serialVersionUID = 1L;

	public CleanupException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public CleanupException(String arg0) {
		super(arg0);
	}

}
