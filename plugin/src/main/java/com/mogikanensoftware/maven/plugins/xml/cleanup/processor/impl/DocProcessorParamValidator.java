package com.mogikanensoftware.maven.plugins.xml.cleanup.processor.impl;

import java.io.File;

import org.codehaus.plexus.util.StringUtils;

import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.DocProcessor;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.DocProcessorException;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.DocProcessorParam;

import lombok.AllArgsConstructor;

/**
 * @author mogikanen9
 */

@AllArgsConstructor
public final class DocProcessorParamValidator implements DocProcessor {

	private DocProcessor target;

	@Override
	public void process(DocProcessorParam param) throws DocProcessorException {

		if (param == null) {
			throw new DocProcessorException("Param value cannot be null");
		}

		if (StringUtils.isEmpty(param.getSrcFilePath()) || !new File(param.getSrcFilePath()).exists()) {
			throw new DocProcessorException(
					String.format("Source file does not exist, srcfilePath->%s", param.getSrcFilePath()));
		}

		if (StringUtils.isEmpty(param.getDestFilePath())) {
			throw new DocProcessorException(
					String.format("Destination file path cannot be null/empty->%s", param.getDestFilePath()));
		}

		this.target.process(param);

	}

}
