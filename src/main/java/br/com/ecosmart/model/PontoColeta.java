package br.com.ecosmart.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PontoColeta {

    @NotBlank(message = "O código do ponto de coleta (codigo_ponto) é obrigatório")
    @JsonProperty("codigo_ponto")
    private String codigoPonto;

    @NotEmpty(message = "A lista de tipos de resíduo (tipo_residuo) não pode estar vazia")
    @JsonProperty("tipo_residuo")
    private List<String> tipoResiduo;

    @NotNull(message = "A localização geoespacial (localizacao) é obrigatória")
    @Valid
    @JsonProperty("localizacao")
    private GeoLocation localizacao;

    @NotBlank(message = "O bairro é obrigatório")
    @JsonProperty("bairro")
    private String bairro;

    @NotNull(message = "A capacidade em litros é obrigatória")
    @Positive(message = "A capacidade em litros deve ser maior que zero")
    @JsonProperty("capacidade_litros")
    private Integer capacidadeLitros;

    @NotNull(message = "O nível de ocupação percentual é obrigatório")
    @Min(value = 0, message = "O nível de ocupação não pode ser menor que 0%")
    @Max(value = 100, message = "O nível de ocupação não pode ultrapassar 100%")
    @JsonProperty("nivel_ocupacao_percentual")
    private Integer nivelOcupacaoPercentual;

    @NotBlank(message = "O status do ponto de coleta é obrigatório")
    @Pattern(regexp = "ativo|manutencao|inativo", message = "O status deve ser 'ativo', 'manutencao' ou 'inativo'")
    @JsonProperty("status")
    private String status;

    @JsonProperty("data_instalacao")
    private String dataInstalacao;

    @Valid
    @JsonProperty("sensor_iot")
    private SensorIot sensorIot;

    @JsonProperty("campanha_educativa")
    private Boolean campanhaEducativa;

    @JsonProperty("alerta_incendio")
    private Boolean alertaIncendio;

    @JsonProperty("ultima_manutencao")
    private String ultimaManutencao;

    @JsonProperty("data_desativacao")
    private String dataDesativacao;

    public PontoColeta() {
    }

    public PontoColeta(String codigoPonto, List<String> tipoResiduo, GeoLocation localizacao,
                       String bairro, Integer capacidadeLitros, Integer nivelOcupacaoPercentual,
                       String status, String dataInstalacao, SensorIot sensorIot,
                       Boolean campanhaEducativa, Boolean alertaIncendio) {
        this.codigoPonto = codigoPonto;
        this.tipoResiduo = tipoResiduo;
        this.localizacao = localizacao;
        this.bairro = bairro;
        this.capacidadeLitros = capacidadeLitros;
        this.nivelOcupacaoPercentual = nivelOcupacaoPercentual;
        this.status = status;
        this.dataInstalacao = dataInstalacao;
        this.sensorIot = sensorIot;
        this.campanhaEducativa = campanhaEducativa;
        this.alertaIncendio = alertaIncendio;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SensorIot {

        @NotBlank(message = "O modelo do sensor IoT é obrigatório")
        @JsonProperty("modelo")
        private String modelo;

        @Min(value = 0, message = "A bateria deve ser entre 0 e 100%")
        @Max(value = 100, message = "A bateria deve ser entre 0 e 100%")
        @JsonProperty("bateria_percentual")
        private Integer bateriaPercentual;

        @JsonProperty("temperatura_c")
        private Double temperaturaC;

        @JsonProperty("ultima_leitura")
        private String ultimaLeitura;

        public SensorIot() {
        }

        public SensorIot(String modelo, Integer bateriaPercentual, Double temperaturaC, String ultimaLeitura) {
            this.modelo = modelo;
            this.bateriaPercentual = bateriaPercentual;
            this.temperaturaC = temperaturaC;
            this.ultimaLeitura = ultimaLeitura;
        }

        public String getModelo() {
            return modelo;
        }

        public void setModelo(String modelo) {
            this.modelo = modelo;
        }

        public Integer getBateriaPercentual() {
            return bateriaPercentual;
        }

        public void setBateriaPercentual(Integer bateriaPercentual) {
            this.bateriaPercentual = bateriaPercentual;
        }

        public Double getTemperaturaC() {
            return temperaturaC;
        }

        public void setTemperaturaC(Double temperaturaC) {
            this.temperaturaC = temperaturaC;
        }

        public String getUltimaLeitura() {
            return ultimaLeitura;
        }

        public void setUltimaLeitura(String ultimaLeitura) {
            this.ultimaLeitura = ultimaLeitura;
        }
    }

    public String getCodigoPonto() {
        return codigoPonto;
    }

    public void setCodigoPonto(String codigoPonto) {
        this.codigoPonto = codigoPonto;
    }

    public List<String> getTipoResiduo() {
        return tipoResiduo;
    }

    public void setTipoResiduo(List<String> tipoResiduo) {
        this.tipoResiduo = tipoResiduo;
    }

    public GeoLocation getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(GeoLocation localizacao) {
        this.localizacao = localizacao;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Integer getCapacidadeLitros() {
        return capacidadeLitros;
    }

    public void setCapacidadeLitros(Integer capacidadeLitros) {
        this.capacidadeLitros = capacidadeLitros;
    }

    public Integer getNivelOcupacaoPercentual() {
        return nivelOcupacaoPercentual;
    }

    public void setNivelOcupacaoPercentual(Integer nivelOcupacaoPercentual) {
        this.nivelOcupacaoPercentual = nivelOcupacaoPercentual;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDataInstalacao() {
        return dataInstalacao;
    }

    public void setDataInstalacao(String dataInstalacao) {
        this.dataInstalacao = dataInstalacao;
    }

    public SensorIot getSensorIot() {
        return sensorIot;
    }

    public void setSensorIot(SensorIot sensorIot) {
        this.sensorIot = sensorIot;
    }

    public Boolean getCampanhaEducativa() {
        return campanhaEducativa;
    }

    public void setCampanhaEducativa(Boolean campanhaEducativa) {
        this.campanhaEducativa = campanhaEducativa;
    }

    public Boolean getAlertaIncendio() {
        return alertaIncendio;
    }

    public void setAlertaIncendio(Boolean alertaIncendio) {
        this.alertaIncendio = alertaIncendio;
    }

    public String getUltimaManutencao() {
        return ultimaManutencao;
    }

    public void setUltimaManutencao(String ultimaManutencao) {
        this.ultimaManutencao = ultimaManutencao;
    }

    public String getDataDesativacao() {
        return dataDesativacao;
    }

    public void setDataDesativacao(String dataDesativacao) {
        this.dataDesativacao = dataDesativacao;
    }
}
