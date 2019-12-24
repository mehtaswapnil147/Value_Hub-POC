package com.poc.pro.services;

import java.util.List;

import com.poc.pro.model.MDFile;

public interface MDFileService {
	
	public MDFile getFile(int id);
	public int saveFile(MDFile filename);
	public void DeleteFile(MDFile file, int id);
	public List<MDFile> getAllFiles();

}