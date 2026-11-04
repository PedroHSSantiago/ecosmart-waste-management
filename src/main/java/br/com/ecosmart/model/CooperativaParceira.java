package br.com.ecosmart.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CooperativaParceira {

    @JsonProperty("codigo_cooperativa")
    private String codigoCooperativa;

    @NotBlank(message = "O nome da cooperativa (nome_cooperativa) é obrigatório")
    @JsonProperty("nome_cooperativa")
    private String nomeCooperativa;

    @NotBlank(message = "O CNPJ da cooperativa é obrigatório")
    @JsonProperty("cnpj")
    private String cnpj;

    @NotBlank(message = "O responsável legal é obrigatório")
    @JsonProperty("responsavel_legal")
    private String responsavelLegal;

    @NotNull(message = "O endereço da cooperativa é obrigatório")
    @Valid
    @JsonProperty("endereco")
    private EnderecoCooperativa endereco;

    @NotEmpty(message = "A lista de tipos de material aceitos (tipos_material_aceitos) não pode estar vazia")
    @JsonProperty("tipos_material_aceitos")
    private List<String> tiposMaterialAceitos;

    @NotNull(message = "A capacidade de processamento mensal em kg é obrigatória")
    @Positive(message = "A capacidade de processamento mensal deve ser maior que zero")
    @JsonProperty("capacidade_processamento_mensal_kg")
    private Integer capacidadeProcessamentoMensalKg;

    @JsonProperty("volume_processado_historico")
    private List<VolumeProcessadoMensal> volumeProcessadoHistorico;

    @NotNull(message = "O número de cooperados é obrigatório")
    @Positive(message = "O número de cooperados deve ser maior que zero")
    @JsonProperty("num_cooperados")
    private Integer numCooperados;

    @NotNull(message = "O contrato municipal é obrigatório")
    @Valid
    @JsonProperty("contrato_municipal")
    private ContratoMunicipal contratoMunicipal;

    @JsonProperty("certificacoes")
    private List<String> certificacoes;

    @JsonProperty("avaliacao_desempenho")
    private Double avaliacaoDesempenho;

    public CooperativaParceira() {
    }

    public CooperativaParceira(String codigoCooperativa, String nomeCooperativa, String cnpj,
                               String responsavelLegal, EnderecoCooperativa endereco,
                               List<String> tiposMaterialAceitos, Integer capacidadeProcessamentoMensalKg,
                               List<VolumeProcessadoMensal> volumeProcessadoHistorico, Integer numCooperados,
                               ContratoMunicipal contratoMunicipal, List<String> certificacoes,
                               Double avaliacaoDesempenho) {
        this.codigoCooperativa = codigoCooperativa;
        this.nomeCooperativa = nomeCooperativa;
        this.cnpj = cnpj;
        this.responsavelLegal = responsavelLegal;
        this.endereco = endereco;
        this.tiposMaterialAceitos = tiposMaterialAceitos;
        this.capacidadeProcessamentoMensalKg = capacidadeProcessamentoMensalKg;
        this.volumeProcessadoHistorico = volumeProcessadoHistorico;
        this.numCooperados = numCooperados;
        this.contratoMunicipal = contratoMunicipal;
        this.certificacoes = certificacoes;
        this.avaliacaoDesempenho = avaliacaoDesempenho;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class EnderecoCooperativa {

        @NotBlank(message = "O bairro é obrigatório")
        @JsonProperty("bairro")
        private String bairro;

        @NotBlank(message = "A cidade é obrigatória")
        @JsonProperty("cidade")
        private String cidade;

        @NotBlank(message = "A UF é obrigatória")
        @JsonProperty("uf")
        private String uf;

        public EnderecoCooperativa() {
        }

        public EnderecoCooperativa(String bairro, String cidade, String uf) {
            this.bairro = bairro;
            this.cidade = cidade;
            this.uf = uf;
        }

        public String getBairro() {
            return bairro;
        }

        public void setBairro(String bairro) {
            this.bairro = bairro;
        }

        public String getCidade() {
            return cidade;
        }

        public void setCidade(String cidade) {
            this.cidade = cidade;
        }

        public String getUf() {
            return uf;
        }

        public void setUf(String uf) {
            this.uf = uf;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class VolumeProcessadoMensal {

        @JsonProperty("mes_ano")
        private String mesAno;

        @JsonProperty("volume_kg")
        private Integer volumeKg;

        @JsonProperty("receita_gerada_reais")
        private Double receitaGeradaReais;

        public VolumeProcessadoMensal() {
        }

        public VolumeProcessadoMensal(String mesAno, Integer volumeKg, Double receitaGeradaReais) {
            this.mesAno = mesAno;
            this.volumeKg = volumeKg;
            this.receitaGeradaReais = receitaGeradaReais;
        }

        public String getMesAno() {
            return mesAno;
        }

        public void setMesAno(String mesAno) {
            this.mesAno = mesAno;
        }

        public Integer getVolumeKg() {
            return volumeKg;
        }

        public void setVolumeKg(Integer volumeKg) {
            this.volumeKg = volumeKg;
        }

        public Double getReceitaGeradaReais() {
            return receitaGeradaReais;
        }

        public void setReceitaGeradaReais(Double receitaGeradaReais) {
            this.receitaGeradaReais = receitaGeradaReais;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ContratoMunicipal {

        @NotBlank(message = "O número do contrato municipal é obrigatório")
        @JsonProperty("numero")
        private String numero;

        @NotBlank(message = "A data de início de vigência é obrigatória")
        @JsonProperty("vigencia_inicio")
        private String vigenciaInicio;

        @NotBlank(message = "A data de fim de vigência é obrigatória")
        @JsonProperty("vigencia_fim")
        private String vigenciaFim;

        @NotNull(message = "O valor do repasse mensal é obrigatório")
        @Positive(message = "O valor do repasse mensal deve ser maior que zero")
        @JsonProperty("valor_repasse_mensal")
        private Double valorRepasseMensal;

        public ContratoMunicipal() {
        }

        public ContratoMunicipal(String numero, String vigenciaInicio, String vigenciaFim, Double valorRepasseMensal) {
            this.numero = numero;
            this.vigenciaInicio = vigenciaInicio;
            this.vigenciaFim = vigenciaFim;
            this.valorRepasseMensal = valorRepasseMensal;
        }

        public String getNumero() {
            return numero;
        }

        public void setNumero(String numero) {
            this.numero = numero;
        }

        public String getVigenciaInicio() {
            return vigenciaInicio;
        }

        public void setVigenciaInicio(String vigenciaInicio) {
            this.vigenciaInicio = vigenciaInicio;
        }

        public String getVigenciaFim() {
            return vigenciaFim;
        }

        public void setVigenciaFim(String vigenciaFim) {
            this.vigenciaFim = vigenciaFim;
        }

        public Double getValorRepasseMensal() {
            return valorRepasseMensal;
        }

        public void setValorRepasseMensal(Double valorRepasseMensal) {
            this.valorRepasseMensal = valorRepasseMensal;
        }
    }

    public String getCodigoCooperativa() {
        return codigoCooperativa;
    }

    public void setCodigoCooperativa(String codigoCooperativa) {
        this.codigoCooperativa = codigoCooperativa;
    }

    public String getNomeCooperativa() {
        return nomeCooperativa;
    }

    public void setNomeCooperativa(String nomeCooperativa) {
        this.nomeCooperativa = nomeCooperativa;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getResponsavelLegal() {
        return responsavelLegal;
    }

    public void setResponsavelLegal(String responsavelLegal) {
        this.responsavelLegal = responsavelLegal;
    }

    public EnderecoCooperativa getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoCooperativa endereco) {
        this.endereco = endereco;
    }

    public List<String> getTiposMaterialAceitos() {
        return tiposMaterialAceitos;
    }

    public void setTiposMaterialAceitos(List<String> tiposMaterialAceitos) {
        this.tiposMaterialAceitos = tiposMaterialAceitos;
    }

    public Integer getCapacidadeProcessamentoMensalKg() {
        return capacidadeProcessamentoMensalKg;
    }

    public void setCapacidadeProcessamentoMensalKg(Integer capacidadeProcessamentoMensalKg) {
        this.capacidadeProcessamentoMensalKg = capacidadeProcessamentoMensalKg;
    }

    public List<VolumeProcessadoMensal> getVolumeProcessadoHistorico() {
        return volumeProcessadoHistorico;
    }

    public void setVolumeProcessadoHistorico(List<VolumeProcessadoMensal> volumeProcessadoHistorico) {
        this.volumeProcessadoHistorico = volumeProcessadoHistorico;
    }

    public Integer getNumCooperados() {
        return numCooperados;
    }

    public void setNumCooperados(Integer numCooperados) {
        this.numCooperados = numCooperados;
    }

    public ContratoMunicipal getContratoMunicipal() {
        return contratoMunicipal;
    }

    public void setContratoMunicipal(ContratoMunicipal contratoMunicipal) {
        this.contratoMunicipal = contratoMunicipal;
    }

    public List<String> getCertificacoes() {
        return certificacoes;
    }

    public void setCertificacoes(List<String> certificacoes) {
        this.certificacoes = certificacoes;
    }

    public Double getAvaliacaoDesempenho() {
        return avaliacaoDesempenho;
    }

    public void setAvaliacaoDesempenho(Double avaliacaoDesempenho) {
        this.avaliacaoDesempenho = avaliacaoDesempenho;
    }
}
