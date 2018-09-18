package com.github.mogikanen9.maven.plugins.xml.cleanup.processor.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

import com.github.mogikanen9.maven.plugins.xml.cleanup.MyMojoTest;

/**
 * 
 * @author mogikanen9
 *
 */
public class JAXPDocHelperTest {

	private static final String PROPERTY = "property";

	private static final String ODA_DATA_SOURCE = "oda-data-source";

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

	@Test
	public void testRemoveNodeEmptyMock() {
		sut.removeNode(mock(Node.class));
	}

	protected Node createPropertyNode(Document doc, String attributeName, String attrValue, String textValue) {
		
		Element  tagNode = doc.createElement(PROPERTY);
		tagNode.setAttribute(attributeName, attrValue);
		
		Node tagData = doc.createTextNode(textValue);
		tagNode.appendChild(tagData);
		
		return tagNode;
	}
	
	protected Node createPropertyNodeWithSubNodeTextValue(Document doc, String textValue) {
		
		Element  tagNode = doc.createElement(PROPERTY);
		
		Element  valueNode = doc.createElement("value");
		tagNode.appendChild(valueNode);
		
		Node tagData = doc.createTextNode(textValue);
		valueNode.appendChild(tagData);
		
		return tagNode;
	}
	
	@Test
	public void testRemoveNodeWithParentOnly() throws ParserConfigurationException {
		
		final String controlXml = "<oda-data-source><property><value>JNDI12</value></property></oda-data-source>";				
		
		Document testDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
		.newDocument();
		
		Element odaDs = testDoc.createElement(ODA_DATA_SOURCE);		
		
		Node userNode = createPropertyNodeWithSubNodeTextValue(testDoc, "user12");
		odaDs.appendChild(userNode);
		
		Node jndiNode = createPropertyNodeWithSubNodeTextValue(testDoc, "JNDI12");
		odaDs.appendChild(jndiNode);				
		
		testDoc.appendChild(odaDs);
		
		sut.removeNode(userNode);
		

		Diff myDiff = DiffBuilder.compare(Input.fromString(controlXml))
	              .withTest(Input.fromDocument(testDoc))
	              .build();

		Assert.assertFalse("XML similar " + myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testRemoveNodeWithParentAndChildren() throws ParserConfigurationException {
		
		final String controlXml = "<oda-data-source><property name='odaJndi'>JNDI1</property></oda-data-source>";				
		
		Document testDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
		.newDocument();
		
		Element odaDs = testDoc.createElement(ODA_DATA_SOURCE);		
		
		Node userNode = createPropertyNode(testDoc, "name", "odaUser","user1");
		odaDs.appendChild(userNode);
		
		Node jndiNode = createPropertyNode(testDoc, "name", "odaJndi","JNDI1");
		odaDs.appendChild(jndiNode);				
		
		testDoc.appendChild(odaDs);
		
		sut.removeNode(userNode);
		

		Diff myDiff = DiffBuilder.compare(Input.fromString(controlXml))
	              .withTest(Input.fromDocument(testDoc))
	              .build();

		Assert.assertFalse("XML similar " + myDiff.toString(), myDiff.hasDifferences());
	}
	
	@Test
	public void testSave() throws FileNotFoundException, TransformerFactoryConfigurationError, TransformerException {
		String destFilePath = validSrcFile.getParentFile().getAbsolutePath() + java.io.File.separator + "destFile.xml";
		sut.save(mock(Document.class), destFilePath);
		Assert.assertTrue(new File(destFilePath).exists());
	}

}
