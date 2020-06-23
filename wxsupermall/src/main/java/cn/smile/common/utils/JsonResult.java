package cn.smile.common.utils;

/**
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/18
 */
public class JsonResult {
	
	private Integer code;
	private String message;
	private Object body;
	
	public Integer getCode() {
		return code;
	}
	
	public void setCode(Integer code) {
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Object getBody() {
		return body;
	}
	
	public void setBody(Object body) {
		this.body = body;
	}
	
}
