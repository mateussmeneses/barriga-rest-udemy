package br.ce.mmeneses.rest.core;

import io.restassured.http.ContentType;

public interface Constantes {
	String APP_BASE_URL = "http://barrigarest.wcaquino.me/";
	int APP_PORT = 80; // HTTP = 80 HTTPS = 443
	String APP_BASE_PATH = "";

	ContentType APP_CONTENT_TYPE = ContentType.JSON;

	Long MAX_TIMEOUT = 10000L;

}
