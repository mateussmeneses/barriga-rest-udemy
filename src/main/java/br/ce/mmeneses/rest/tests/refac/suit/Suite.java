package br.ce.mmeneses.rest.tests.refac.suit;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import br.ce.mmeneses.rest.core.BaseTest;
import br.ce.mmeneses.rest.tests.refac.AuthTest;
import br.ce.mmeneses.rest.tests.refac.ContasTest;
import br.ce.mmeneses.rest.tests.refac.MovimentacoesTest;
import br.ce.mmeneses.rest.tests.refac.SaldoTest;
import io.restassured.RestAssured;

@RunWith(org.junit.runners.Suite.class)
@SuiteClasses({
	ContasTest.class,
	MovimentacoesTest.class,
	SaldoTest.class,
	AuthTest.class
	
})
public class Suite extends BaseTest {
	@BeforeClass
	public static void login() {
		//System.out.println("Before conta");
		Map<String, String> login =  new HashMap<String, String>();
		login.put("email", "mateus@testador.com");
		login.put("senha", "123456");
		
		
	String  TOKEN =	given()
			.body(login)
		.when()
			.post("/signin")
		.then()
			.statusCode(200)
			.extract().path("token");
	
	requestSpecification.header("Authorization", "JWT " + TOKEN);
	
	RestAssured.get("/reset").then().statusCode(200);
	}
}
