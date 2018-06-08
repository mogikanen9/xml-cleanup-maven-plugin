package com.github.mogikanen9.maven.plugins.xml.cleanup.file.impl;

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
import java.io.FilenameFilter;
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

import com.github.mogikanen9.maven.plugins.test.mockito.rule.MockitoInitRule;
import com.github.mogikanen9.maven.plugins.xml.cleanup.MyMojoTest;
import com.github.mogikanen9.maven.plugins.xml.cleanup.file.FileService;
import com.github.mogikanen9.maven.plugins.xml.cleanup.file.FileServiceException;
import com.github.mogikanen9.maven.plugins.xml.cleanup.file.impl.ExtensionFileFilter;
import com.github.mogikanen9.maven.plugins.xml.cleanup.file.impl.FileServiceParamValidator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * 
 * @author mogikanen9
 *
 */
@RunWith(JUnitParamsRunner.class)
public class FileServiceParamValidatorTest {

	@org.junit.Rule
	public MockitoInitRule mockitoRule;

	@Mock
	private FileService fileService;

	private FileServiceParamValidator sut;

	private File validSrcFolder;

	private File validSrcFile;

	private java.io.FilenameFilter emptyFilenameFiler;

	public FileServiceParamValidatorTest() {
		super();
		mockitoRule = new MockitoInitRule(this);
	}

	@Before
	public void setUp() throws Exception {
		sut = new FileServiceParamValidator(fileService);
		validSrcFolder = new File(MyMojoTest.class.getResource("/samples").getPath());
		validSrcFile = new File(MyMojoTest.class.getResource("/samples/file1.xml").getPath());
		emptyFilenameFiler = new ExtensionFileFilter("");
	}

	@After
	public void tearDown() throws Exception {
		sut = null;
		validSrcFolder = null;
		validSrcFile = null;
		emptyFilenameFiler = null;
	}

	@Test
	public void testListFilePathsSuccess() throws FileServiceException {
		when(fileService.listFilePaths(validSrcFolder, emptyFilenameFiler))
				.thenReturn(Arrays.asList("mySourceFilePathValue"));

		List<String> rs = sut.listFilePaths(validSrcFolder, emptyFilenameFiler);
		verify(fileService).listFilePaths(validSrcFolder, emptyFilenameFiler);
		assertNotNull(rs);
		assertTrue(rs.size() == 1);
		assertTrue(rs.get(0).equalsIgnoreCase("mySourceFilePathValue"));
	}

	public Object[] buildInvalidFileParams() {
		return new Object[] { new Object[] { null, emptyFilenameFiler, "Folder" },
				new Object[] { Mockito.mock(File.class), emptyFilenameFiler, "does not exists" },
				new Object[] { new File(MyMojoTest.class.getResource("/samples/file1.xml").getPath()),
						emptyFilenameFiler, "not actually a folder" },
				new Object[] { new File(MyMojoTest.class.getResource("/samples").getPath()),
						null, "FileFilter cannot be null" }};
	}

	@Test
	@Parameters(method = "buildInvalidFileParams")
	public void testListFilePathsFail(File folderParam, FilenameFilter filter, String expectedErrMsg) {
		try {
			sut.listFilePaths(folderParam, filter);
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
