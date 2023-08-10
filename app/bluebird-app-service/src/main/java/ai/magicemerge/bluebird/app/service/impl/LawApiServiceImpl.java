package ai.magicemerge.bluebird.app.service.impl;

import ai.magicemerge.bluebird.app.dal.mapper.SearchMapper;
import ai.magicemerge.bluebird.app.dal.model.Search;
import ai.magicemerge.bluebird.app.integration.LawOpenFeign;
import ai.magicemerge.bluebird.app.integration.dto.LawSearchMessageDto;
import ai.magicemerge.bluebird.app.integration.dto.LawSearchRequestDto;
import ai.magicemerge.bluebird.app.service.LawApiService;
import ai.magicemerge.bluebird.app.service.common.utils.WebContextUtil;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Slf4j
@Service
public class LawApiServiceImpl implements LawApiService {

	@Autowired
	private LawOpenFeign lawOpenFeign;

	@Autowired
	private SearchMapper searchMapper;


	@Override
	public LawSearchMessageDto searchLawGpt(LawSearchRequestDto requestDto) {
		LawSearchMessageDto lawSearchMessageDto = lawOpenFeign.searchLaw(requestDto);
		if (Objects.nonNull(lawSearchMessageDto)) {
			Search search = new Search();
			search.setUserId(String.valueOf(WebContextUtil.getUserSession().getUserId()));
			search.setSearchTextId(lawSearchMessageDto.getId());
			search.setResponse(lawSearchMessageDto.getResponse());
			search.setSearchTime(DateUtil.date());
			searchMapper.insert(search);
			return lawSearchMessageDto;
		}
		return null;
	}







}
