package com.mogikanensoftware.maven.plugins.xml.cleanup.processor;

import java.util.Map;

import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.rule.Rule;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class Result {

	private Map<Rule, Integer> processedNodes;
}
