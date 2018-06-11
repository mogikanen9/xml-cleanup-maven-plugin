package com.github.mogikanen9.maven.plugins.xml.cleanup.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mogikanen9.maven.plugins.xml.cleanup.file.FileService;
import com.github.mogikanen9.maven.plugins.xml.cleanup.file.FileServiceException;
import com.github.mogikanen9.maven.plugins.xml.cleanup.processor.Action;
import com.github.mogikanen9.maven.plugins.xml.cleanup.processor.DocProcessor;
import com.github.mogikanen9.maven.plugins.xml.cleanup.processor.DocProcessorException;
import com.github.mogikanen9.maven.plugins.xml.cleanup.processor.Param;
import com.github.mogikanen9.maven.plugins.xml.cleanup.processor.Result;
import com.github.mogikanen9.maven.plugins.xml.cleanup.processor.impl.DocProcessorParamValidator;
import com.github.mogikanen9.maven.plugins.xml.cleanup.processor.impl.JAXPDocHelper;
import com.github.mogikanen9.maven.plugins.xml.cleanup.processor.impl.JAXPDocProcessorImpl;
import com.github.mogikanen9.maven.plugins.xml.cleanup.service.CleanupException;
import com.github.mogikanen9.maven.plugins.xml.cleanup.service.Request;
import com.github.mogikanen9.maven.plugins.xml.cleanup.service.Response;
import com.github.mogikanen9.maven.plugins.xml.cleanup.service.Service;

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

			String destFilePath = request.getDestFilePath();
			String srcFilePath = request.getSrcFilePath();
			
			if(fileService.fileExists(destFilePath)) {
				String copyFilePath = fileService.generateFileCopy(destFilePath);
				logger.debug(
						String.format("copyFilePath->'%s", copyFilePath));
				srcFilePath = copyFilePath;
				
			}
			
			//boolean removed = fileService.removeFileIfAlreadyExists(destFilePath);
			//logger.debug(
				//	String.format("FIle->'%s' was removed since existed ->%b", destFilePath, removed));

			Result procRs = docProcessor.process(new Param(srcFilePath, destFilePath, request.getRules(),
					Action.REMOVE_NODE));
			logger.debug(String.format("procRs->%s", procRs.toString()));
			
			return new Response(procRs);
		} catch (DocProcessorException | FileServiceException e) {
			throw new CleanupException(e.getMessage(), e);
		}
	}

}
