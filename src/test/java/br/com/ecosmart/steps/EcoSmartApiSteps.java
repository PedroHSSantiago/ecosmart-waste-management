package br.com.ecosmart.steps;

import br.com.ecosmart.service.EcoSmartDataStore;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EcoSmartApiSteps {

    @LocalServerPort
    private int port;

    @Autowired
    private EcoSmartDataStore dataStore;

    private Response response;
    private Map<String, Object> requestPayload;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        requestPayload = new LinkedHashMap<>();
    }

    @Dado("que a base de dados do EcoSmart está inicializada com os registros padrões ESG")
    public void queABaseDeDadosDoEcoSmartEstaInicializada() {
        dataStore.resetData();
    }

    // =========================================================================
    // PAYLOAD BUILDERS — PONTOS DE COLETA
    // =========================================================================

    @Dado("que eu possuo os dados válidos de um novo ponto de coleta {string} no bairro {string} com capacidade de {int} litros")
    public void dadosValidosNovoPontoColeta(String codigoPonto, String bairro, int capacidade) {
        requestPayload = new LinkedHashMap<>();
        requestPayload.put("codigo_ponto", codigoPonto);
        requestPayload.put("tipo_residuo", List.of("reciclavel", "vidro"));
        requestPayload.put("localizacao", Map.of("type", "Point", "coordinates", List.of(-44.9850, -12.1420)));
        requestPayload.put("bairro", bairro);
        requestPayload.put("capacidade_litros", capacidade);
        requestPayload.put("nivel_ocupacao_percentual", 15);
        requestPayload.put("status", "ativo");
        requestPayload.put("sensor_iot", Map.of(
                "modelo", "EcoSense X200",
                "bateria_percentual", 98,
                "temperatura_c", 25.5
        ));
    }

    @Dado("que eu desejo atualizar a telemetria do ponto {string} para ocupação {int} e bateria {int}")
    public void dadosAtualizacaoTelemetria(String codigoPonto, int ocupacao, int bateria) {
        requestPayload = new LinkedHashMap<>();
        requestPayload.put("nivel_ocupacao_percentual", ocupacao);
        requestPayload.put("bateria_percentual", bateria);
    }

    @Dado("que eu possuo um payload inválido de ponto de coleta {string} com nivel_ocupacao_percentual igual a {int}")
    public void payloadInvalidoPontoColetaOcupacao(String codigoPonto, int ocupacaoInvalida) {
        requestPayload = new LinkedHashMap<>();
        requestPayload.put("codigo_ponto", codigoPonto);
        requestPayload.put("tipo_residuo", List.of("organico"));
        requestPayload.put("localizacao", Map.of("type", "Point", "coordinates", List.of(-44.9900, -12.1400)));
        requestPayload.put("bairro", "Centro");
        requestPayload.put("capacidade_litros", 500);
        requestPayload.put("nivel_ocupacao_percentual", ocupacaoInvalida);
        requestPayload.put("status", "ativo");
    }

    // =========================================================================
    // PAYLOAD BUILDERS — COLETAS AGENDADAS
    // =========================================================================

    @Dado("que eu possuo os dados válidos para agendar a nova rota {string} atendendo os pontos {string} e {string}")
    public void dadosValidosNovaRotaColeta(String idRota, String ponto1, String ponto2) {
        requestPayload = new LinkedHashMap<>();
        requestPayload.put("id_rota", idRota);
        requestPayload.put("caminhao", Map.of(
                "placa", "BRS-9Z99",
                "motorista", "Roberto Almeida",
                "capacidade_kg", 7500
        ));
        requestPayload.put("pontos_atendidos", List.of(ponto1, ponto2));
        requestPayload.put("data_hora_agendada", "2026-09-12T08:00:00Z");
        requestPayload.put("status", "agendado");
        requestPayload.put("tipo_residuo_alvo", "reciclavel");
        requestPayload.put("distancia_km_estimada", 11.2);
        requestPayload.put("emissao_co2_estimada_kg", 8.4);
    }

    @Dado("que eu preparo a atualização de status da rota para {string}")
    public void preparoAtualizacaoStatusRota(String novoStatus) {
        requestPayload = new LinkedHashMap<>();
        requestPayload.put("status", novoStatus);
    }

    @Dado("que eu preparo a atualização de status da rota para {string} sem motivo de cancelamento")
    public void preparoCancelamentoSemMotivo(String novoStatus) {
        requestPayload = new LinkedHashMap<>();
        requestPayload.put("status", novoStatus);
    }

    @Dado("que eu possuo uma rota inválida {string} vinculada ao ponto inativo {string}")
    public void rotaInvalidaComPontoInativo(String idRota, String codigoPontoInativo) {
        requestPayload = new LinkedHashMap<>();
        requestPayload.put("id_rota", idRota);
        requestPayload.put("caminhao", Map.of(
                "placa", "BRS-1A23",
                "motorista", "Carlos Andrade",
                "capacidade_kg", 8000
        ));
        requestPayload.put("pontos_atendidos", List.of(codigoPontoInativo));
        requestPayload.put("data_hora_agendada", "2026-09-15T07:00:00Z");
        requestPayload.put("status", "agendado");
        requestPayload.put("tipo_residuo_alvo", "organico");
        requestPayload.put("distancia_km_estimada", 5.0);
        requestPayload.put("emissao_co2_estimada_kg", 3.9);
    }

    // =========================================================================
    // PAYLOAD BUILDERS — USUÁRIOS CIDADÃOS
    // =========================================================================

    @Dado("que o cidadão {string} realiza um descarte de {word} kg de {string} no ponto {string} valendo {int} pontos")
    public void cidadaoRealizaDescarte(String email, String pesoKgStr, String tipoResiduo, String codigoPonto, int pontos) {
        double pesoKg = Double.parseDouble(pesoKgStr.replace(",", "."));
        requestPayload = new LinkedHashMap<>();
        requestPayload.put("ponto_coleta_codigo", codigoPonto);
        requestPayload.put("tipo_residuo", tipoResiduo);
        requestPayload.put("peso_kg", pesoKg);
        requestPayload.put("pontos_ganhos", pontos);
    }

    @Dado("que eu possuo os dados válidos de um novo cidadão {string} com e-mail {string}")
    public void dadosValidosNovoCidadao(String nome, String email) {
        requestPayload = new LinkedHashMap<>();
        requestPayload.put("nome", nome);
        requestPayload.put("email", email);
        requestPayload.put("cpf_hash", "pedro123hash456");
        requestPayload.put("endereco", Map.of(
                "bairro", "Centro",
                "cep", "47800-100",
                "localizacao", Map.of("type", "Point", "coordinates", List.of(-44.9910, -12.1450))
        ));
        requestPayload.put("pontuacao_recompensas", 100);
    }

    // =========================================================================
    // PAYLOAD BUILDERS — COOPERATIVAS PARCEIRAS
    // =========================================================================

    @Dado("que eu possuo os dados válidos da nova cooperativa {string} com CNPJ {string}")
    public void dadosValidosNovaCooperativa(String codigoCooperativa, String cnpj) {
        requestPayload = new LinkedHashMap<>();
        requestPayload.put("codigo_cooperativa", codigoCooperativa);
        requestPayload.put("nome_cooperativa", "Cooperativa EcoBarreiras Sustentável");
        requestPayload.put("cnpj", cnpj);
        requestPayload.put("responsavel_legal", "Marcos Oliveira");
        requestPayload.put("endereco", Map.of(
                "bairro", "Morada Nobre",
                "cidade", "Barreiras",
                "uf", "BA"
        ));
        requestPayload.put("tipos_material_aceitos", List.of("plastico", "papel", "vidro"));
        requestPayload.put("capacidade_processamento_mensal_kg", 14000);
        requestPayload.put("num_cooperados", 20);
        requestPayload.put("contrato_municipal", Map.of(
                "numero", "CM-2026-030",
                "vigencia_inicio", "2026-09-01",
                "vigencia_fim", "2028-09-01",
                "valor_repasse_mensal", 7500.0
        ));
        requestPayload.put("certificacoes", List.of("ISO 14001"));
    }

    // =========================================================================
    // PAYLOAD BUILDERS — AUDITORIAS AMBIENTAIS
    // =========================================================================

    @Dado("que eu possuo os dados de uma nova auditoria {string} com valor medido {word} para a meta {word}")
    public void dadosNovaAuditoriaConforme(String codigoAuditoria, String valorMedidoStr, String metaMunicipalStr) {
        double valorMedido = Double.parseDouble(valorMedidoStr.replace(",", "."));
        double metaMunicipal = Double.parseDouble(metaMunicipalStr.replace(",", "."));
        requestPayload = new LinkedHashMap<>();
        requestPayload.put("codigo_auditoria", codigoAuditoria);
        requestPayload.put("periodo_referencia", "2026-04");
        requestPayload.put("metrica", "reducao_emissao_co2");
        requestPayload.put("valor_medido", valorMedido);
        requestPayload.put("unidade", "kg_co2_evitado");
        requestPayload.put("meta_municipal", metaMunicipal);
        requestPayload.put("responsavel_auditoria", "Secretaria de Meio Ambiente");
        requestPayload.put("status_conformidade", "conforme");
        requestPayload.put("evidencia_url", "https://ecosmart.gov/evidencias/2026-04-co2.pdf");
    }

    @Dado("que eu possuo uma auditoria não conforme {string} abaixo da meta sem plano de ação corretiva")
    public void dadosAuditoriaNaoConformeSemPlano(String codigoAuditoria) {
        requestPayload = new LinkedHashMap<>();
        requestPayload.put("codigo_auditoria", codigoAuditoria);
        requestPayload.put("periodo_referencia", "2026-04");
        requestPayload.put("metrica", "taxa_reciclagem_municipal");
        requestPayload.put("valor_medido", 20.0);
        requestPayload.put("unidade", "%");
        requestPayload.put("meta_municipal", 35.0);
        requestPayload.put("responsavel_auditoria", "Secretaria de Meio Ambiente");
        requestPayload.put("status_conformidade", "nao_conforme");
    }

    // =========================================================================
    // CHAMADAS HTTP REST ASSURED (QUANDO)
    // =========================================================================

    @Quando("eu envio uma requisição GET para {string}")
    public void envioRequisicaoGet(String endpoint) {
        response = RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get(endpoint);
    }

    @Quando("eu envio uma requisição POST para {string} com o payload configurado")
    public void envioRequisicaoPost(String endpoint) {
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestPayload)
                .when()
                .post(endpoint);
    }

    @Quando("eu envio uma requisição PATCH para {string} com o payload configurado")
    public void envioRequisicaoPatch(String endpoint) {
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestPayload)
                .when()
                .patch(endpoint);
    }

    @Quando("eu envio uma requisição DELETE para {string}")
    public void envioRequisicaoDelete(String endpoint) {
        response = RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .delete(endpoint);
    }

    // =========================================================================
    // ASSERÇÕES DE STATUS CODE, CORPO JSON E CONTRATO JSON SCHEMA (ENTÃO / E)
    // =========================================================================

    @Então("o status code da resposta deve ser {int}")
    public void validarStatusCode(int statusCodeEsperado) {
        assertEquals(statusCodeEsperado, response.getStatusCode(),
                "Status HTTP inesperado. Resposta: " + response.asString());
    }

    @E("o corpo da resposta deve respeitar o contrato JSON Schema {string}")
    public void validarContratoJsonSchema(String schemaPath) {
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
    }

    @E("o campo JSON {string} deve ser igual a {string}")
    public void validarCampoJsonString(String jsonPath, String valorEsperado) {
        String valorAtual = response.jsonPath().getString(jsonPath);
        assertThat("Valor do campo JSON '" + jsonPath + "' divergente", valorAtual, equalTo(valorEsperado));
    }

    @E("o campo JSON {string} deve ser igual a {int}")
    public void validarCampoJsonInt(String jsonPath, int valorEsperado) {
        int valorAtual = response.jsonPath().getInt(jsonPath);
        assertThat("Valor inteiro do campo JSON '" + jsonPath + "' divergente", valorAtual, equalTo(valorEsperado));
    }

    @E("o campo decimal JSON {string} deve ser igual a {word}")
    public void validarCampoJsonDouble(String jsonPath, String valorEsperadoStr) {
        double valorEsperado = Double.parseDouble(valorEsperadoStr.replace(",", "."));
        double valorAtual = response.jsonPath().getDouble(jsonPath);
        assertEquals(valorEsperado, valorAtual, 0.01, "Valor decimal do campo JSON '" + jsonPath + "' divergente");
    }

    @E("o campo JSON {string} deve conter {string}")
    public void validarCampoJsonContem(String jsonPath, String trechoEsperado) {
        String valorAtual = response.jsonPath().getString(jsonPath);
        assertThat("O campo JSON '" + jsonPath + "' não contém o texto esperado", valorAtual, containsString(trechoEsperado));
    }

    @E("a lista retornada deve conter exatamente {int} registro(s)")
    public void validarTamanhoListaRetornada(int quantidadeEsperada) {
        List<?> lista = response.jsonPath().getList("$");
        assertEquals(quantidadeEsperada, lista.size(), "Quantidade de registros retornados na lista JSON divergente");
    }
}
