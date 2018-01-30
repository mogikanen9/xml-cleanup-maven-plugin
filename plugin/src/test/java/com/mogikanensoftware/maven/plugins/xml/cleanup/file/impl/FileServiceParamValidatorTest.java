package com.mogikanensoftware.maven.plugins.xml.cleanup.file.impl;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.mogikanensoftware.maven.plugins.test.mockito.rule.MockitoInitRule;
import com.mogikanensoftware.maven.plugins.xml.cleanup.MyMojoTest;
import com.mogikanensoftware.maven.plugins.xml.cleanup.file.FileService;
import com.mogikanensoftware.maven.plugins.xml.cleanup.file.FileServiceException;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class FileServiceParamValidatorTest {

	@org.junit.Rule
	public MockitoInitRule mockitoRule;

	@Mock
	private FileService fileService;

	private FileServiceParamValidator sut;

	private File validSrcFolder;

	private File validSrcFile;

	public FileServiceParamValidatorTest() {
		super();
		mockitoRule = new MockitoInitRule(this);
	}

	@Before
	public void setUp() throws Exception {
		sut = new FileServiceParamValidator(fileService);
		validSrcFolder = new File(MyMojoTest.class.getResource("/samples").getPath());
		validSrcFile = new File(MyMojoTest.class.getResource("/samples/file1.xml").getPath());
	}

	@After
	public void tearDown() throws Exception {
		sut = null;
		validSrcFolder = null;
		validSrcFile = null;
	}

	@Test
	public void testListFilePathsSuccess() throws FileServiceException {
		when(fileService.listFilePaths(validSrcFolder)).thenReturn(Arrays.asList("mySourceFilePathValue"));

		List<String> rs = sut.listFilePaths(validSrcFolder);
		verify(fileService).listFilePaths(validSrcFolder);
		assertNotNull(rs);
		assertTrue(rs.size() == 1);
		assertTrue(rs.get(0).equalsIgnoreCase("mySourceFilePathValue"));
	}

	public Object[] buildInvalidFileParams() {
		return new Object[] { 
				new Object[] { null, "Folder" }, 
				new Object[] { Mockito.mock(File.class), "does not exists" },
				new Object[] { new File(MyMojoTest.class.getResource("/samples/file1.xml").getPath()), "not actually a folder" } };
	}

	@Test
	@Parameters(method = "buildInvalidFileParams")
	public void testListFilePathsFail(File folderParam, String expectedErrMsg) {
		try {
			sut.listFilePaths(folderParam);
			fail();
		} catch (FileServiceException e) {
			assertThat(e.getMessage(), CoreMatchers.containsString(expectedErrMsg));
			Mockito.verifyZeroInteractions(fileService);
		}
	}

	@Test
	public void testBuildDestFilePathSuccess() throws FileServiceException {

		String sourceFilePath = "sourceFilePath";
		File destFolder = validSrcFolder;
		when(fileService.buildDestFilePath(sourceFilePath, destFolder)).thenReturn("myDestFilePath");

		String rs = sut.buildDestFilePath(sourceFilePath, destFolder);
		verify(fileService).buildDestFilePath(sourceFilePath, destFolder);
		assertNotNull(rs);
		Assert.assertTrue(rs.equalsIgnoreCase("myDestFilePath"));
	}

	public Object[] buildInvalidDestFilePathParams() {
		return new Object[] { new Object[] { null, mock(File.class), "sourceFilePath" },
				new Object[] { "", mock(File.class), "sourceFilePath" },
				new Object[] { "non-existing-path", mock(File.class), "destFolder" },
				new Object[] { "non-existing-path", null, "destFolder" } };
	}

	@Test
	@Parameters(method = "buildInvalidDestFilePathParams")
	public void testBuildDestFilePathFail(String sourceFilePath, File destFolder, String expectedMsgPart) {

		try {
			sut.buildDestFilePath(sourceFilePath, destFolder);
			fail("unreachable");
		} catch (FileServiceException e) {
			assertThat(e.getMessage(), containsString(expectedMsgPart));
		}
		verifyZeroInteractions(fileService);
	}

	@Test
	public void testRemoveFileIfAlreadyExistsSuccess() throws FileServiceException {

		when(fileService.removeFileIfAlreadyExists(validSrcFile.getPath())).thenReturn(Boolean.TRUE);

		boolean rs = sut.removeFileIfAlreadyExists(validSrcFile.getPath());
		verify(fileService).removeFileIfAlreadyExists(validSrcFile.getPath());
		assertTrue(rs);
	}

	@Test
	public void testRemoveFileIfAlreadyExistsFail() {
		try {
			sut.removeFileIfAlreadyExists(null);
			fail("unreachable");
		} catch (FileServiceException e) {
			assertThat(e.getMessage(), containsString("filePath"));
			verifyZeroInteractions(fileService);
		}
	}

}
