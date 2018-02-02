package com.github.mogikanen9.maven.plugins.xml.cleanup.processor.rule.impl;

import org.junit.Assert;
import org.junit.Test;

import com.github.mogikanen9.maven.plugins.xml.cleanup.processor.rule.Rule;
import com.github.mogikanen9.maven.plugins.xml.cleanup.processor.rule.impl.XPathRule;

public class XPathRuleTest {

	@Test
	public void testGetRuleDefinition() {
		Rule rule = new XPathRule("MyRuleDeifinitionValue");
		Assert.assertTrue(rule.getRuleDefinition().equals("MyRuleDeifinitionValue"));
	}

}
