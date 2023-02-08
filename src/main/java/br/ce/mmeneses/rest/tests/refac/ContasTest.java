package br.ce.mmeneses.rest.tests.refac;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;


import org.junit.Test;

import br.ce.mmeneses.rest.core.BaseTest;
import br.ce.mmeneses.rest.utils.BarrigaUtils;

public class ContasTest extends BaseTest{
	

	@Test
	public void deveIncluirContaComSucesso() {
	//	System.out.println("Incluir");
		given()
				.body("{ \"nome\": \"Conta inserida\" }")
			.when()
				.post("/contas")
			.then()
				.statusCode(201)
				;
	}
	@Test
	public void deveAlterarContaComSucesso() {		
	//	System.out.println("Alterar");
		Integer CONTA_ID = BarrigaUtils.getIdDaContaPeloNome("Conta para alterar");
		
		given()
				.body("{ \"nome\": \"Conta alterada\" }")
				.pathParam("id", CONTA_ID)
			.when()
				.put("/contas/{id}")
			.then()
				.statusCode(200)
				.body("nome", is("Conta alterada"));
	}
	
	
	
	@Test
	public void naoDeveInserirContaComMesmoNome() {
	
			given()
				.body("{ \"nome\": \"Conta mesmo nome\" }")
			.when()
				.post("/contas")
			.then()
				.log().all()
				.statusCode(400)
				.body("error", is("Já existe uma conta com esse nome!"))
				;
	}
}
