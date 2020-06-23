package cn.smile.core.entity;

import java.io.Serializable;

/**
 * 订单项
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/20
 */
public class WxOrderItem implements Serializable {
	
	public final  static long serialVersionUID = 1L;
	
	private String id;
	private String imageURL;
	private String title;
	private String desc;
	private Double price;
	private Boolean checked;
	private Integer count;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getImageURL() {
		return imageURL;
	}
	
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Boolean getChecked() {
		return checked;
	}
	
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
	public Integer getCount() {
		return count;
	}
	
	public void setCount(Integer count) {
		this.count = count;
	}
}
