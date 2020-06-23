package cn.smile.core.senum;

/**
 *
 * 定义支付方式
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/20
 */
public enum Mode {
	WECHATPAY("微信支付"), PAYBYAIPAY("支付宝支付"),NOT_PAY("未支付");
	
	Mode(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	private String name;
}
