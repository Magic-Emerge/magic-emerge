package ai.magicemerge.bluebird.app.service;

import ai.magicemerge.bluebird.app.integration.dto.LawSearchMessageDto;
import ai.magicemerge.bluebird.app.integration.dto.LawSearchRequestDto;

public interface LawApiService {


	LawSearchMessageDto searchLawGpt(LawSearchRequestDto requestDto);



}
