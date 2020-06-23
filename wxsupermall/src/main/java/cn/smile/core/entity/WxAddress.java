package cn.smile.core.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户地址
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/20
 */
public class WxAddress implements Serializable {
	public final  static long serialVersionUID = 1L;
	
	private String id;
	private String phone;
	private String consignee;
	private Date date;
	private String regional;
	private String [] editSign;
	private String detailedAddress;
	private Integer defaultAddress;// 0(不默认) ,1(默认)
	
	public String[] getEditSign() {
		return editSign;
	}
	
	public void setEditSign(String[] editSign) {
		this.editSign = editSign;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getConsignee() {
		return consignee;
	}
	
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getRegional() {
		return regional;
	}
	
	public void setRegional(String regional) {
		this.regional = regional;
	}
	
	public String getDetailedAddress() {
		return detailedAddress;
	}
	
	public void setDetailedAddress(String detailedAddress) {
		this.detailedAddress = detailedAddress;
	}
	
	public Integer getDefaultAddress() {
		return defaultAddress;
	}
	
	public void setDefaultAddress(Integer defaultAddress) {
		this.defaultAddress = defaultAddress;
	}
}
