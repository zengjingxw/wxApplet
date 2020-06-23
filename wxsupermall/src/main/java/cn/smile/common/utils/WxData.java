package cn.smile.common.utils;

import java.io.Serializable;

/**
 * @author smiletofotget
 * @creationTime 2020-05-2020/5/21
 */
public class WxData implements Serializable {
	public final static  long serialVersionUID = 1L;
	private String openid;
	private String session_key;
	private String unionid;
	private Integer errcode;
	private String errmsg;
	
	public String getOpenid() {
		return openid;
	}
	
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	public String getSession_key() {
		return session_key;
	}
	
	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}
	
	public String getUnionid() {
		return unionid;
	}
	
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	
	public Integer getErrcode() {
		return errcode;
	}
	
	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}
	
	public String getErrmsg() {
		return errmsg;
	}
	
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
}
