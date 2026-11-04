package br.com.ecosmart.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioCidadao {

    @NotBlank(message = "O nome do cidadão é obrigatório")
    @JsonProperty("nome")
    private String nome;

    @NotBlank(message = "O e-mail do cidadão é obrigatório")
    @Email(message = "O e-mail informado é inválido")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "O hash do CPF (cpf_hash) é obrigatório")
    @JsonProperty("cpf_hash")
    private String cpfHash;

    @NotNull(message = "O endereço do cidadão é obrigatório")
    @Valid
    @JsonProperty("endereco")
    private EnderecoCidadao endereco;

    @Min(value = 0, message = "A pontuação de recompensas não pode ser negativa")
    @JsonProperty("pontuacao_recompensas")
    private Integer pontuacaoRecompensas = 0;

    @Pattern(regexp = "bronze|prata|ouro|platina",
             message = "O nível de engajamento deve ser 'bronze', 'prata', 'ouro' ou 'platina'")
    @JsonProperty("nivel_engajamento")
    private String nivelEngajamento = "bronze";

    @Valid
    @JsonProperty("historico_descarte")
    private List<RegistroDescarte> historicoDescarte = new ArrayList<>();

    @JsonProperty("data_cadastro")
    private String dataCadastro;

    @JsonProperty("badges")
    private List<String> badges;

    @JsonProperty("preferencias_notificacao")
    private PreferenciasNotificacao preferenciasNotificacao;

    public UsuarioCidadao() {
    }

    public UsuarioCidadao(String nome, String email, String cpfHash, EnderecoCidadao endereco,
                          Integer pontuacaoRecompensas, String nivelEngajamento,
                          List<RegistroDescarte> historicoDescarte, String dataCadastro,
                          List<String> badges, PreferenciasNotificacao preferenciasNotificacao) {
        this.nome = nome;
        this.email = email;
        this.cpfHash = cpfHash;
        this.endereco = endereco;
        this.pontuacaoRecompensas = pontuacaoRecompensas;
        this.nivelEngajamento = nivelEngajamento;
        this.historicoDescarte = historicoDescarte != null ? new ArrayList<>(historicoDescarte) : new ArrayList<>();
        this.dataCadastro = dataCadastro;
        this.badges = badges;
        this.preferenciasNotificacao = preferenciasNotificacao;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class EnderecoCidadao {

        @NotBlank(message = "O bairro do endereço é obrigatório")
        @JsonProperty("bairro")
        private String bairro;

        @NotBlank(message = "O CEP do endereço é obrigatório")
        @JsonProperty("cep")
        private String cep;

        @Valid
        @JsonProperty("localizacao")
        private GeoLocation localizacao;

        public EnderecoCidadao() {
        }

        public EnderecoCidadao(String bairro, String cep, GeoLocation localizacao) {
            this.bairro = bairro;
            this.cep = cep;
            this.localizacao = localizacao;
        }

        public String getBairro() {
            return bairro;
        }

        public void setBairro(String bairro) {
            this.bairro = bairro;
        }

        public String getCep() {
            return cep;
        }

        public void setCep(String cep) {
            this.cep = cep;
        }

        public GeoLocation getLocalizacao() {
            return localizacao;
        }

        public void setLocalizacao(GeoLocation localizacao) {
            this.localizacao = localizacao;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RegistroDescarte {

        @JsonProperty("data")
        private String data;

        @NotBlank(message = "O código do ponto de coleta (ponto_coleta_codigo) é obrigatório")
        @JsonProperty("ponto_coleta_codigo")
        private String pontoColetaCodigo;

        @NotBlank(message = "O tipo de resíduo descartado é obrigatório")
        @JsonProperty("tipo_residuo")
        private String tipoResiduo;

        @NotNull(message = "O peso em kg do descarte é obrigatório")
        @Positive(message = "O peso do descarte deve ser maior que zero")
        @JsonProperty("peso_kg")
        private Double pesoKg;

        @JsonProperty("pontos_ganhos")
        private Integer pontosGanhos;

        public RegistroDescarte() {
        }

        public RegistroDescarte(String data, String pontoColetaCodigo, String tipoResiduo, Double pesoKg, Integer pontosGanhos) {
            this.data = data;
            this.pontoColetaCodigo = pontoColetaCodigo;
            this.tipoResiduo = tipoResiduo;
            this.pesoKg = pesoKg;
            this.pontosGanhos = pontosGanhos;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getPontoColetaCodigo() {
            return pontoColetaCodigo;
        }

        public void setPontoColetaCodigo(String pontoColetaCodigo) {
            this.pontoColetaCodigo = pontoColetaCodigo;
        }

        public String getTipoResiduo() {
            return tipoResiduo;
        }

        public void setTipoResiduo(String tipoResiduo) {
            this.tipoResiduo = tipoResiduo;
        }

        public Double getPesoKg() {
            return pesoKg;
        }

        public void setPesoKg(Double pesoKg) {
            this.pesoKg = pesoKg;
        }

        public Integer getPontosGanhos() {
            return pontosGanhos;
        }

        public void setPontosGanhos(Integer pontosGanhos) {
            this.pontosGanhos = pontosGanhos;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PreferenciasNotificacao {

        @JsonProperty("email")
        private Boolean email;

        @JsonProperty("sms")
        private Boolean sms;

        public PreferenciasNotificacao() {
        }

        public PreferenciasNotificacao(Boolean email, Boolean sms) {
            this.email = email;
            this.sms = sms;
        }

        public Boolean getEmail() {
            return email;
        }

        public void setEmail(Boolean email) {
            this.email = email;
        }

        public Boolean getSms() {
            return sms;
        }

        public void setSms(Boolean sms) {
            this.sms = sms;
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpfHash() {
        return cpfHash;
    }

    public void setCpfHash(String cpfHash) {
        this.cpfHash = cpfHash;
    }

    public EnderecoCidadao getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoCidadao endereco) {
        this.endereco = endereco;
    }

    public Integer getPontuacaoRecompensas() {
        return pontuacaoRecompensas;
    }

    public void setPontuacaoRecompensas(Integer pontuacaoRecompensas) {
        this.pontuacaoRecompensas = pontuacaoRecompensas;
    }

    public String getNivelEngajamento() {
        return nivelEngajamento;
    }

    public void setNivelEngajamento(String nivelEngajamento) {
        this.nivelEngajamento = nivelEngajamento;
    }

    public List<RegistroDescarte> getHistoricoDescarte() {
        return historicoDescarte;
    }

    public void setHistoricoDescarte(List<RegistroDescarte> historicoDescarte) {
        this.historicoDescarte = historicoDescarte;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public List<String> getBadges() {
        return badges;
    }

    public void setBadges(List<String> badges) {
        this.badges = badges;
    }

    public PreferenciasNotificacao getPreferenciasNotificacao() {
        return preferenciasNotificacao;
    }

    public void setPreferenciasNotificacao(PreferenciasNotificacao preferenciasNotificacao) {
        this.preferenciasNotificacao = preferenciasNotificacao;
    }
}
