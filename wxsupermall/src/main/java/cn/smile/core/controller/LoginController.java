package cn.smile.core.controller;

import cn.smile.common.utils.HttpsRequestUtil;
import cn.smile.common.utils.JsonResult;
import cn.smile.common.utils.WxData;
import cn.smile.core.entity.WxUser;
import cn.smile.core.entity.WxUserCart;
import cn.smile.core.entity.WxUserCartItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 微信登录
 *
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/18
 */
@RestController
@Slf4j
public class LoginController {
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	@Qualifier("stringRedisTemplateTwo")
	private StringRedisTemplate stringRedisTemplateTow;
	
	@Autowired
	@Qualifier("redisTemplate")
	private RedisTemplate redisTemplate;
	@Autowired
	@Qualifier("redisTemplateDataTwo")
	private RedisTemplate redisTemplateTwo;
	
	
	/**
	 * wx用户登录
	 *
	 * @param code 1
	 * @return org.springframework.http.ResponseEntity<java.util.Map < java.lang.String, java.lang.Object>>
	 * @author smileforget
	 * @date 2020/6/18
	 */
	@GetMapping("/login.wx")
	public ResponseEntity<Map<String, Object>> login(String code) throws Exception {
		Map<String, Object> maps = new HashMap<>();
		ObjectMapper objectMapper = new ObjectMapper();
		HttpStatus httpStatus = HttpStatus.NOT_FOUND;
		
		String wxId = "wxId";
		String wxSecretKey = "wxSecretKey";
		String requestUrl = " https://api.weixin.qq.com/sns/jscode2session?appid=" + wxId + "&secret=" + wxSecretKey + "&js_code=" + code +
				"&grant_type" +
				"=authorization_code";
		String wxData = HttpsRequestUtil.HttpsRequest(requestUrl, "GET", null);
		
		/*序列化数据*/
		WxData jsonToObject = objectMapper.readValue(wxData, WxData.class);
		
		if (jsonToObject.getErrcode() == null || jsonToObject.getErrcode() == 0) {
			
			httpStatus = HttpStatus.OK;
			String token = UUID.randomUUID().toString().substring(18);
			String sessionId = UUID.randomUUID().toString().substring(10).replaceAll("-", "");
			
			WxUser wxUser;
			
			//判断用户第一次登录
			Object openid = redisTemplateTwo.opsForValue().get(jsonToObject.getOpenid());
			if (StringUtils.isEmpty(openid)) {
				wxUser = new WxUser();
				wxUser.setWxUId(UUID.randomUUID().toString().substring(5).replaceAll("-", ""));
				wxUser.setWxUDate(new Date());
				wxUser.setWxData(jsonToObject);
				wxUser.setWxUName(UUID.randomUUID().toString().substring(6).replaceAll("-", ""));
				redisTemplateTwo.opsForValue().set(jsonToObject.getOpenid(), wxUser);
			} else {
				wxUser = (WxUser) openid;
			}
			stringRedisTemplate.opsForValue().set("uid", wxUser.getWxData().getOpenid(), 2, TimeUnit.HOURS);
			
			maps.put("token", token);
			maps.put("code", 200);
			maps.put("sessionId", sessionId);
			//保存token
			stringRedisTemplate.opsForValue().set(sessionId, token, 2, TimeUnit.HOURS);
		}
		
		return new ResponseEntity<Map<String, Object>>(maps, httpStatus);
	}
	
	/**
	 * wx的token验证
	 *
	 * @param token     1
	 * @param sessionId 2
	 * @return java.lang.String
	 * @author smileforget
	 * @date 2020/6/18
	 */
	@GetMapping("/checkUserToken.wx")
	public String checkUserToken(String token, String sessionId) {
		
		String sId = stringRedisTemplate.opsForValue().get(sessionId);
		
		if (StringUtils.isEmpty(sId)) {
			return "-1";
		}
		
		if (sId.equals(token)) {
			return "1";
		} else {
			return "40001";
		}
		
	}
	
	/**
	 * 登录有同步购物车
	 *
	 * @param body  1
	 * @param token 2
	 * @return cn.smile.common.utils.JsonResult
	 * @author smileforget
	 * @date 2020/6/19
	 */
	@PostMapping(value = "/syncCart.wx", headers = {"token"})
	public JsonResult syncCart(@RequestBody String body, @RequestHeader(name = "token") String token,
							   @RequestHeader(name = "sessionId") String sessionId) throws JsonProcessingException {
		
		JsonResult jsonResult = new JsonResult();
		WxUser wxUser;
		
		//检查token是否失效
		
		String ck = stringRedisTemplate.opsForValue().get(sessionId);
		
		if (StringUtils.isEmpty(ck)) {
			jsonResult.setMessage("token失效了");
			jsonResult.setCode(-1);
			return jsonResult;
		}
		
		if (!token.equals(ck)) {
			jsonResult.setMessage("token不正确");
			jsonResult.setCode(-1);
			return jsonResult;
		}
		
		//获取用户在缓存用的标识
		String openId = stringRedisTemplate.opsForValue().get("uid");
		
		//如果存在uid获取到用户的对应的信息
		
		if (!StringUtils.isEmpty(openId)) {
			
			wxUser = (WxUser) redisTemplateTwo.opsForValue().get(openId);
			
		} else {
			jsonResult.setMessage("标识失效");
			jsonResult.setCode(-1);
			return jsonResult;
		}
		
		//反序列化本地保存的购物车
		ObjectMapper objectMapper = new ObjectMapper();
		if (!StringUtils.isEmpty(body) && !body.equals("{}")) {
			WxUserCartItem[] wxUserCartItems = objectMapper.readValue(body, WxUserCartItem[].class);
			log.debug(body);
			log.debug("前端传来的数据",wxUserCartItems.toString());
			WxUserCart haveCart = wxUser.getWxUserCart() == null ? new WxUserCart() : wxUser.getWxUserCart();
			haveCart.setListProxy(Arrays.asList(wxUserCartItems));
			wxUser.setWxUserCart(haveCart);
			log.debug("wxUser",wxUser);
			//合并购物车
			redisTemplateTwo.opsForValue().set(openId, wxUser);
		}
		
		jsonResult.setCode(1);
		jsonResult.setMessage("ok");
		jsonResult.setBody(wxUser.getWxUserCart());
		return jsonResult;
	}
	
	
}
