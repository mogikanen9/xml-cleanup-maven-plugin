package com.mogikanensoftware.maven.plugins.xml.cleanup.processor.impl;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.Action;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.DocProcessor;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.DocProcessorException;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.DocProcessorParam;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.rule.Rule;
import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.rule.impl.XPathRule;

import lombok.AllArgsConstructor;

/**
 * @author mogikanen9
 */

@AllArgsConstructor
public final class JAXPDocProcessorImpl implements DocProcessor {

	private JAXPDocHelper helper;

	@Override
	public void process(DocProcessorParam param) throws DocProcessorException {

		Document doc;
		try {
			doc = helper.parse(param.getSrcFilePath());

			for (Rule rule : param.getRules()) {
				if (rule instanceof XPathRule) {
					NodeList nodeList = helper.search(doc, rule.getRuleDefinition());
					if (param.getAction().equals(Action.REMOVE_NODE)) {
						helper.removeNodes(nodeList);
					} else {
						throw new DocProcessorException(String.format("Unsupported action->%s", param.getAction()));
					}

				} else {
					throw new DocProcessorException(
							String.format("Only XPathRule types are supported. Received rule->", rule.getClass()));
				}
			}

			helper.save(doc, param.getDestFilePath());

		} catch (Exception e) {
			throw new DocProcessorException(e.getMessage(), e);
		}

	}

}
