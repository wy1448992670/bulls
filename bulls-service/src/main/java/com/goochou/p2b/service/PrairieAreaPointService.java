package com.goochou.p2b.service;

import java.util.List;

import com.goochou.p2b.model.PrairieAreaPoint;

public interface PrairieAreaPointService {

	List<PrairieAreaPoint> getByPrairieAreaId(Long prairieAreaId);

}


