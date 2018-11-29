package com.github.mogikanen9.maven.plugins.xml.cleanup.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

/**
 * @author mogikanen9
 */
public interface FileService {

	List<String> listFilePaths(File folder, FilenameFilter filenameFilter) throws FileServiceException;
	String buildDestFilePath(String sourceFilePath, File destFolder) throws FileServiceException;
	boolean removeFileIfAlreadyExists(String filePath) throws FileServiceException;
	boolean fileExists(String filePath) throws FileServiceException;
}
