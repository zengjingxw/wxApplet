package cn.smile.core.controller;

import cn.smile.common.utils.CheckUser;
import cn.smile.common.utils.JsonResult;
import cn.smile.core.entity.WxAddress;
import cn.smile.core.entity.WxUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/20
 */
@RestController
public class WxAddressController {
	
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
	 * 显示地址列表
	 * @param sessionId 1
	 * @param token     2
	 * @return cn.smile.common.utils.JsonResult
	 * @author smileforget
	 * @date 2020/6/20
	 */
	@GetMapping("/addressList.wx")
	public JsonResult address(String sessionId, String token) {
		
		String cToken = stringRedisTemplate.opsForValue().get(sessionId);
		
		Assert.notNull(cToken, "失效");
		
		if (!cToken.equals(token)) {
			throw new RuntimeException("token不正确");
		}
		
		String uOpenId = stringRedisTemplate.opsForValue().get("uid");
		
		Assert.notNull(uOpenId, "没有用户");
		
		JsonResult jsonResult = new JsonResult();
		
		WxUser wxUser = (WxUser) redisTemplateTwo.opsForValue().get(uOpenId);
		
		List<WxAddress> wxAddresses = wxUser.getWxAddresses();
		
		if (!StringUtils.isEmpty(wxAddresses)) {
			for (WxAddress wxAddress : wxAddresses) {
				String phone = wxAddress.getPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
				wxAddress.setPhone(phone);
			}
			jsonResult.setBody(wxAddresses);
		}
		jsonResult.setCode(1);
		return jsonResult;
	}
	
	/**
	 * 添加地址
	 * @param sessionId 1
	 * @param token     2
	 * @param body      3
	 * @return cn.smile.common.utils.JsonResult
	 * @author smileforget
	 * @date 2020/6/20
	 */
	@PostMapping("/saveAddress.wx")
	public JsonResult saveAddress(@RequestHeader("sessionId") String sessionId, @RequestHeader("token") String token,
								  @RequestBody String body) throws JsonProcessingException {
		//用户登录验证
		String uOpenId = CheckUser.checkUser(stringRedisTemplate, sessionId, token);
		
		//序列
		ObjectMapper objectMapper = new ObjectMapper();
		
		WxAddress wxAddress = objectMapper.readValue(body, WxAddress.class);
		wxAddress.setDate(new Date());
		WxUser wxUser = (WxUser) redisTemplateTwo.opsForValue().get(uOpenId);
		//判断是编辑还是添加
		if (!wxAddress.getId().equals("sign")) {
			List<WxAddress> wxAddresses = wxUser.getWxAddresses();
			for (WxAddress address : wxAddresses) {
				if (wxAddress.getDefaultAddress() == 1) {
					address.setDefaultAddress(0);
				}
				if (address.getId().equals(wxAddress.getId())) {
					address.setPhone(wxAddress.getPhone());
					address.setDefaultAddress(wxAddress.getDefaultAddress());
					address.setDetailedAddress(wxAddress.getDetailedAddress());
					address.setConsignee(wxAddress.getConsignee());
					address.setEditSign(wxAddress.getEditSign());
					address.setRegional(wxAddress.getRegional());
					address.setDate(new Date());
				}
			}
			wxUser.setWxAddresses(wxAddresses);
		} else {
			wxAddress.setId(UUID.randomUUID().toString().substring(0, 8).replaceAll("-", ""));
			wxUser.setProxyWxAddress(wxAddress);
		}
		
		redisTemplateTwo.opsForValue().set(uOpenId, wxUser);
		
		JsonResult jsonResult = new JsonResult();
		jsonResult.setCode(1);
		String phone = wxAddress.getPhone();
		phone = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
		wxAddress.setPhone(phone);
		jsonResult.setBody(wxAddress);
		
		return jsonResult;
	}
	
	/**
	 * 删除地址
	 * @param addressId 1
	 * @param sessionId 2
	 * @param token     3
	 * @return cn.smile.common.utils.JsonResult
	 * @author smileforget
	 * @date 2020/6/20
	 */
	@GetMapping("/deleteAddress.wx")
	public JsonResult deleteAddress(String addressId,@RequestHeader("sessionId") String sessionId, @RequestHeader("token") String token) {
		String uOpenId = CheckUser.checkUser(stringRedisTemplate, sessionId, token);
		
		WxUser wxUser = (WxUser) redisTemplateTwo.opsForValue().get(uOpenId);
		
		List<WxAddress> wxAddresses = wxUser.getWxAddresses();
		for (WxAddress wxAddress : wxAddresses) {
			if (wxAddress.getId().equals(addressId)) {
				wxUser.deleteAddress(wxAddress);
				break;
			}
		}
		redisTemplateTwo.opsForValue().set(uOpenId, wxUser);
		JsonResult jsonResult = new JsonResult();
		jsonResult.setCode(1);
		jsonResult.setMessage("Ok");
		return jsonResult;
	}
	
	//异常处理
	@ExceptionHandler(value = {Exception.class})
	public JsonResult handleException(Exception e) {
		JsonResult jsonResult = new JsonResult();
		if (e instanceof IllegalArgumentException) {
			jsonResult.setCode(-1);
			jsonResult.setMessage(e.getMessage());
		} else  {
			jsonResult.setCode(-1);
			jsonResult.setMessage(e.getMessage());
		}
		
		return jsonResult;
	}
}
