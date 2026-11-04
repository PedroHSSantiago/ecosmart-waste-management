package br.com.ecosmart.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColetaAgendada {

    @NotBlank(message = "O identificador da rota (id_rota) é obrigatório")
    @JsonProperty("id_rota")
    private String idRota;

    @NotNull(message = "Os dados do caminhão (caminhao) são obrigatórios")
    @Valid
    @JsonProperty("caminhao")
    private Caminhao caminhao;

    @NotEmpty(message = "A lista de pontos atendidos (pontos_atendidos) não pode estar vazia")
    @JsonProperty("pontos_atendidos")
    private List<String> pontosAtendidos;

    @NotBlank(message = "A data e hora agendada (data_hora_agendada) é obrigatória")
    @JsonProperty("data_hora_agendada")
    private String dataHoraAgendada;

    @NotBlank(message = "O status da coleta é obrigatório")
    @Pattern(regexp = "agendado|em_andamento|concluido|cancelado",
             message = "O status deve ser 'agendado', 'em_andamento', 'concluido' ou 'cancelado'")
    @JsonProperty("status")
    private String status;

    @NotBlank(message = "O tipo de resíduo alvo (tipo_residuo_alvo) é obrigatório")
    @JsonProperty("tipo_residuo_alvo")
    private String tipoResiduoAlvo;

    @NotNull(message = "A distância estimada em km é obrigatória")
    @Positive(message = "A distância estimada deve ser maior que zero")
    @JsonProperty("distancia_km_estimada")
    private Double distanciaKmEstimada;

    @NotNull(message = "A emissão estimada de CO2 em kg é obrigatória")
    @Positive(message = "A emissão estimada de CO2 deve ser maior que zero")
    @JsonProperty("emissao_co2_estimada_kg")
    private Double emissaoCo2EstimadaKg;

    @JsonProperty("observacoes")
    private String observacoes;

    @JsonProperty("motivo_cancelamento")
    private String motivoCancelamento;

    @JsonProperty("reagendado_para")
    private String reagendadoPara;

    public ColetaAgendada() {
    }

    public ColetaAgendada(String idRota, Caminhao caminhao, List<String> pontosAtendidos,
                          String dataHoraAgendada, String status, String tipoResiduoAlvo,
                          Double distanciaKmEstimada, Double emissaoCo2EstimadaKg,
                          String observacoes, String motivoCancelamento, String reagendadoPara) {
        this.idRota = idRota;
        this.caminhao = caminhao;
        this.pontosAtendidos = pontosAtendidos;
        this.dataHoraAgendada = dataHoraAgendada;
        this.status = status;
        this.tipoResiduoAlvo = tipoResiduoAlvo;
        this.distanciaKmEstimada = distanciaKmEstimada;
        this.emissaoCo2EstimadaKg = emissaoCo2EstimadaKg;
        this.observacoes = observacoes;
        this.motivoCancelamento = motivoCancelamento;
        this.reagendadoPara = reagendadoPara;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Caminhao {

        @NotBlank(message = "A placa do caminhão é obrigatória")
        @JsonProperty("placa")
        private String placa;

        @NotBlank(message = "O nome do motorista é obrigatório")
        @JsonProperty("motorista")
        private String motorista;

        @NotNull(message = "A capacidade em kg do caminhão é obrigatória")
        @Positive(message = "A capacidade do caminhão deve ser maior que zero")
        @JsonProperty("capacidade_kg")
        private Integer capacidadeKg;

        public Caminhao() {
        }

        public Caminhao(String placa, String motorista, Integer capacidadeKg) {
            this.placa = placa;
            this.motorista = motorista;
            this.capacidadeKg = capacidadeKg;
        }

        public String getPlaca() {
            return placa;
        }

        public void setPlaca(String placa) {
            this.placa = placa;
        }

        public String getMotorista() {
            return motorista;
        }

        public void setMotorista(String motorista) {
            this.motorista = motorista;
        }

        public Integer getCapacidadeKg() {
            return capacidadeKg;
        }

        public void setCapacidadeKg(Integer capacidadeKg) {
            this.capacidadeKg = capacidadeKg;
        }
    }

    public String getIdRota() {
        return idRota;
    }

    public void setIdRota(String idRota) {
        this.idRota = idRota;
    }

    public Caminhao getCaminhao() {
        return caminhao;
    }

    public void setCaminhao(Caminhao caminhao) {
        this.caminhao = caminhao;
    }

    public List<String> getPontosAtendidos() {
        return pontosAtendidos;
    }

    public void setPontosAtendidos(List<String> pontosAtendidos) {
        this.pontosAtendidos = pontosAtendidos;
    }

    public String getDataHoraAgendada() {
        return dataHoraAgendada;
    }

    public void setDataHoraAgendada(String dataHoraAgendada) {
        this.dataHoraAgendada = dataHoraAgendada;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipoResiduoAlvo() {
        return tipoResiduoAlvo;
    }

    public void setTipoResiduoAlvo(String tipoResiduoAlvo) {
        this.tipoResiduoAlvo = tipoResiduoAlvo;
    }

    public Double getDistanciaKmEstimada() {
        return distanciaKmEstimada;
    }

    public void setDistanciaKmEstimada(Double distanciaKmEstimada) {
        this.distanciaKmEstimada = distanciaKmEstimada;
    }

    public Double getEmissaoCo2EstimadaKg() {
        return emissaoCo2EstimadaKg;
    }

    public void setEmissaoCo2EstimadaKg(Double emissaoCo2EstimadaKg) {
        this.emissaoCo2EstimadaKg = emissaoCo2EstimadaKg;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getMotivoCancelamento() {
        return motivoCancelamento;
    }

    public void setMotivoCancelamento(String motivoCancelamento) {
        this.motivoCancelamento = motivoCancelamento;
    }

    public String getReagendadoPara() {
        return reagendadoPara;
    }

    public void setReagendadoPara(String reagendadoPara) {
        this.reagendadoPara = reagendadoPara;
    }
}
