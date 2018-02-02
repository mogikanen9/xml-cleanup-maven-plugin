package com.github.mogikanen9.maven.plugins.xml.cleanup.service;

import java.util.List;

import com.github.mogikanen9.maven.plugins.xml.cleanup.processor.rule.Rule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author mogikanen9
 */

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Request {

	private String srcFilePath;
	private String destFilePath;	
	private List<Rule> rules;
}
