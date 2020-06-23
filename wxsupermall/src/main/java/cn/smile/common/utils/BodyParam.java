package cn.smile.common.utils;

import cn.smile.core.entity.WxAddress;
import cn.smile.core.entity.WxOrderItem;

import java.io.Serializable;
import java.util.List;

/**
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/20
 */
public class BodyParam implements Serializable {
	
	public final static long serialVersionUID = 1L;
	
	
	private List<WxOrderItem> body;
	private WxAddress address;
	private String sign;
	
	public List<WxOrderItem> getBody() {
		return body;
	}
	
	public void setBody(List<WxOrderItem> body) {
		this.body = body;
	}
	
	public WxAddress getAddress() {
		return address;
	}
	
	public void setAddress(WxAddress address) {
		this.address = address;
	}
	
	public String getSign() {
		return sign;
	}
	
	public void setSign(String sign) {
		this.sign = sign;
	}
}
