package com.mogikanensoftware.maven.plugins.xml.cleanup.processor.rule.impl;

import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.rule.Rule;

import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * @author mogikanen9
 */

@AllArgsConstructor
@ToString
public final class XPathRule implements Rule {

	private String rule;

	@Override
	public String getRuleDefinition() {
		return rule;
	}

}
