package com.mogikanensoftware.maven.plugins.xml.cleanup.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mogikanensoftware.maven.plugins.xml.cleanup.file.FileService;
import com.mogikanensoftware.maven.plugins.xml.cleanup.file.FileServiceException;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.Action;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.DocProcessor;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.DocProcessorException;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.Param;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.Result;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.impl.DocProcessorParamValidator;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.impl.JAXPDocHelper;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.impl.JAXPDocProcessorImpl;
import com.mogikanensoftware.maven.plugins.xml.cleanup.service.CleanupException;
import com.mogikanensoftware.maven.plugins.xml.cleanup.service.Request;
import com.mogikanensoftware.maven.plugins.xml.cleanup.service.Response;
import com.mogikanensoftware.maven.plugins.xml.cleanup.service.Service;

/**
 * @author mogikanen9
 */
public final class XmlServiceImpl implements Service {

	private static final Logger logger = LoggerFactory.getLogger(XmlServiceImpl.class);

	private FileService fileService;

	public XmlServiceImpl(FileService fileService) {
		super();
		this.fileService = fileService;
	}

	public Response cleanup(Request request) throws CleanupException {
		logger.debug(String.format("request->%s", request));

		DocProcessor docProcessor = new DocProcessorParamValidator(new JAXPDocProcessorImpl(new JAXPDocHelper()));
		try {

			boolean removed = fileService.removeFileIfAlreadyExists(request.getDestFilePath());
			logger.debug(
					String.format("FIle->'%s' was removed since existed ->%b", request.getDestFilePath(), removed));

			Result procRs = docProcessor.process(new Param(request.getSrcFilePath(), request.getDestFilePath(), request.getRules(),
					Action.REMOVE_NODE));
			logger.debug(String.format("procRs->%s", procRs.toString()));
			
			return new Response(procRs);
		} catch (DocProcessorException | FileServiceException e) {
			throw new CleanupException(e.getMessage(), e);
		}
	}

}
