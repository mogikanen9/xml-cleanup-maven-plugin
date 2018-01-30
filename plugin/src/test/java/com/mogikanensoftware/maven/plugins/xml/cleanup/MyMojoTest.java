package com.mogikanensoftware.maven.plugins.xml.cleanup;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.mogikanensoftware.maven.plugins.test.mockito.rule.MockitoInitRule;
import com.mogikanensoftware.maven.plugins.xml.cleanup.file.FileService;
import com.mogikanensoftware.maven.plugins.xml.cleanup.file.FileServiceException;
import com.mogikanensoftware.maven.plugins.xml.cleanup.file.impl.ExtensionFileFilter;
import com.mogikanensoftware.maven.plugins.xml.cleanup.service.CleanupException;
import com.mogikanensoftware.maven.plugins.xml.cleanup.service.Response;
import com.mogikanensoftware.maven.plugins.xml.cleanup.service.Service;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class MyMojoTest {

	private static final String EMPTY_SOURCE_FOLDER_FILTER = "";

	@org.junit.Rule
	public MockitoInitRule mockitoRule;

	@Mock
	private Service cleanupService;

	@Mock
	private FileService fileService;

	@Mock
	private Log log;

	private File validSrcFolder;

	private File validSrcFile;
	
	private FilenameFilter filenameFilter;

	public MyMojoTest() {
		super();
		mockitoRule = new MockitoInitRule(this);
	}

	@Before
	public void setUp() throws FileServiceException, CleanupException {
		validSrcFolder = new File(MyMojoTest.class.getResource("/samples").getPath());
		validSrcFile = new File(MyMojoTest.class.getResource("/samples/file1.xml").getPath());
		filenameFilter = new ExtensionFileFilter(EMPTY_SOURCE_FOLDER_FILTER);
		when(fileService.listFilePaths(validSrcFolder, filenameFilter)).thenReturn(Arrays.asList(validSrcFile.getPath()));
		when(cleanupService.cleanup(Mockito.any())).thenReturn(Mockito.mock(Response.class));
	}

	@After
	public void tearDwon() {
		validSrcFolder = null;
		filenameFilter = null;
		validSrcFolder = null;
		validSrcFile = null;
	}

	@Test
	public void testExecute() throws MojoExecutionException, CleanupException, FileServiceException {
		MyMojo sut = new MyMojo();
		sut.init(cleanupService, fileService, validSrcFolder, EMPTY_SOURCE_FOLDER_FILTER, Mockito.mock(File.class), Arrays.asList("rule1"));
		sut.setLog(log);
		sut.execute();
		Mockito.verify(fileService).buildDestFilePath(Mockito.any(), Mockito.any());
		Mockito.verify(cleanupService).cleanup(Mockito.any());
	}

	@Test
	public void testExecuteCleanupExceptionNullResponse() throws CleanupException {
		MyMojo sut = new MyMojo();

		// return null response
		when(cleanupService.cleanup(Mockito.any())).thenReturn(null);

		sut.init(cleanupService, fileService, validSrcFolder, EMPTY_SOURCE_FOLDER_FILTER, Mockito.mock(File.class), Arrays.asList("rule1"));
		sut.setLog(log);
		try {
			sut.execute();
			Assert.fail("unrechable");
		} catch (MojoExecutionException e) {
			assertThat(e.getMessage(), containsString("Response is null"));
		}

	}

	@Test
	public void testPrintParams() {
		MyMojo sut = new MyMojo();
		sut.printParams();
	}

	@Test(expected = MojoExecutionException.class)
	public void testValidateParams() throws MojoExecutionException {
		MyMojo sut = new MyMojo();
		sut.validateParams();
		Assert.fail();
	}

	public Object[] buildNoRules() {
		return new Object[] { null, new ArrayList<>() };
	}

	@Test(expected = MojoExecutionException.class)
	@Parameters(method = "buildNoRules")
	public void testValidateParamsNoRules(List<String> xPathRules) throws MojoExecutionException {
		MyMojo sut = new MyMojo();
		sut.init(cleanupService, fileService, validSrcFolder, EMPTY_SOURCE_FOLDER_FILTER, Mockito.mock(File.class), xPathRules);
		sut.validateParams();
		Assert.fail();
	}

	@Test
	public void testValidateParamsNoEx() throws MojoExecutionException {
		MyMojo sut = new MyMojo();
		sut.init(cleanupService, fileService, validSrcFolder, EMPTY_SOURCE_FOLDER_FILTER, Mockito.mock(File.class), Arrays.asList("rule1"));
		sut.validateParams();
	}

}
