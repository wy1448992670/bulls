package com.goochou.p2b.hessian.openapi.message;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Request;

public class SendMessageRequest extends Request {

	/**
	 * <p>Discription:[字段功能描述]</p>
	 */
	private static final long serialVersionUID = -7075661958477888709L;
	@FieldMeta(description = "短信渠道")
	private String channel;
	@FieldMeta(description = "手机(多个手机号用英文\",\"分割)", have=true)
	private String phones;
	@FieldMeta(description = "内容", have=true)
	private String content;
	@FieldMeta(description = "是否是营销短信 true：是，false：否")
    private boolean market = false;
	public boolean isMarket() {
        return market;
    }
    public void setMarket(boolean market) {
        this.market = market;
    }
    public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getPhones() {
		return phones;
	}
	public void setPhone(String phones) {
		this.phones = phones;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
