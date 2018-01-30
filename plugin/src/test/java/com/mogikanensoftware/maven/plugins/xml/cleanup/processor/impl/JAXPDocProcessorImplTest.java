package com.mogikanensoftware.maven.plugins.xml.cleanup.processor.impl;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.Arrays;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.xml.sax.SAXException;

import com.mogikanensoftware.maven.plugins.test.mockito.rule.MockitoInitRule;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.Action;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.DocProcessorException;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.DocProcessorParam;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.rule.Rule;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.rule.impl.XPathRule;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class JAXPDocProcessorImplTest {

	private JAXPDocProcessorImpl sut;

	@org.junit.Rule
	public MockitoInitRule mockitoRule;

	@Mock
	private JAXPDocHelper helper;

	public JAXPDocProcessorImplTest() {
		super();
		mockitoRule = new MockitoInitRule(this);
	}

	@Before
	public void setUp() throws Exception {
		sut = new JAXPDocProcessorImpl(helper);
	}

	@After
	public void tearDown() throws Exception {
		sut = null;
	}

	public Object[] buildINvalidParams() {
		return new Object[] { new DocProcessorParam(null, null, null, null),
				new DocProcessorParam("abc", null, null, Action.REMOVE_NODE),
				new DocProcessorParam("abc", "koko", null, Action.REMOVE_NODE),
				new DocProcessorParam("abc", "koko", Arrays.asList(new XPathRule("")), null) };
	}

	@Test(expected = DocProcessorException.class)
	@Parameters(method = "buildINvalidParams")
	public void testProcessFail(DocProcessorParam param) throws DocProcessorException {
		sut.process(param);
		fail("unreachable");
	}

	@Test
	public void testProcessFailXPathRule() {
		try {
			sut.process(new DocProcessorParam("abc", "koko", Arrays.asList(mock(Rule.class)), Action.REMOVE_NODE));
			fail("unreachable");
		} catch (DocProcessorException e) {
			Assert.assertThat(e.getMessage(), CoreMatchers.containsString("Only XPathRule types are supported"));
		}

	}

	@Test
	public void testProcess() throws DocProcessorException, XPathExpressionException, ParserConfigurationException,
			SAXException, IOException {
		sut.process(new DocProcessorParam("abc", "koko", Arrays.asList(new XPathRule("")), Action.REMOVE_NODE));
	}

}
