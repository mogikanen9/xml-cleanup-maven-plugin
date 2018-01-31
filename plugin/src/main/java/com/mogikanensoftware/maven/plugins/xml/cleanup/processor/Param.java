package com.mogikanensoftware.maven.plugins.xml.cleanup.processor;

import java.util.List;

import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.rule.Rule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Param {

	private String srcFilePath;
	private String destFilePath;
	private List<Rule> rules;
	private Action action;
}
