package com.github.mogikanen9.maven.plugins.xml.cleanup.file.impl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.codehaus.plexus.util.IOUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.github.mogikanen9.maven.plugins.xml.cleanup.MyMojoTest;
import com.github.mogikanen9.maven.plugins.xml.cleanup.file.FileService;
import com.github.mogikanen9.maven.plugins.xml.cleanup.file.FileServiceException;

/**
 * 
 * @author mogikanen9
 *
 */
public class FileServiceImplTest {

	FileService sut;

	@BeforeEach
	public void setUp() throws Exception {
		sut = new FileServiceImpl();
	}

	@AfterEach
	public void tearDown() throws Exception {
		sut = null;
	}

	@Test
	public void testListFilePaths() throws FileServiceException {
		File srcFolder = new File(MyMojoTest.class.getResource("/samples/").getPath());
		List<String> rs = sut.listFilePaths(srcFolder, new ExtensionFileFilter(""));
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

	@Test
	public void testRemoveFileIfAlreadyExistsNull() throws FileServiceException {
		assertThrows(FileServiceException.class, () -> sut.removeFileIfAlreadyExists(null));
	}

	@Test
	public void testRemoveFileIfAlreadyExistsNE() throws FileServiceException {
		boolean rs = sut.removeFileIfAlreadyExists("");
		assertFalse(rs);
	}

	@Disabled
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

	@Test
	public void testGenerateFileCopyEx() throws FileServiceException {		
		assertThrows(FileServiceException.class, ()->{
			sut.generateFileCopy("garbage");
		});
	}
	
	@Test
	public void testGenerateFileCopy() throws FileServiceException {
		Path existingFile = (new File(MyMojoTest.class.getResource("/samples/file1.xml").getPath())).toPath();
		String existingFilePath = existingFile.toFile().getAbsolutePath();
		String rs = sut.generateFileCopy(existingFilePath);
		assertNotNull(rs);
		assertEquals(existingFilePath, rs);
	}
}
