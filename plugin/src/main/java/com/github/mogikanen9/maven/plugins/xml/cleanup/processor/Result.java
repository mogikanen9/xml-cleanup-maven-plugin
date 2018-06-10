package com.github.mogikanen9.maven.plugins.xml.cleanup.processor;

import java.util.Map;

import com.github.mogikanen9.maven.plugins.xml.cleanup.processor.rule.Rule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 
 * @author mogikanen9
 *
 */
@Getter
@ToString
@AllArgsConstructor
public class Result {

	private Map<Rule, Integer> processedNodes;
}
