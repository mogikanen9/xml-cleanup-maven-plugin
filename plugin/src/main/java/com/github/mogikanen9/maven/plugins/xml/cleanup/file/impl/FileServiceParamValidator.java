package com.github.mogikanen9.maven.plugins.xml.cleanup.file.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

import org.codehaus.plexus.util.StringUtils;

import com.github.mogikanen9.maven.plugins.xml.cleanup.file.FileService;
import com.github.mogikanen9.maven.plugins.xml.cleanup.file.FileServiceException;

import lombok.AllArgsConstructor;

/**
 * 
 * @author mogikanen9
 *
 */

@AllArgsConstructor
public final class FileServiceParamValidator implements FileService {

	private FileService target;

	@Override
	public List<String> listFilePaths(File folder, FilenameFilter fileFilter) throws FileServiceException {
		if (folder == null) {
			throw new FileServiceException("Folder cannot be null.");
		} else if (!folder.exists()) {
			throw new FileServiceException(String.format("Folder '%s' does not exists", folder.getAbsolutePath()));
		} else if (!folder.isDirectory()) {
			throw new FileServiceException(
					String.format("Folder '%s' is not actually a folder", folder.getAbsolutePath()));
		} else if (fileFilter==null){
			throw new FileServiceException("FileFilter cannot be null.");
		}else {
			return target.listFilePaths(folder, fileFilter);
		}
	}

	@Override
	public String buildDestFilePath(String sourceFilePath, File destFolder) throws FileServiceException {
		if (StringUtils.isEmpty(sourceFilePath)) {
			throw new FileServiceException("Param 'sourceFilePath' cannot be null or empty.");
		} else if (destFolder == null) {
			throw new FileServiceException(String.format("Folder from 'destFolder' param '%s' does not exists", destFolder));
		} else if (!destFolder.isDirectory()) {
			throw new FileServiceException(
					String.format("Folder from 'destFolder' param '%s' is nota a actually a folder", destFolder.getAbsolutePath()));
		}
		return target.buildDestFilePath(sourceFilePath, destFolder);
	}

	@Override
	public boolean removeFileIfAlreadyExists(String filePath) throws FileServiceException {
		if (StringUtils.isEmpty(filePath)) {
			throw new FileServiceException("Param 'filePath' cannot be null or empty.");
		} else {
			return target.removeFileIfAlreadyExists(filePath);
		}
	}

}
