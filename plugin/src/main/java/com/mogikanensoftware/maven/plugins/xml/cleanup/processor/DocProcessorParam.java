package com.mogikanensoftware.maven.plugins.xml.cleanup.processor;

import java.util.List;

import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.rule.Rule;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DocProcessorParam {

	private String srcFilePath;
	private String destFilePath;
	private List<Rule> rules;
	private Action action;
}
