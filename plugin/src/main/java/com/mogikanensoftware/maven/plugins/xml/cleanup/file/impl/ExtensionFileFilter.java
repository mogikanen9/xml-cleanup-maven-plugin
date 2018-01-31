package com.mogikanensoftware.maven.plugins.xml.cleanup.file.impl;

import java.io.File;
import java.io.FilenameFilter;

import org.codehaus.plexus.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = { "fileExtension" })
public class ExtensionFileFilter implements FilenameFilter {

	private String fileExtension;

	@Override
	public boolean accept(File dir, String name) {
		boolean rs = true;
		if (StringUtils.isNotEmpty(fileExtension)) {
			rs = name.toLowerCase().endsWith(fileExtension);
		}
		return rs;
	}

}
