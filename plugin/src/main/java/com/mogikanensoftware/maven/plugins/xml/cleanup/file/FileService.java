package com.mogikanensoftware.maven.plugins.xml.cleanup.file;

import java.io.File;
import java.util.List;

/**
 * @author mogikanen9
 */
public interface FileService {

	List<String> listFilePaths(File folder) throws FileServiceException;
	String buildDestFilePath(String sourceFilePath, File destFolder) throws FileServiceException;
	boolean removeFileIfAlreadyExists(String filePath) throws FileServiceException;
}
