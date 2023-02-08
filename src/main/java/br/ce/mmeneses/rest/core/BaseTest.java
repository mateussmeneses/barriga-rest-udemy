package br.ce.mmeneses.rest.core;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.BeforeClass;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;


public class BaseTest implements Constantes {
	
	@BeforeClass
	public static void setup() {
	//	System.out.println("Before base");
		baseURI = APP_BASE_URL;
		port = APP_PORT;
		basePath = APP_BASE_PATH;
		
		RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
		reqBuilder.setContentType(APP_CONTENT_TYPE);
		requestSpecification = reqBuilder.build();
		
		ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
		resBuilder.expectResponseTime(lessThan(MAX_TIMEOUT));
		responseSpecification = resBuilder.build();
		
		enableLoggingOfRequestAndResponseIfValidationFails();
		
		
	}


}
