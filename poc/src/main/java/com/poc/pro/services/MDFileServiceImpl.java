package com.poc.pro.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.poc.pro.dao.MDFileDao;
import com.poc.pro.model.MDFile;

@Service
public class MDFileServiceImpl implements MDFileService {
	static Logger logger = LoggerFactory.getLogger(MDFileServiceImpl.class);
	@Autowired
	MDFileDao fdao;

	@Override
	public MDFile getFile(int id) {
		logger.info("getfile by id is " + id);
		return fdao.getFile(id);
	}

	@Override
	public int saveFile(MDFile file) {
		logger.info("your file data added  ----------  > " + file.getName());
		return fdao.saveFile(file);
	}

	@Override
	public void DeleteFile(MDFile file, int id) {
		logger.info("your file is deleted --------- >   ", file.getName() + "\t" + id);
		fdao.DeleteFile(file, id);

	}

	@Override
	public java.util.List<MDFile> getAllFiles() {
		// TODO Auto-generated method stub
		logger.info("file list  -------------->");
		return fdao.getAllFiles();
	}

}
