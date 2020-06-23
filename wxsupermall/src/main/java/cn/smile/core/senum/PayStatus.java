package cn.smile.core.senum;

/**
 * 定义支付状态
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/20
 */
public enum PayStatus {
	HAVE_PAID("已支付"),NO_PAYMENT("代支付"),DROP_SHIPPING("代发货"),FULIFILL("已完成"),
	REMAIN_TO_BE_EVALUATED("待评价"),HAVE_EVALUATION("已评价"),OFF("已取消");
	
	PayStatus(String name) {
		this.name = name;
	}
	
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
