package com.goochou.p2b.hessian.transaction.goods;

import java.util.List;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.model.goods.GoodsOrder;

public class GoodsOrderListResponse extends Response{
	private static final long serialVersionUID = 1L;
	
	@FieldMeta(description = "商品订单列表", have = true)
	private List<GoodsOrder> list;
	@FieldMeta(description = "", have = true)
	private int count;
	
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<GoodsOrder> getList() {
		return list;
	}
	public void setList(List<GoodsOrder> list) {
		this.list = list;
	}
	
	 
}
