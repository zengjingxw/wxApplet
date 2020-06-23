package cn.smile.core.test;

import cn.smile.common.utils.HttpsRequestUtil;
import cn.smile.core.config.RootApplicationConfig;
import cn.smile.core.config.WebApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootApplicationConfig.class, WebApplicationConfig.class})
public class WxTest {
	
	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	
	@Test
	public void test() {
		ValueOperations<String, String> ops = redisTemplate.opsForValue();
//		ops.set("测试","测试");
		String s = ops.get("测试");
		System.out.println(s);
	}

}
