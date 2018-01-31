package com.mogikanensoftware.maven.plugins.xml.cleanup;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mogikanen9
 */
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.mogikanensoftware.maven.plugins.xml.cleanup.file.FileService;
import com.mogikanensoftware.maven.plugins.xml.cleanup.file.FileServiceException;
import com.mogikanensoftware.maven.plugins.xml.cleanup.file.impl.ExtensionFileFilter;
import com.mogikanensoftware.maven.plugins.xml.cleanup.file.impl.FileServiceImpl;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.rule.Rule;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.rule.impl.XPathRule;
import com.mogikanensoftware.maven.plugins.xml.cleanup.service.CleanupException;
import com.mogikanensoftware.maven.plugins.xml.cleanup.service.Request;
import com.mogikanensoftware.maven.plugins.xml.cleanup.service.Response;
import com.mogikanensoftware.maven.plugins.xml.cleanup.service.Service;
import com.mogikanensoftware.maven.plugins.xml.cleanup.service.impl.ServiceParamValidator;
import com.mogikanensoftware.maven.plugins.xml.cleanup.service.impl.XmlServiceImpl;

@Mojo(name = "cleanup")
public class MyMojo extends AbstractMojo {

	@Parameter(property = "sourceFolder")
	private File sourceFolder;

	@Parameter(property = "sourceFolderFilter")
	private String sourceFolderFilter;

	@Parameter(property = "destFolder")
	private File destFolder;

	@Parameter(property = "cleanup.xPathRules")
	private List<String> xPathRules;

	private Service cleanupService;

	private FileService fileService;

	public MyMojo() {
		super();

		getLog().debug("Initializing MyMojo");

		FileService defaultFileService = new FileServiceImpl();
		this.init(new ServiceParamValidator(new XmlServiceImpl(defaultFileService)), defaultFileService);

		getLog().debug(String.format("default cleanupService initialized -> %s", cleanupService.toString()));
	}

	public void init(Service cleanupService, FileService fileService) {
		this.fileService = fileService;
		this.cleanupService = cleanupService;
	}

	public void init(Service cleanupService, FileService fileService, File sourceFolder, String sourceFolderFilter,
			File destFolder, List<String> xPathRules) {
		this.init(cleanupService, fileService);
		this.destFolder = destFolder;
		this.sourceFolder = sourceFolder;
		this.sourceFolderFilter = sourceFolderFilter;
		this.xPathRules = xPathRules;
	}

	public void execute() throws MojoExecutionException {

		getLog().info("Execute cleanup ... ");

		printParams();

		validateParams();

		List<Rule> rules = xPathRules.stream().map(rule -> new XPathRule(rule)).collect(Collectors.toList());

		try {

			List<String> filesToProcess = fileService.listFilePaths(sourceFolder,
					new ExtensionFileFilter(sourceFolderFilter));
			getLog().info(String.format("Found %d files to process/transform.", filesToProcess.size()));

			for (String srcFilePath : filesToProcess) {

				getLog().info(String.format("Processing file '%s'", srcFilePath));

				String destFilePath = fileService.buildDestFilePath(srcFilePath, destFolder);

				Response response = cleanupService.cleanup(new Request(srcFilePath, destFilePath, rules));
				if (response == null) {
					throw new CleanupException("Response is null");
				} else {
					getLog().info(String.format("Successfully processed/cleaned up file '%s' with response->%s",
							destFilePath, response.toString()));
				}

			}

		} catch (CleanupException | FileServiceException e) {
			getLog().error(e.getMessage(), e);
			throw new MojoExecutionException(e.getMessage(), e);
		}

		getLog().info("Done.");
	}

	public void printParams() {
		getLog().info("Defined configuration params:");
		getLog().info(String.format("sourceFolder->%s", sourceFolder));
		getLog().info(String.format("destFolder->%s", destFolder));
		getLog().info(String.format("xPathRules->%s", xPathRules));
	}

	public void validateParams() throws MojoExecutionException {
		if (sourceFolder == null || !sourceFolder.exists()) {
			throw new MojoExecutionException(
					String.format("Specified 'sourceFolder' (value='%s') does not exist!", sourceFolder));
		}

		if (xPathRules == null || xPathRules.isEmpty()) {
			throw new MojoExecutionException(String.format("No rules attributes conigured! Nothing to cleanup!"));
		}
	}
}
