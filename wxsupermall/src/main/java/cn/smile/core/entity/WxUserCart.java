package cn.smile.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/7
 */
public class WxUserCart implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private double totalPrice;
	
	private List<WxUserCartItem> lists = new ArrayList<>();
	
	private Integer totalCount;
	
	public double getTotalPrice() {
		return totalPrice;
	}
	

	public void setTotalPrice() {
		double price = 0;
		for (WxUserCartItem list : this.lists) {
			if (list.getChecked()) {
				price += list.getPrice();
			}
		}
		this.totalPrice = price;
	}
	
	public List<WxUserCartItem> getLists() {
		return lists;
	}
	

	public void setLists(List<WxUserCartItem> lists) {
		this.lists = lists;
	}
	
	
	public Integer getCount() {
		return totalCount;
	}
	
	
	public void setCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	
	
	@Override
	public String toString() {
		return "WxUserCart{" +
				"totalPrice=" + totalPrice +
				", lists=" + lists +
				", totalCount=" + totalCount +
				'}';
	}
	
//==========================/proxy====================================================
	
	//保存购物项
	public void setListProxy(List<WxUserCartItem> lists) {
		for (WxUserCartItem list : lists) {
			if (this.lists.contains(list)) {
				for (WxUserCartItem wxUserCartItem : this.lists) {
					if (wxUserCartItem.getId().equals(list.getId())) {
						wxUserCartItem.setChecked(list.getChecked());
						wxUserCartItem.setCount(list.getCount());
					}
				}
			}
			else {
				this.lists.add(list);
			}
		}
		setCountProxy();
		setTotalPriceProxy();
	}
	
	//统计购物件数
	public void setCountProxy() {
		Integer count = 0;
		for (WxUserCartItem list : this.lists) {
			count += list.getCount();
		}
		this.totalCount = count;
	}
	
	//统计总价格
	public void setTotalPriceProxy() {
		double price = 0;
		for (WxUserCartItem list : this.lists) {
			if (list.getChecked()) {
				price += list.getPrice();
			}
		}
		this.totalPrice = price;
	}
	
	//删除
	
	@JsonIgnore
	public void  deleteCartItem(WxUserCartItem wxUserCartItem) {
		this.lists.remove(wxUserCartItem);
	}
	
//======================/proxy==============================================

}
