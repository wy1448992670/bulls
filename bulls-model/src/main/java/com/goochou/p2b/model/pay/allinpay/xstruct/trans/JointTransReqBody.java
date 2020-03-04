package com.goochou.p2b.model.pay.allinpay.xstruct.trans;




/**
 * @Description 联合放贷dto
 * @Author meixf@allinpay.com
 * @Date 2018年10月22日
 **/
public class JointTransReqBody  {
	private Jointrans trxEntity;
	private JointDto joint;
	
	public Jointrans getTrxEntity() {
		return trxEntity;
	}
	public void setTrxEntity(Jointrans trxEntity) {
		this.trxEntity = trxEntity;
	}
	public JointDto getJoint() {
		return joint;
	}
	public void setJoint(JointDto joint) {
		this.joint = joint;
	}

}
