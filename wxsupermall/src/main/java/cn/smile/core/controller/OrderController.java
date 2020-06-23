package cn.smile.core.controller;

import cn.smile.common.utils.BodyParam;
import cn.smile.common.utils.CheckUser;
import cn.smile.common.utils.JsonResult;
import cn.smile.core.entity.*;
import cn.smile.core.senum.Mode;
import cn.smile.core.senum.PayStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/20
 */

@RestController
public class OrderController {
	
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
	 * 跳转订单页面
	 *
	 * @param sessionId 1
	 * @param token     2
	 * @return cn.smile.common.utils.JsonResult
	 * @author smileforget
	 * @date 2020/6/20
	 */
	@GetMapping("/toOrder.wx")
	public JsonResult toOrder(String sessionId, String token) {
		
		String openId = stringRedisTemplate.opsForValue().get("uid");
		Assert.notNull(openId, "无效用户");
		
		WxUser wxUser = (WxUser) redisTemplateTwo.opsForValue().get(openId);
		Assert.notNull(wxUser, "无效用户");
		
		WxUserCart wxUserCart = wxUser.getWxUserCart();
		List<WxUserCartItem> lists = wxUserCart.getLists();
		
		List<WxUserCartItem> returnOrderItems = new ArrayList<>();
		
		for (WxUserCartItem list : lists) {
			if (list.getChecked()) {
				returnOrderItems.add(list);
			}
		}
		
		WxAddress returnAddress = null;
		List<WxAddress> wxAddresses = wxUser.getWxAddresses();
		if (!StringUtils.isEmpty(wxAddresses) && wxAddresses.size() != 0) {
			for (WxAddress wxAddress : wxAddresses) {
				if (wxAddress.getDefaultAddress() == 1) {
					returnAddress = wxAddress;
				}
			}
			if (StringUtils.isEmpty(returnAddress)) {
				for (WxAddress wxAddress : wxAddresses) {
					returnAddress = wxAddress;
					break;
				}
			}
			String phone = returnAddress.getPhone();
			phone = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
			returnAddress.setPhone(phone);
		}
		if (returnAddress == null) {
			returnAddress = new WxAddress();
		}
		
		JsonResult jsonResult = new JsonResult();
		
		Map<String, Object> mps = new HashMap<>();
		mps.put("prodList", returnOrderItems);
		mps.put("address", returnAddress);
		jsonResult.setCode(1);
		jsonResult.setBody(mps);
		
		return jsonResult;
	}
	
	/**
	 * 提交订单进行处理
	 *
	 * @param body      1
	 * @param address   2
	 * @param sign      3
	 * @param sessionId 4
	 * @param token     5
	 * @return cn.smile.common.utils.JsonResult
	 * @author smileforget
	 * @date 2020/6/21
	 */
	@PostMapping("/orderHandle.wx")
	public JsonResult orderHandle(
			@RequestBody String body,
			String address,
			String sign,
			@RequestHeader("sessionId") String sessionId,
			@RequestHeader("token") String token) throws JsonProcessingException {
		
		JsonResult jsonResult = new JsonResult();
		String uPenId = CheckUser.checkUser(stringRedisTemplate, sessionId, token);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		BodyParam bodyParam = objectMapper.readValue(body, BodyParam.class);
		
		//获取用户对象
		WxUser wxUser = (WxUser) redisTemplateTwo.opsForValue().get(uPenId);
		
		//获取到地址
		WxAddress wxAddress = bodyParam.getAddress();
		
		//构建订单对象
		WxOrder wxOrder = new WxOrder();
		wxOrder.setId(UUID.randomUUID().toString().substring(0, 8).replaceAll("-", ""));
		wxOrder.setFreight(0d);
		wxOrder.setDate(new Date());
		wxOrder.setWxAddress(wxAddress);
		wxOrder.setPayStatus(PayStatus.NO_PAYMENT);
		wxOrder.setMode(Mode.NOT_PAY);
		
		//反序列化数据
		if (bodyParam.getSign().equals("one")) {
			List<WxOrderItem> body1 = bodyParam.getBody();
			WxOrderItem wxOrderItem = body1.get(0);
			wxOrder.setTotalPrice(wxOrderItem.getPrice());
			wxOrder.setTotalCount(wxOrderItem.getCount());
			wxOrder.setWxOrderItemsProxy(wxOrderItem);
			
			jsonResult.setMessage("one");
		} else if (bodyParam.getSign().equals("much")) {
			List<WxOrderItem> wxOrderItems = (List<WxOrderItem>) bodyParam.getBody();
			Integer count = 0;
			Double totalPrice = 0D;
			
			for (WxOrderItem wxOrderItem : wxOrderItems) {
				count += wxOrderItem.getCount();
				totalPrice += wxOrderItem.getCount() * wxOrderItem.getPrice();
				wxOrder.setWxOrderItemsProxy(wxOrderItem);
			}
			wxOrder.setTotalPrice(totalPrice);
			wxOrder.setTotalCount(count);
			
			//清除
			WxUserCart wxUserCart = wxUser.getWxUserCart();
			List<WxUserCartItem> lists = wxUserCart.getLists();
			List<WxUserCartItem> signsWxUserCartItem = new ArrayList<>();
			for (WxUserCartItem list : lists) {
				if (list.getChecked()) {
					signsWxUserCartItem.add(list);
				}
			}
			for (WxUserCartItem wxUserCartItem : signsWxUserCartItem) {
				wxUserCart.deleteCartItem(wxUserCartItem);
			}
			
			jsonResult.setMessage("much");
			wxUser.setWxUserCart(wxUserCart);
		}
		
		//添加用户订单
		wxUser.setWxOrdersProxy(wxOrder);
		
		//保存信息
		redisTemplateTwo.opsForValue().set(uPenId, wxUser);
		
		jsonResult.setCode(1);
		jsonResult.setBody(wxOrder);
		
		return jsonResult;
	}
	
	/**
	 * 返回用户的订单信息
	 *
	 * @param sessionId 1
	 * @param token     2
	 * @return cn.smile.common.utils.JsonResult
	 * @author smileforget
	 * @date 2020/6/21
	 */
	@GetMapping("/returnMyOrderData.wx")
	public JsonResult returnMyOrderData(@RequestHeader("sessionId") String sessionId, @RequestHeader("token") String token) {
		String uOpenId = CheckUser.checkUser(stringRedisTemplate, sessionId, token);
		
		WxUser wxUser = (WxUser) redisTemplateTwo.opsForValue().get(uOpenId);
		
		//获取到订单对象
		List<WxOrder> wxOrders = wxUser.getWxOrders();
		
		if (wxOrders.size() == 0) {
			throw new RuntimeException("wxOrder没有");
		}
		
		//构建返回数据Body
		
		Map<String, Object> mps = new HashMap<>();
		
		//全部
		List<WxOrder> all = new ArrayList<>();
		//代付款
		List<WxOrder> ofPay = new ArrayList<>();
		//待收货
		List<WxOrder> wait = new ArrayList<>();
		//已完成
		List<WxOrder> off = new ArrayList<>();
		//已取消
		List<WxOrder> motion = new ArrayList<>();
		
		
		for (WxOrder wxOrder : wxOrders) {
			all.add(wxOrder);
			
			if (wxOrder.getPayStatus().getName().equals("代支付")) {
				ofPay.add(wxOrder);
			}
			
			if (wxOrder.getPayStatus().getName().equals("代发货")) {
				wait.add(wxOrder);
			}
			
			if (wxOrder.getPayStatus().getName().equals("已完成")) {
				off.add(wxOrder);
			}
			
			if (wxOrder.getPayStatus().getName().equals("已取消")) {
				motion.add(wxOrder);
			}
		}
		
		mps.put("all", all);
		mps.put("ofPay", ofPay);
		mps.put("wait", wait);
		mps.put("off", off);
		mps.put("motion", motion);
		
		JsonResult jsonResult = new JsonResult();
		jsonResult.setCode(1);
		jsonResult.setMessage("OK");
		jsonResult.setBody(mps);
		
		return jsonResult;
	}
	
	/**
	 * 订单状态的更新
	 * @param sessionId 1
	 * @param token     2
	 * @param orderId   3
	 * @param sign      4
	 * @return cn.smile.common.utils.JsonResult
	 * @author smileforget
	 * @date 2020/6/21
	 */
	@GetMapping("/updOrder.wx")
	public JsonResult updOrder(@RequestHeader("sessionId") String sessionId, @RequestHeader("token") String token, String orderId,
							   String sign) {
		String uOpenId = CheckUser.checkUser(stringRedisTemplate, sessionId, token);
		
		WxUser wxUser = (WxUser) redisTemplateTwo.opsForValue().get(uOpenId);
		
		List<WxOrder> wxOrders = wxUser.getWxOrders();
		
		if (sign.equals("fk")) {
			for (WxOrder wxOrder : wxOrders) {
				if (wxOrder.getId().equals(orderId)) {
					wxOrder.setPayStatus(PayStatus.DROP_SHIPPING);
					break;
				}
			}
		}
		
		wxUser.setWxOrders(wxOrders);
		
		redisTemplateTwo.opsForValue().set(uOpenId, wxUser);
		
		JsonResult jsonResult = new JsonResult();
		jsonResult.setCode(1);
		jsonResult.setMessage("OK");
		return jsonResult;
	}
	
	//异常处理
	@ExceptionHandler(value = {Exception.class})
	public JsonResult exceptionHandle(Exception e) {
		JsonResult jsonResult = new JsonResult();
		e.printStackTrace();
		if (e instanceof IllegalArgumentException) {
			jsonResult.setCode(-1);
			jsonResult.setMessage(e.getMessage());
		} else if (e instanceof RuntimeException) {
			jsonResult.setCode(0);
			jsonResult.setMessage("OrderObjectNull");
		}
		return jsonResult;
	}
}
