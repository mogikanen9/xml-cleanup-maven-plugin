package com.github.mogikanen9.maven.plugins.xml.cleanup.processor.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.github.mogikanen9.maven.plugins.xml.cleanup.MyMojoTest;
import com.github.mogikanen9.maven.plugins.xml.cleanup.processor.impl.JAXPDocHelper;

/**
 * 
 * @author mogikanen9
 *
 */
public class JAXPDocHelperTest {

	private JAXPDocHelper sut;

	File validSrcFile;

	@Before
	public void setUp() throws Exception {
		sut = new JAXPDocHelper();
		validSrcFile = new File(MyMojoTest.class.getResource("/samples/file1.xml").getPath());
	}

	@After
	public void tearDown() throws Exception {
		sut = null;
		validSrcFile = null;
	}

	@Test
	public void testParse() throws ParserConfigurationException, SAXException, IOException {
		Document doc = sut.parse(validSrcFile.getAbsolutePath());
		assertNotNull(doc);
	}

	@Ignore
	@Test
	public void testSearch() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testRemoveNodes() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testRemoveNode() {
		fail("Not yet implemented");
	}

	@Test
	public void testSave() throws FileNotFoundException, TransformerFactoryConfigurationError, TransformerException {
		String destFilePath = validSrcFile.getParentFile().getAbsolutePath()+java.io.File.separator+"destFile.xml";
		sut.save(mock(Document.class), destFilePath);
		Assert.assertTrue(new File(destFilePath).exists());
	}

}
