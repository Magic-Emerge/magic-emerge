package ai.magicemerge.bluebird.app.integration;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
		name = "emergeflow-open-api",
		url = "${emergeflow.openapi.url}",
		path = "/v1")
public interface EmergeFlowOpenFeign {
	


}
