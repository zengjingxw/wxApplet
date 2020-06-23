package cn.smile.core.service;

import cn.smile.core.entity.WxUser;

import java.util.List;

/**
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/18
 */
public interface TestService {
	
	default public List<WxUser> getAllWxUser() { return null; }
}
