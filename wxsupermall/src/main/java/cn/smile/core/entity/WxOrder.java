package cn.smile.core.entity;

import cn.smile.core.senum.Mode;
import cn.smile.core.senum.PayStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * 订单对象
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/20
 */
public class WxOrder implements Serializable {
	public final static long serialVersionUID = 1L;
	
	private String id;
	private Double totalPrice;
	private WxUser wxUser;
	private Double freight;
	private List<WxOrderItem> wxOrderItems = new ArrayList<>();
	private Date date;
	private Mode mode;
	private PayStatus payStatus;
	private WxAddress wxAddress;
	private Integer totalCount;
	
	private String showStatus;
	
	public String getShowStatus() {
		return showStatus;
	}
	
	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Integer getTotalCount() {
		return totalCount;
	}
	
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	
	public Double getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public WxUser getWxUser() {
		return wxUser;
	}
	
	public void setWxUser(WxUser wxUser) {
		this.wxUser = wxUser;
	}
	
	public Double getFreight() {
		return freight;
	}
	
	public void setFreight(Double freight) {
		this.freight = freight;
	}
	
	public List<WxOrderItem> getWxOrderItems() {
		return wxOrderItems;
	}
	
	public void setWxOrderItems(List<WxOrderItem> wxOrderItems) {
		this.wxOrderItems = wxOrderItems;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Mode getMode() {
		return mode;
	}
	
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	
	public PayStatus getPayStatus() {
		return payStatus;
	}
	
	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
		this.setShowStatus(this.payStatus.getName());
	}
	
	public WxAddress getWxAddress() {
		return wxAddress;
	}
	
	public void setWxAddress(WxAddress wxAddress) {
		this.wxAddress = wxAddress;
	}
	
	//================proxy=======================
	
	@JsonIgnore
	public List<WxOrderItem> getWxOrderItemsProxy() {
		return wxOrderItems;
	}
	@JsonIgnore
	public void setWxOrderItemsProxy(WxOrderItem wxOrderItemsProxy) {
		this.wxOrderItems.add(wxOrderItemsProxy);
	}
	
}
