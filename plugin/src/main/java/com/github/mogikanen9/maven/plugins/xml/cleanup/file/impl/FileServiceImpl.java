package com.github.mogikanen9.maven.plugins.xml.cleanup.file.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.github.mogikanen9.maven.plugins.xml.cleanup.file.FileService;
import com.github.mogikanen9.maven.plugins.xml.cleanup.file.FileServiceException;

/**
 * @author mogikanen9
 */
public final class FileServiceImpl implements FileService {

	@Override
	public List<String> listFilePaths(File folder, FilenameFilter fileFilter) throws FileServiceException {
		return Arrays.stream(folder.listFiles(fileFilter)).map(file -> file.getAbsolutePath())
				.collect(Collectors.toList());

	}

	@Override
	public String buildDestFilePath(String sourceFilePath, File destFolder) throws FileServiceException {
		String fileName = new File(sourceFilePath).getName();
		String destFilePath = destFolder.getAbsolutePath() + File.separator + fileName;
		return destFilePath;
	}

	@Override
	public boolean removeFileIfAlreadyExists(String filePath) throws FileServiceException {
		try {
			boolean result = false;
			File file = new File(filePath);
			if (file.exists()) {
				if (!file.delete()) {
					throw new FileServiceException(
							String.format("File '%s' cannot be deleted", file.getAbsolutePath()));
				} else {
					result = true;
				}
			}
			return result;
		} catch (Exception e) {
			throw new FileServiceException(e);
		}
	}

	@Override
	public String generateFileCopy(String filePath) throws FileServiceException {
		Path source = Paths.get(filePath);
		Path target = Paths.get(filePath);
		try {
			Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new FileServiceException(e);
		}

		return target.toFile().getPath();
	}

	@Override
	public boolean fileExists(String filePath) throws FileServiceException {
		return new File(filePath).exists();
	}

}
