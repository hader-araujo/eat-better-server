package com.eat.better.controller.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/food")
public class FoodRestController {

	private static final Logger log = LogManager.getLogger(FoodRestController.class);

	private static final String URI_USDA_FOOD_LIST = "http://api.nal.usda.gov/ndb/list/?format=json&lt=f&sort=n&offset=1&max=2&api_key=DEMO_KEY";

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> getFood(@RequestParam("page") int page, @RequestParam("size") int size) {

		log.debug(String.format("getFood::page %d, size %d", page, size));

		// String url = String.format(URI_USDA_FOOD_LIST, page, size);//TODO
		// page
		// log.debug(String.format("getFood::URL used %s", url));

		HttpHeaders headers = new HttpHeaders();
//		headers.set("Accept", "application/json");
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);

		return restTemplate.exchange(URI_USDA_FOOD_LIST, HttpMethod.GET, httpEntity, String.class);

	}
}
