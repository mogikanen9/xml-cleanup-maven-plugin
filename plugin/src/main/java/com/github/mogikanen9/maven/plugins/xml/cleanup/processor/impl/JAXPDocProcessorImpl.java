package com.github.mogikanen9.maven.plugins.xml.cleanup.processor.impl;

import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.github.mogikanen9.maven.plugins.xml.cleanup.processor.Action;
import com.github.mogikanen9.maven.plugins.xml.cleanup.processor.DocProcessor;
import com.github.mogikanen9.maven.plugins.xml.cleanup.processor.DocProcessorException;
import com.github.mogikanen9.maven.plugins.xml.cleanup.processor.Param;
import com.github.mogikanen9.maven.plugins.xml.cleanup.processor.Result;
import com.github.mogikanen9.maven.plugins.xml.cleanup.processor.rule.Rule;
import com.github.mogikanen9.maven.plugins.xml.cleanup.processor.rule.impl.XPathRule;

import lombok.AllArgsConstructor;

/**
 * @author mogikanen9
 */

@AllArgsConstructor
public final class JAXPDocProcessorImpl implements DocProcessor {

	private JAXPDocHelper helper;

	@Override
	public Result process(Param param) throws DocProcessorException {

		try {

			Map<Rule, Integer> processedNodes = new HashMap<>();

			Document doc = helper.parse(param.getSrcFilePath());

			boolean needToSave = false;
			for (Rule rule : param.getRules()) {
				int processedNodesCnt = applyRule(rule, doc, param);
				if (processedNodesCnt > 0) {
					processedNodes.put(rule, processedNodesCnt);
					needToSave = true;
				}
			}

			if (needToSave) {
				helper.save(doc, param.getDestFilePath());
			}

			return new Result(processedNodes);

		} catch (Exception e) {
			throw new DocProcessorException(e.getMessage(), e);
		}

	}

	protected int applyRule(Rule rule, Document doc, Param param)
			throws XPathExpressionException, DocProcessorException {
		
		int processedNodes = -1;
		
		if (rule instanceof XPathRule) {
			
			NodeList nodeList = helper.search(doc, rule.getRuleDefinition());

			if (nodeList != null && nodeList.getLength() > 0) {

				if (param.getAction().equals(Action.REMOVE_NODE)) {
					helper.removeNodes(nodeList);
					processedNodes = nodeList.getLength();
				} else {
					throw new DocProcessorException(String.format("Unsupported action->%s", param.getAction()));
				}
			}

		} else {
			throw new DocProcessorException(
					String.format("Only XPathRule types are supported. Received rule->%s", rule.getClass()));
		}
		return processedNodes;
	}

}
