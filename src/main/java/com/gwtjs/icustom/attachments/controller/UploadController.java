package com.gwtjs.icustom.attachments.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/attachments")
public class UploadController {


	@RequestMapping(value = "/hello")
	public String hello(HashMap<String, Object> map) {
		map.put("hello", "欢迎进入HTML页面");
		return "/test";
	}
	@RequestMapping(value = "/showUpload", method = RequestMethod.GET)
	public ModelAndView showUpload() {
		return new ModelAndView("/UploadProgressDemo");
	}

	@RequestMapping("/upload")
	@ResponseBody
	public void uploadFile(MultipartFile file) {
		System.out.println(file.getOriginalFilename());
	}
	
	@RequestMapping("/uploads")
	@ResponseBody
	public Map<String,Object> uploadFiles(HttpServletRequest request) {
		Map<String,Object> result = new HashMap<String,Object>();
		List<MultipartFile> files2 = ((MultipartHttpServletRequest) request).getFiles("file");
		BufferedOutputStream stream = null;
		String uploadPath = "d:/";
		result.put("fileLength", files2.size());
		int i = 0;
		for (MultipartFile file : files2) {
			String originalFilename = file.getOriginalFilename();
			i++;
			validateType(uploadPath);
			if (!file.isEmpty()) {
				try {
					byte[] bytes = file.getBytes();
					stream = new BufferedOutputStream(new FileOutputStream(new File(uploadPath,"mll_"+originalFilename)));
					stream.write(bytes);
					result.put("file"+i, uploadPath+originalFilename);
					stream.close();
				} catch (Exception e) {
					result.put("message", originalFilename+"You failed to upload  => " + e.getMessage());
				}
			} else {
				result.put("message", originalFilename+"You failed to upload  because the file was empty.");
			}
		}
		return result;
	}
	
	/**
	 * 验证上传的文件类型
	 * @return
	 */
	private Map<String,Object> validateType(String path){
		Map<String,Object> result = new HashMap<String,Object>();
		File file = new File(path);
		if(!file.exists())
			file.mkdirs();
		return result;
	}
	
	/**
	 * 验证上传的文件路径,没有则创建
	 * @return
	 */
	private Map<String,Object> validatePath(String path){
		Map<String,Object> result = new HashMap<String,Object>();
		File file = new File(path);
		if(!file.exists())
			file.mkdirs();
		return result;
	}

}