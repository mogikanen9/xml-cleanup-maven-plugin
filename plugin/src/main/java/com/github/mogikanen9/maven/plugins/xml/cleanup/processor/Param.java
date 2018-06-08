package com.github.mogikanen9.maven.plugins.xml.cleanup.processor;

import java.util.List;

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
@AllArgsConstructor
@ToString
public class Param {

	private String srcFilePath;
	private String destFilePath;
	private List<Rule> rules;
	private Action action;
}
