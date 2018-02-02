package com.github.mogikanen9.maven.plugins.xml.cleanup.processor.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.github.mogikanen9.maven.plugins.xml.cleanup.MyMojoTest;
import com.github.mogikanen9.maven.plugins.xml.cleanup.processor.impl.JAXPDocHelper;

public class JAXPDocHelperTest {

	private JAXPDocHelper helper;

	File validSrcFile;

	@Before
	public void setUp() throws Exception {
		helper = new JAXPDocHelper();
		validSrcFile = new File(MyMojoTest.class.getResource("/samples/file1.xml").getPath());
	}

	@After
	public void tearDown() throws Exception {
		helper = null;
		validSrcFile = null;
	}

	@Test
	public void testParse() throws ParserConfigurationException, SAXException, IOException {
		Document doc = helper.parse(validSrcFile.getAbsolutePath());
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

	@Ignore
	@Test
	public void testSave() {
		fail("Not yet implemented");
	}

}
