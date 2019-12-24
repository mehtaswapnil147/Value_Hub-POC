package com.poc.pro.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poc.pro.model.MDFile;
import com.poc.pro.services.MDFileService;
import com.poc.pro.utility.AppConstant;

@Controller
public class MainControler {

	@Autowired
	Environment env;

	@Autowired
	MDFileService fservice;

	static Logger logger = LoggerFactory.getLogger(MainControler.class);

	@GetMapping("/")
	public String listUploadedFiles(Model model) throws IOException {
		logger.info("its a root dir  ------------------   ");
		List<MDFile> fileList = fservice.getAllFiles();
		logger.info("file list size   ------------------>" + fileList.size());
		model.addAttribute("flist", fileList);
		/*
		 * logger.info("file info is  ----------> " + fileList.get(1).getPath() + "/" +
		 * fileList.get(1).getName()); logger.info("file id is ------ > " +
		 * fileList.get(1).getId());
		 */
		return "index";
	}

	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		String filepath = env.getProperty(AppConstant.FILE_STORE_PATH);
		logger.info("fileapth ==============> " + filepath);
		logger.info("fileupload ---------------->  " + file.getOriginalFilename());
		File f = new File(filepath + "/" + file.getOriginalFilename());
		fservice.saveFile(new MDFile(file.getOriginalFilename(), filepath));
		try (OutputStream outputStream = new FileOutputStream(f)) {
			InputStream inputStream = file.getInputStream();
			IOUtils.copy(inputStream, outputStream);
			outputStream.write(1204);
		} catch (FileNotFoundException e) {
			logger.error("exception  ----- > ", e);
		} catch (IOException e) {
			logger.error("exception --------- > ", e);
		}
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");
		return "redirect:/";
	}

	@GetMapping("/getFile")
	public @ResponseBody String fileContent(@RequestParam("id") int fileId) {
		String filepath = env.getProperty(AppConstant.FILE_STORE_PATH);
		logger.info("getfile -----> " + fileId);
		String data = null;
		MDFile file = fservice.getFile(fileId);
		logger.info("file info---------->" + file);
		try {
			InputStream inputStream = new FileInputStream(filepath + "/" + file.getName());
			StringWriter writer = new StringWriter();
			String encoding = StandardCharsets.UTF_8.name();
			IOUtils.copy(inputStream, writer, encoding);
			if(inputStream !=null)
			{
			data = writer.toString();
			}
			else {
				data ="file not found or data does not available ";
			}
			
		} catch (Exception e) {
			logger.error("error b----------- > ", e);
		}
		return data;
	}
}
