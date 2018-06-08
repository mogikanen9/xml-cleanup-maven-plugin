package com.github.mogikanen9.maven.plugins.xml.cleanup.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.mogikanen9.maven.plugins.xml.cleanup.file.FileService;
import com.github.mogikanen9.maven.plugins.xml.cleanup.service.CleanupException;
import com.github.mogikanen9.maven.plugins.xml.cleanup.service.Request;
import com.github.mogikanen9.maven.plugins.xml.cleanup.service.impl.XmlServiceImpl;

/**
 * 
 * @author mogikanen9
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class XmlServiceImplTest {

	private XmlServiceImpl sut;
	
	@Mock
	private FileService fileService;
	
	@Before
	public void setUp() throws Exception {
		sut = new XmlServiceImpl(fileService);
	}

	@After
	public void tearDown() throws Exception {
		sut = null;
	}

	@Test
	public void testXmlServiceImpl() {
		new XmlServiceImpl(null);
	}

	@Test(expected=CleanupException.class)
	public void testCleanup() throws CleanupException {
		sut.cleanup(new Request(null, null, null));
	}

}
