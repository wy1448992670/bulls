package com.goochou.p2b.service;

import java.util.List;
import java.util.Map;
import com.goochou.p2b.model.PrairieArea;


public interface PrairieAreaService {


	Map<Long, List<PrairieArea>> getPrairieIdKPrairieAreaListVMap();

}


