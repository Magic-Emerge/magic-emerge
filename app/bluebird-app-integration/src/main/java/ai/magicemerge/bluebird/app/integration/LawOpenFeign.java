package ai.magicemerge.bluebird.app.integration;


import ai.magicemerge.bluebird.app.integration.dto.LawSearchMessageDto;
import ai.magicemerge.bluebird.app.integration.dto.LawSearchRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
		name = "law-open-api",
		url = "${law.openapi.url}",
		path = "/api")
public interface LawOpenFeign {


	@PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	LawSearchMessageDto searchLaw(@RequestBody LawSearchRequestDto requestDto);




}
