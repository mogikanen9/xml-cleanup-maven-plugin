package com.mogikanensoftware.maven.plugins.xml.cleanup.processor;

/**
 * @author mogikanen9
 */
public interface DocProcessor {

	public Result process(Param param)
			throws DocProcessorException;
}
