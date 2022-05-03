package com.ling.oss.controller;

import com.ling.commonutils.R;
import com.ling.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(value="阿里云文件管理")
@RestController
@RequestMapping("/eduoss/fileoss")
 //跨域
public class FileController {

	@Autowired
	private FileService fileService;

	/**
	 * 文件上传
	 *
	 * @param file
	 */
	@ApiOperation(value = "文件上传")
	@PostMapping("upload")
	public R upload(
			@ApiParam(name = "file", value = "文件", required = true)
			@RequestParam("file") MultipartFile file) {

		String uploadUrl = fileService.upload(file);
		//返回r对象
		return R.ok().message("文件上传成功").data("url", uploadUrl);

	}
}