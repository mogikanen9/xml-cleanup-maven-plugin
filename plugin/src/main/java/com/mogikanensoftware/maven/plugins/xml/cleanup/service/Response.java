package com.mogikanensoftware.maven.plugins.xml.cleanup.service;

import com.mogikanensoftware.maven.plugins.xml.cleanup.processor.Result;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author mogikanen9
 */
@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class Response {

	private Result docProcessorRs;
}
