package com.eat.better.rest.controller;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/food")
public class FoodRestController {

	private static final Logger log = LogManager.getLogger(FoodRestController.class);

	private static final String URI_USDA_FOOD_LIST = "https://api.nal.usda.gov/ndb/list/?format=json&lt=f&sort=n&offset={offset}&max={max}&api_key=iDRxNvxZLHC6oSPeu2nr4C6T3RAe4rzikMjakXnC";

	private RestTemplate restTemplate;
	private HttpEntity<?> httpEntity;

	public FoodRestController() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		restTemplate = new RestTemplate();
		httpEntity = new HttpEntity<>(headers);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> getFood(@RequestParam("page") Long page, @RequestParam("max") Long max) {

		final Long offset = (page - 1) * max;
		log.debug(String.format("getFood::page %d - offset %d, max %d", page, offset, max));

		ResponseEntity<String> exchange;
		try {

			exchange = restTemplate.exchange(URI_USDA_FOOD_LIST, HttpMethod.GET, httpEntity, String.class, offset, max);

			String body = exchange.getBody();

			// log.debug(String.format("getFood::body = %s", body));

			return new ResponseEntity<String>(body, HttpStatus.OK);
		} catch (RestClientException e) { // TODO dealing with different errors status
			log.error("getFood::Error getting the food list", e);
			return new ResponseEntity<String>(HttpStatus.TOO_MANY_REQUESTS);
		}

	}

	static {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {

				public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLContext.setDefault(ctx);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
