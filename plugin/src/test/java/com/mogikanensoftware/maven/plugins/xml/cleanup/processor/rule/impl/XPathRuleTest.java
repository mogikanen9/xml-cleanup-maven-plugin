package com.mogikanensoftware.maven.plugins.xml.cleanup.processor.rule.impl;

import org.junit.Assert;
import org.junit.Test;

import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.rule.Rule;

public class XPathRuleTest {

	@Test
	public void testGetRuleDefinition() {
		Rule rule = new XPathRule("MyRuleDeifinitionValue");
		Assert.assertTrue(rule.getRuleDefinition().equals("MyRuleDeifinitionValue"));
	}

}
