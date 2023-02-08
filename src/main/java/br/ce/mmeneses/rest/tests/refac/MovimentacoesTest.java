package br.ce.mmeneses.rest.tests.refac;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;



import org.junit.Test;

import br.ce.mmeneses.rest.core.BaseTest;
import br.ce.mmeneses.rest.tests.Movimentacao;
import br.ce.mmeneses.rest.utils.BarrigaUtils;
import br.ce.mmeneses.rest.utils.DataUtils;
import io.restassured.RestAssured;

public class MovimentacoesTest extends BaseTest{
	
	
	public Integer getIdDaMovimentacaoPelaDescricao(String desc) {
		return RestAssured.get("/transacoes?descricao="+desc).then().extract().path("id[0]");
		
	}
	private Movimentacao getMovimentacaoValida() {
		Movimentacao mov = new Movimentacao();
				
				mov.setConta_id(BarrigaUtils.getIdDaContaPeloNome("Conta para movimentacoes"));
			//	mov.setUsuario_id();
				mov.setDescricao("Descricao da movimentacao");
				mov.setEnvolvido("Envolvido na movimentacao");
				mov.setTipo("REC");
				mov.setData_transacao(DataUtils.getDataDiferencaDias(-1));
				mov.setData_pagamento(DataUtils.getDataDiferencaDias(5));
				mov.setValor(100F);
				mov.setStatus(true);
					return mov;
			}
	
	

	@Test
	public void deveInserirMovimentacaoComSucesso () {
		Movimentacao mov = getMovimentacaoValida();
			given()
				.body(mov)
			.when()
				.post("/transacoes")
			.then()
				.statusCode(201)
			
				;
	}
	@Test
	public void deveValidarCamposObrigatoriosNaMovimentacao () {
		
			given()
				.body("{}")
			.when()
				.post("/transacoes")
			.then()
				.statusCode(400)
				.body("$", hasSize(8))
				.body("msg", hasItems(
						"Data da Movimenta��o � obrigat�rio",
						"Data do pagamento � obrigat�rio",
						"Descri��o � obrigat�rio",
						"Interessado � obrigat�rio",
						"Valor � obrigat�rio",
						"Valor deve ser um n�mero",
						"Conta � obrigat�rio",
						"Situa��o � obrigat�rio"
						))
			
				;
	}
	@Test
	public void naoDeveInserirMovimentacaoComDataFutura () {
		Movimentacao mov = getMovimentacaoValida();
		mov.setData_transacao(DataUtils.getDataDiferencaDias(2));
		
			given()
				.body(mov)
			.when()
				.post("/transacoes")
			.then()
				.statusCode(400)
				.body("$", hasSize(1))

				.body("msg", hasItem("Data da Movimenta��o deve ser menor ou igual � data atual"))
			
				;
	}
	@Test
	public void naoDeveRemoverContaComMovimentacao () {
		Integer CONTA_ID = BarrigaUtils.getIdDaContaPeloNome("Conta com movimentacao");
			given()
				.pathParam("id", CONTA_ID)
			.when()
				.delete("/contas/{id}")
			.then()
				.statusCode(500)
				.body("constraint", is("transacoes_conta_id_foreign"))

			
				;
	}
	@Test
	public void deveRemoverMovimentacao () {
		Integer MOV_ID = getIdDaMovimentacaoPelaDescricao("Movimentacao para exclusao");
			given()
				.pathParam("id", MOV_ID)
			.when()
				.delete("/transacoes/{id}")
			.then()
				.statusCode(204)

			
				;
	}
}