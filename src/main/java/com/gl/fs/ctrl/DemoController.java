package com.gl.fs.ctrl;

import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
@Api(tags = "002、DemoController", description = "Demo文件控制器")
@RestController
public class DemoController {
	@Autowired
	private FastFileStorageClient storageClient;


	@ApiOperation("上传文件")
	@RequestMapping(value = "/upload",method = RequestMethod.POST)
	public String uploadfile(@RequestParam MultipartFile file) throws IOException {
		InputStream is = file.getInputStream();
		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		StorePath returnpath = storageClient.uploadFile("group1", is, is.available(), ext);
		return returnpath.getFullPath();
	}

	@ApiOperation("下载文件")
	@RequestMapping(value="/download",method = RequestMethod.GET)
	public void fdownload(String path, HttpServletResponse response) throws IOException {
		byte[] result = storageClient.downloadFile("group1", path);
		response.setHeader("Content-Disposition", "attachment;filename=" + path);
		response.getOutputStream().write(result);
		response.getOutputStream().close();
	}


}