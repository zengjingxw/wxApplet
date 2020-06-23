package cn.smile.core.service.Imp;

import cn.smile.core.entity.WxUser;
import cn.smile.core.service.TestService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/18
 */

@Service
//@Transactional
public class TestServiceImpl implements TestService {
	@Cacheable(cacheNames = "redisName",key = "#root.methodName")
	@Override
	public List<WxUser> getAllWxUser() {
		List<WxUser> wxuserList = new ArrayList<>();
		wxuserList.add(new WxUser("1","one","a",new Date()));
		wxuserList.add(new WxUser("2","one","b",new Date()));
		wxuserList.add(new WxUser("3","two","c",new Date()));
		wxuserList.add(new WxUser("4","two","d",new Date()));
		wxuserList.add(new WxUser("5","two","e",new Date()));
		wxuserList.add(new WxUser("6","twp","f",new Date()));
		return wxuserList;
	}
}
