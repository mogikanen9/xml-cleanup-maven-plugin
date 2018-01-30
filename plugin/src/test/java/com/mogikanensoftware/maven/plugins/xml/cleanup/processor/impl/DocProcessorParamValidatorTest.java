package com.mogikanensoftware.maven.plugins.xml.cleanup.processor.impl;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.mogikanensoftware.maven.plugins.test.mockito.rule.MockitoInitRule;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.Action;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.DocProcessor;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.DocProcessorException;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.DocProcessorParam;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.rule.Rule;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.rule.impl.XPathRule;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class DocProcessorParamValidatorTest {

	@org.junit.Rule
	public MockitoInitRule mockitoRule;

	@Mock
	private DocProcessor docProcessor;

	private DocProcessorParamValidator sut;

	private String validFilePath;

	private List<Rule> validRules = Arrays.asList(new XPathRule("rule1"));

	public DocProcessorParamValidatorTest() {
		super();
		mockitoRule = new MockitoInitRule(this);
	}

	@Before
	public void setUp() throws Exception {

		sut = new DocProcessorParamValidator(docProcessor);
		validFilePath = DocProcessorParamValidatorTest.class.getClass().getResource("/samples/file1.xml").getPath();
	}

	@After
	public void tearDown() throws Exception {
		sut = null;
	}

	@Test
	public void testProcessVerifyTarget() throws DocProcessorException {

		String srcFilePath = validFilePath;
		String destFilePath = validFilePath;
		Action action = Action.REMOVE_NODE;

		DocProcessorParam param = new DocProcessorParam(srcFilePath, destFilePath, validRules, action); 
		
		sut.process(param);

		Mockito.verify(docProcessor).process(param);
	}

	public Object[] buildInvalidParams() {
		return new Object[] { new Object[] { null, validFilePath, validRules, Action.REMOVE_NODE, "Source file" },
				new Object[] { "", validFilePath, validRules, Action.REMOVE_NODE, "Source file" },
				new Object[] { validFilePath, "", validRules, Action.REMOVE_NODE, "Source file" } };
	}

	@Test
	@Parameters(method = "buildInvalidParams")
	public void testProcessInvalidSrcFilePath(String srcFilePath, String destFilePath, List<Rule> rules, Action action,
			String erroMsgExpected) {
		try {
			sut.process(new DocProcessorParam(srcFilePath, destFilePath, rules, action));
			Assert.fail("unreachable");
		} catch (DocProcessorException e) {
			assertThat(e.getMessage(), containsString(erroMsgExpected));
			verifyZeroInteractions(docProcessor);
		}

	}

}
