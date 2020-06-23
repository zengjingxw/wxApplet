package cn.smile.core.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * 购物项
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/7
 */
public class WxUserCartItem  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String imageURL;
	private String title;
	private String desc;
	private Double price;
	private Boolean checked;
	private Integer count;
	
	public Integer getCount() {
		return count;
	}
	
	public void setCount(Integer count) {
		this.count = count;
	}
	
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
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		WxUserCartItem that = (WxUserCartItem) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(imageURL, that.imageURL) &&
				Objects.equals(title, that.title) &&
				Objects.equals(desc, that.desc) &&
				Objects.equals(price, that.price) ;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, imageURL, title, desc, price, checked, count);
	}
	
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
}
