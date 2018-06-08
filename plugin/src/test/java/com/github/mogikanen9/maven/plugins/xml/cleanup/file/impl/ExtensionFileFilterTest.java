package com.github.mogikanen9.maven.plugins.xml.cleanup.file.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.mogikanen9.maven.plugins.xml.cleanup.file.impl.ExtensionFileFilter;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * 
 * @author mogikanen9
 *
 */
@RunWith(JUnitParamsRunner.class)
public class ExtensionFileFilterTest {

	private ExtensionFileFilter sut;

	private File srcFolder;

	@Before
	public void setUp() throws Exception {
		srcFolder = new File(ExtensionFileFilterTest.class.getResource("/samples/").getPath());
	}

	@After
	public void tearDown() throws Exception {
		srcFolder = null;
	}

	public Object[] buildParams4Success() {
		return new Object[] {
				new Object[] {".xml","file1.xml"},
				new Object[] {"txt","file1.txt"},
				new Object[] {"","file1.xml"},
				new Object[] {null,"file1.xml"}
				};
	}
	
	@Test
	@Parameters(method="buildParams4Success")
	public void testAcceptTrue(String extension, String fileName) {
		sut = new ExtensionFileFilter(extension);
		assertTrue(sut.accept(srcFolder, fileName));
	}
	
	
	public Object[] buildParams4False() {
		return new Object[] {
				new Object[] {".xml","file1.xm"},
				new Object[] {".xml","file1.xmlt"},
				new Object[] {"txt","file1.txt1"}
				};
	}
	
	@Test
	@Parameters(method="buildParams4False")
	public void testAcceptFalse(String extension, String fileName) {
		sut = new ExtensionFileFilter(extension);
		assertFalse(sut.accept(srcFolder, fileName));
	}
}
