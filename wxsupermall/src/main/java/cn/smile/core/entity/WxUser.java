package cn.smile.core.entity;

import cn.smile.common.utils.WxData;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.PipedReader;
import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * (WxUser)实体类
 *
 * @author smile
 * @since 2020-06-07 11:12:26
 */
public class WxUser implements Serializable {
    private static final long serialVersionUID = -53925467250874860L;
    
    private String wxUId;
    
    private String wxUName;
    
    private String wxUOid;
    
    private WxData wxData;
    
    private Date wxUDate;
    
    private WxUserCart wxUserCart;
    
    private List<WxOrder> wxOrders = new ArrayList<>();
    
    //用户订单
    public List<WxOrder> getWxOrders() {
        return wxOrders;
    }
    
    public void setWxOrders(List<WxOrder> wxOrders) {
        this.wxOrders = wxOrders;
    }
    
    //用户地址
    private List<WxAddress> wxAddresses = new ArrayList<>();
    
    public List<WxAddress> getWxAddresses() {
        return wxAddresses;
    }
    
    public void setWxAddresses(List<WxAddress> wxAddresses) {
        this.wxAddresses = wxAddresses;
    }
    
    public WxUserCart getWxUserCart() {
        return wxUserCart;
    }
    
    public void setWxUserCart(WxUserCart wxUserCart) {
        this.wxUserCart = wxUserCart;
    }
    
    public WxData getWxData() {
        return wxData;
    }
    
    public void setWxData(WxData wxData) {
        this.wxData = wxData;
    }
    
    public String getWxUId() {
        return wxUId;
    }

    public void setWxUId(String wxUId) {
        this.wxUId = wxUId;
    }

    public String getWxUName() {
        return wxUName;
    }

    public void setWxUName(String wxUName) {
        this.wxUName = wxUName;
    }

    public String getWxUOid() {
        return wxUOid;
    }

    public void setWxUOid(String wxUOid) {
        this.wxUOid = wxUOid;
    }

    public Date getWxUDate() {
        return wxUDate;
    }

    public void setWxUDate(Date wxUDate) {
        this.wxUDate = wxUDate;
    }
    
    @Override
    public String toString() {
        return "WxUser{" +
                "wxUId='" + wxUId + '\'' +
                ", wxUName='" + wxUName + '\'' +
                ", wxUOid='" + wxUOid + '\'' +
                ", wxData=" + wxData +
                ", wxUDate=" + wxUDate +
                ", wxUserCart=" + wxUserCart +
                ", wxOrders=" + wxOrders +
                ", wxAddresses=" + wxAddresses +
                '}';
    }
    
    public WxUser() {
    
    }
    
    public WxUser(String  wxUId, String wxUName, String wxUOid, Date wxUDate) {
        this.wxUId = wxUId;
        this.wxUName = wxUName;
        this.wxUOid = wxUOid;
        this.wxUDate = wxUDate;
    }
    
    //==================proxy======================
    @JsonIgnore
    public List<WxAddress> getProxyWxAddress() {
        return wxAddresses;
    }
    
    @JsonIgnore
    public void setProxyWxAddress(WxAddress wxAddressesProxy) {
        if (wxAddressesProxy.getDefaultAddress() == 1) {
            for (WxAddress wxAddress : wxAddresses) {
                if (wxAddress.getDefaultAddress() == 1) {
                    wxAddress.setDefaultAddress(0);
                    break;
                }
            }
        }
        this.wxAddresses.add(wxAddressesProxy);
    }
    
    @JsonIgnore
    public void deleteAddress(WxAddress wxAddress) {
        this.wxAddresses.remove(wxAddress);
    }
    
    @JsonIgnore
    public List<WxOrder> getWxOrdersProxy() {
        return wxOrders;
    }
    
    @JsonIgnore
    public void setWxOrdersProxy(WxOrder wxOrdersProxy) {
        this.wxOrders.add(wxOrdersProxy);
    }
    
    
    
}