package com.goochou.p2b.hessian.transaction.investment;

import java.util.List;

import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.model.vo.InvestmentOrderListVO;

public class InvestmentOrderListResponse  extends Response{
	private static final long serialVersionUID = 748248541369472002L;
	private List<InvestmentOrderListVO> list;
	private int count;
	public List<InvestmentOrderListVO> getList() {
		return list;
	}
	public void setList(List<InvestmentOrderListVO> list) {
		this.list = list;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
} 
