package br.com.ecosmart.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuditoriaAmbiental {

    @JsonProperty("codigo_auditoria")
    private String codigoAuditoria;

    @NotBlank(message = "O período de referência (periodo_referencia) é obrigatório")
    @JsonProperty("periodo_referencia")
    private String periodoReferencia;

    @NotBlank(message = "O nome da métrica é obrigatório")
    @JsonProperty("metrica")
    private String metrica;

    @NotNull(message = "O valor medido é obrigatório")
    @Positive(message = "O valor medido deve ser maior que zero")
    @JsonProperty("valor_medido")
    private Double valorMedido;

    @NotBlank(message = "A unidade de medida é obrigatória")
    @JsonProperty("unidade")
    private String unidade;

    @NotNull(message = "A meta municipal é obrigatória")
    @Positive(message = "A meta municipal deve ser maior que zero")
    @JsonProperty("meta_municipal")
    private Double metaMunicipal;

    @JsonProperty("percentual_atingimento")
    private Double percentualAtingimento;

    @NotBlank(message = "O responsável pela auditoria é obrigatório")
    @JsonProperty("responsavel_auditoria")
    private String responsavelAuditoria;

    @Pattern(regexp = "conforme|nao_conforme",
             message = "O status de conformidade deve ser 'conforme' ou 'nao_conforme'")
    @JsonProperty("status_conformidade")
    private String statusConformidade;

    @Valid
    @JsonProperty("plano_acao_corretiva")
    private PlanoAcaoCorretiva planoAcaoCorretiva;

    @JsonProperty("evidencia_url")
    private String evidenciaUrl;

    @JsonProperty("observacoes")
    private String observacoes;

    public AuditoriaAmbiental() {
    }

    public AuditoriaAmbiental(String codigoAuditoria, String periodoReferencia, String metrica,
                              Double valorMedido, String unidade, Double metaMunicipal,
                              Double percentualAtingimento, String responsavelAuditoria,
                              String statusConformidade, PlanoAcaoCorretiva planoAcaoCorretiva,
                              String evidenciaUrl, String observacoes) {
        this.codigoAuditoria = codigoAuditoria;
        this.periodoReferencia = periodoReferencia;
        this.metrica = metrica;
        this.valorMedido = valorMedido;
        this.unidade = unidade;
        this.metaMunicipal = metaMunicipal;
        this.percentualAtingimento = percentualAtingimento;
        this.responsavelAuditoria = responsavelAuditoria;
        this.statusConformidade = statusConformidade;
        this.planoAcaoCorretiva = planoAcaoCorretiva;
        this.evidenciaUrl = evidenciaUrl;
        this.observacoes = observacoes;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PlanoAcaoCorretiva {

        @NotBlank(message = "A descrição do plano de ação corretiva é obrigatória")
        @JsonProperty("descricao")
        private String descricao;

        @NotBlank(message = "O prazo do plano de ação corretiva é obrigatório")
        @JsonProperty("prazo")
        private String prazo;

        public PlanoAcaoCorretiva() {
        }

        public PlanoAcaoCorretiva(String descricao, String prazo) {
            this.descricao = descricao;
            this.prazo = prazo;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        public String getPrazo() {
            return prazo;
        }

        public void setPrazo(String prazo) {
            this.prazo = prazo;
        }
    }

    public String getCodigoAuditoria() {
        return codigoAuditoria;
    }

    public void setCodigoAuditoria(String codigoAuditoria) {
        this.codigoAuditoria = codigoAuditoria;
    }

    public String getPeriodoReferencia() {
        return periodoReferencia;
    }

    public void setPeriodoReferencia(String periodoReferencia) {
        this.periodoReferencia = periodoReferencia;
    }

    public String getMetrica() {
        return metrica;
    }

    public void setMetrica(String metrica) {
        this.metrica = metrica;
    }

    public Double getValorMedido() {
        return valorMedido;
    }

    public void setValorMedido(Double valorMedido) {
        this.valorMedido = valorMedido;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public Double getMetaMunicipal() {
        return metaMunicipal;
    }

    public void setMetaMunicipal(Double metaMunicipal) {
        this.metaMunicipal = metaMunicipal;
    }

    public Double getPercentualAtingimento() {
        return percentualAtingimento;
    }

    public void setPercentualAtingimento(Double percentualAtingimento) {
        this.percentualAtingimento = percentualAtingimento;
    }

    public String getResponsavelAuditoria() {
        return responsavelAuditoria;
    }

    public void setResponsavelAuditoria(String responsavelAuditoria) {
        this.responsavelAuditoria = responsavelAuditoria;
    }

    public String getStatusConformidade() {
        return statusConformidade;
    }

    public void setStatusConformidade(String statusConformidade) {
        this.statusConformidade = statusConformidade;
    }

    public PlanoAcaoCorretiva getPlanoAcaoCorretiva() {
        return planoAcaoCorretiva;
    }

    public void setPlanoAcaoCorretiva(PlanoAcaoCorretiva planoAcaoCorretiva) {
        this.planoAcaoCorretiva = planoAcaoCorretiva;
    }

    public String getEvidenciaUrl() {
        return evidenciaUrl;
    }

    public void setEvidenciaUrl(String evidenciaUrl) {
        this.evidenciaUrl = evidenciaUrl;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
