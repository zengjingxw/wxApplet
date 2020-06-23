package cn.smile.core.controller;

import cn.smile.core.entity.WxUser;
import cn.smile.core.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/7
 */
@RestController
public class IndexController {
	
	@Autowired
	TestService testService;
	
	
	@GetMapping("/index.wx")
	public String test() {
		return "测试";
	}
	
	@GetMapping("/getAllUser.wx")
	public List<WxUser> getAllWxUser() {
		return testService.getAllWxUser();
	}
}
