package com.mogikanensoftware.maven.plugins.xml.cleanup.file.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.codehaus.plexus.util.IOUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.mogikanensoftware.maven.plugins.xml.cleanup.MyMojoTest;
import com.mogikanensoftware.maven.plugins.xml.cleanup.file.FileService;
import com.mogikanensoftware.maven.plugins.xml.cleanup.file.FileServiceException;

public class FileServiceImplTest {

	FileService sut;

	@Before
	public void setUp() throws Exception {
		sut = new FileServiceImpl();
	}

	@After
	public void tearDown() throws Exception {
		sut = null;
	}

	@Test
	public void testListFilePaths() throws FileServiceException {
		File srcFolder = new File(MyMojoTest.class.getResource("/samples/").getPath());
		List<String> rs = sut.listFilePaths(srcFolder);
		assertNotNull(rs);
		assertTrue(rs.size() >= 1);
	}

	@Test
	public void testBuildDestFilePath() throws FileServiceException {
		File srcFile = new File(MyMojoTest.class.getResource("/samples/file1.xml").getPath());
		File destFolder = new File(MyMojoTest.class.getResource("/samples-dest").getPath());
		String destFilePath = sut.buildDestFilePath(srcFile.getAbsolutePath(), destFolder);
		assertNotNull(destFilePath);
		assertTrue(destFilePath.endsWith("samples-dest" + File.separator + "file1.xml"));
	}

	@Test(expected = FileServiceException.class)
	public void testRemoveFileIfAlreadyExistsNull() throws FileServiceException {
		sut.removeFileIfAlreadyExists(null);
	}

	@Test
	public void testRemoveFileIfAlreadyExistsNE() throws FileServiceException {
		boolean rs = sut.removeFileIfAlreadyExists("");
		assertFalse(rs);
	}

	@Ignore
	@Test
	public void testRemoveFileIfAlreadyExists() throws FileServiceException, FileNotFoundException, IOException {

		File existingFile = new File(FileServiceImplTest.class.getResource("/samples/file1.xml").getPath());
		assertTrue(existingFile.exists());
		
		File newFile = new File(existingFile.getParent()+File.separator+"file4-delete-test.xml");		
		IOUtil.copy(new FileInputStream(existingFile), new FileOutputStream(newFile));
		assertTrue(newFile.exists());
		
		boolean rs = sut
				.removeFileIfAlreadyExists(newFile.getAbsolutePath());
		assertTrue(rs);
	}

}
