package com.github.mogikanen9.maven.plugins.xml.cleanup.processor.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.codehaus.plexus.util.IOUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author mogikanen9
 */
public class JAXPDocHelper {

	public Document parse(String filePath) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		return builder.parse(new File(filePath));
	}

	NodeList search(Document doc, String rule) throws XPathExpressionException {
		XPath xPath = XPathFactory.newInstance().newXPath();
		XPathExpression xExpress = xPath.compile(rule);// ex.: "/orgs/org/item[text()='b']"
		return (NodeList) xExpress.evaluate(doc.getDocumentElement(), XPathConstants.NODESET);
	}

	public void removeNodes(NodeList nodeList) {
		for (int index = 0; index < nodeList.getLength(); index++) {
			Node node = nodeList.item(index);
			removeNode(node);
		}
	}

	public void removeNode(Node node) {
		if (node != null) {
			while (node.hasChildNodes()) {
				removeNode(node.getFirstChild());
			}

			Node parent = node.getParentNode();
			if (parent != null) {
				parent.removeChild(node);
			}
		}
	}

	public void save(Document doc, String destFilePath)
			throws TransformerFactoryConfigurationError, TransformerException, FileNotFoundException {
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		
		Transformer transformer = transformerFactory.newTransformer();
		
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

		DOMSource domSource = new DOMSource(doc);
		OutputStream out = new FileOutputStream(new File(destFilePath));
		try {
			StreamResult sr = new StreamResult(out);
			transformer.transform(domSource, sr);
		} finally {
				IOUtil.close(out);
		}

	}

}
