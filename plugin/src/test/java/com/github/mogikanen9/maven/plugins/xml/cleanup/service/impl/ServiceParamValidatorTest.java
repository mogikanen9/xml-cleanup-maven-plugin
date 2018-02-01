package com.github.mogikanen9.maven.plugins.xml.cleanup.service.impl;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.github.mogikanen9.maven.plugins.test.mockito.rule.MockitoInitRule;
import com.github.mogikanen9.maven.plugins.xml.cleanup.processor.rule.Rule;
import com.github.mogikanen9.maven.plugins.xml.cleanup.service.CleanupException;
import com.github.mogikanen9.maven.plugins.xml.cleanup.service.Request;
import com.github.mogikanen9.maven.plugins.xml.cleanup.service.Service;
import com.github.mogikanen9.maven.plugins.xml.cleanup.service.impl.ServiceParamValidator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class ServiceParamValidatorTest {

	@org.junit.Rule
	public MockitoInitRule mockitoRule;

	@Mock
	private Service service;

	private ServiceParamValidator sut;

	public ServiceParamValidatorTest() {
		super();
		mockitoRule = new MockitoInitRule(this);
	}

	@Before
	public void setUp() throws Exception {
		sut = new ServiceParamValidator(service);
	}

	@After
	public void tearDown() throws Exception {
		sut = null;
	}

	@Test
	public void testServiceParamValidatorNull() {
		try {
			new ServiceParamValidator(null).cleanup(mock(Request.class));
		} catch (CleanupException e) {
			assertThat(e.getMessage(), containsString("Target service cannot be null"));
		}
	}

	@Test
	public void testCleanup() throws CleanupException {
		Request request = mock(Request.class);
		when(request.getSrcFilePath()).thenReturn("smoeshit-src-path");
		when(request.getDestFilePath()).thenReturn("smoeshit-dest-path");
		when(request.getRules()).thenReturn(Arrays.asList(mock(Rule.class)));
		sut.cleanup(request);
		Mockito.verify(service).cleanup(request);
	}

	public Object[] buildInvalidRequest(){
		return new Object[]{
				new Object[]{null,"Request"},
				new Object[]{new Request(null, null, null),"srcFilePath"},
				new Object[]{new Request("", null, null),"srcFilePath"},
				new Object[]{new Request("scrFilePath", null, null),"destFilePath"},
				new Object[]{new Request("scrFilePath", "", null),"destFilePath"},
				new Object[]{new Request("scrFilePath", "destFilePath", null),"rules"},
				new Object[]{new Request("scrFilePath", "destFilePath", new ArrayList<>()),"rules"}
		};
	}
	
	@Test
	@Parameters(method="buildInvalidRequest")
	public void testInvalidRequest(Request request, String expectedMsgPart){
		try {
			sut.cleanup(request);
			Assert.fail("unreachable");
		} catch (CleanupException e) {
			assertThat(e.getMessage(), containsString(expectedMsgPart));
			verifyZeroInteractions(service);
		}
	}
}
