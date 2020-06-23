package cn.smile.common.utils;

import cn.smile.core.entity.WxUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;

/**
 * 验证
 *
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/20
 */
public class CheckUser {
	
	public static String checkUser(StringRedisTemplate stringRedisTemplate, String sessionId,
								   String token) {
		String cToken = stringRedisTemplate.opsForValue().get(sessionId);
		Assert.notNull(cToken, "失效了");
		
		if (!cToken.equals(token)) {
			throw new RuntimeException("token不正确");
		}
		
		String uOpenId = stringRedisTemplate.opsForValue().get("uid");
		Assert.notNull(uOpenId, "没有这个用户");
		return uOpenId;
	}
}
