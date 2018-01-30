package com.mogikanensoftware.maven.plugins.xml.cleanup.processor;

/**
 * @author mogikanen9
 */
public interface DocProcessor {

	public void process(DocProcessorParam param)
			throws DocProcessorException;
}
