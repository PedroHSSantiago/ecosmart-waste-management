package br.com.ecosmart.service;

import br.com.ecosmart.exception.BusinessRuleException;
import br.com.ecosmart.exception.ResourceConflictException;
import br.com.ecosmart.exception.ResourceNotFoundException;
import br.com.ecosmart.model.AuditoriaAmbiental;
import br.com.ecosmart.model.ColetaAgendada;
import br.com.ecosmart.model.CooperativaParceira;
import br.com.ecosmart.model.GeoLocation;
import br.com.ecosmart.model.PontoColeta;
import br.com.ecosmart.model.UsuarioCidadao;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EcoSmartDataStore {

    private final Map<String, PontoColeta> pontosColeta = new LinkedHashMap<>();
    private final Map<String, ColetaAgendada> coletasAgendadas = new LinkedHashMap<>();
    private final Map<String, UsuarioCidadao> usuariosCidadaos = new LinkedHashMap<>();
    private final Map<String, CooperativaParceira> cooperativasParceiras = new LinkedHashMap<>();
    private final Map<String, AuditoriaAmbiental> auditoriasAmbientais = new LinkedHashMap<>();

    @PostConstruct
    public synchronized void resetData() {
        pontosColeta.clear();
        coletasAgendadas.clear();
        usuariosCidadaos.clear();
        cooperativasParceiras.clear();
        auditoriasAmbientais.clear();

        seedPontosColeta();
        seedColetasAgendadas();
        seedUsuariosCidadaos();
        seedCooperativasParceiras();
        seedAuditoriasAmbientais();
    }

    private void seedPontosColeta() {
        salvarPontoSeed(new PontoColeta(
                "PC-001", List.of("organico", "reciclavel"), GeoLocation.of(-44.9903, -12.1444),
                "Centro", 500, 78, "ativo", "2023-03-10",
                new PontoColeta.SensorIot("EcoSense X200", 82, 27.4, "2026-09-07T08:00:00Z"),
                null, null
        ));
        salvarPontoSeed(new PontoColeta(
                "PC-002", List.of("reciclavel", "vidro"), GeoLocation.of(-44.9820, -12.1390),
                "Vila Rica", 700, 45, "ativo", "2023-05-22",
                new PontoColeta.SensorIot("EcoSense X200", 65, 29.1, "2026-09-07T08:05:00Z"),
                true, null
        ));
        salvarPontoSeed(new PontoColeta(
                "PC-003", List.of("organico"), GeoLocation.of(-44.9955, -12.1520),
                "Junco", 400, 92, "ativo", "2022-11-02",
                null, null, null
        ));
        salvarPontoSeed(new PontoColeta(
                "PC-004", List.of("eletronico"), GeoLocation.of(-45.0010, -12.1600),
                "Sandra Regina", 300, 30, "ativo", "2024-01-18",
                new PontoColeta.SensorIot("EcoSense Lite", 74, 26.0, "2026-09-07T07:50:00Z"),
                null, false
        ));
        salvarPontoSeed(new PontoColeta(
                "PC-005", List.of("organico", "reciclavel", "vidro"), GeoLocation.of(-44.9780, -12.1350),
                "Bela Vista", 600, 55, "ativo", "2023-07-14",
                null, true, null
        ));
        PontoColeta pc006 = new PontoColeta(
                "PC-006", List.of("reciclavel"), GeoLocation.of(-45.0050, -12.1700),
                "Nova Barreiras", 500, 88, "manutencao", "2023-02-09",
                new PontoColeta.SensorIot("EcoSense X200", 12, 31.2, "2026-09-06T22:00:00Z"),
                null, null
        );
        pc006.setUltimaManutencao("2026-09-08");
        salvarPontoSeed(pc006);

        salvarPontoSeed(new PontoColeta(
                "PC-007", List.of("organico"), GeoLocation.of(-44.9700, -12.1250),
                "São Judas Tadeu", 450, 20, "ativo", "2024-03-30",
                null, null, null
        ));
        salvarPontoSeed(new PontoColeta(
                "PC-008", List.of("eletronico", "reciclavel"), GeoLocation.of(-44.9600, -12.1200),
                "Alto da Boa Vista", 350, 67, "ativo", "2024-06-11",
                new PontoColeta.SensorIot("EcoSense Lite", 58, 28.3, "2026-09-07T08:10:00Z"),
                null, false
        ));
        salvarPontoSeed(new PontoColeta(
                "PC-009", List.of("reciclavel", "vidro"), GeoLocation.of(-44.9880, -12.1480),
                "Multivix", 550, 40, "ativo", "2023-09-25",
                new PontoColeta.SensorIot("EcoSense X200", 90, 27.0, "2026-09-07T08:02:00Z"),
                null, null
        ));
        PontoColeta pc010 = new PontoColeta(
                "PC-010", List.of("organico", "reciclavel"), GeoLocation.of(-45.0120, -12.1550),
                "Aeroporto", 500, 0, "inativo", "2021-12-01",
                null, null, null
        );
        pc010.setDataDesativacao("2026-08-15");
        salvarPontoSeed(pc010);
    }

    private void salvarPontoSeed(PontoColeta ponto) {
        pontosColeta.put(ponto.getCodigoPonto(), ponto);
    }

    private void seedColetasAgendadas() {
        salvarColetaSeed(new ColetaAgendada(
                "ROTA-001", new ColetaAgendada.Caminhao("BRS-1A23", "Carlos Andrade", 8000),
                List.of("PC-001", "PC-003", "PC-007"), "2026-09-09T07:00:00Z", "agendado",
                "organico", 12.4, 9.8, null, null, null
        ));
        salvarColetaSeed(new ColetaAgendada(
                "ROTA-002", new ColetaAgendada.Caminhao("BRS-2B45", "Marina Souza", 6000),
                List.of("PC-002", "PC-005", "PC-009"), "2026-09-08T08:30:00Z", "em_andamento",
                "reciclavel", 9.7, 7.1, null, null, null
        ));
        salvarColetaSeed(new ColetaAgendada(
                "ROTA-003", new ColetaAgendada.Caminhao("BRS-3C67", "João Pereira", 4000),
                List.of("PC-004", "PC-008"), "2026-09-08T09:00:00Z", "concluido",
                "eletronico", 6.2, 5.0, "Coleta especial realizada sem intercorrências", null, null
        ));
        salvarColetaSeed(new ColetaAgendada(
                "ROTA-004", new ColetaAgendada.Caminhao("BRS-4D89", "Ana Lima", 8000),
                List.of("PC-006"), "2026-09-07T07:00:00Z", "cancelado",
                "reciclavel", 3.1, 2.4, null, "Caminhão em manutenção mecânica", "2026-09-10T07:00:00Z"
        ));
        salvarColetaSeed(new ColetaAgendada(
                "ROTA-005", new ColetaAgendada.Caminhao("BRS-1A23", "Carlos Andrade", 8000),
                List.of("PC-001", "PC-002", "PC-005", "PC-009"), "2026-09-07T08:00:00Z", "concluido",
                "reciclavel", 15.6, 11.9, null, null, null
        ));
    }

    private void salvarColetaSeed(ColetaAgendada coleta) {
        coletasAgendadas.put(coleta.getIdRota(), coleta);
    }

    private void seedUsuariosCidadaos() {
        salvarUsuarioSeed(new UsuarioCidadao(
                "Ana Beatriz Ramos", "ana.ramos@email.com", "a1b2c3d4e5",
                new UsuarioCidadao.EnderecoCidadao("Centro", "47800-000", GeoLocation.of(-44.9905, -12.1440)),
                1250, "ouro",
                List.of(
                        new UsuarioCidadao.RegistroDescarte("2026-08-01", "PC-001", "reciclavel", 4.5, 90),
                        new UsuarioCidadao.RegistroDescarte("2026-08-15", "PC-001", "organico", 2.1, 40)
                ),
                "2024-01-15",
                List.of("Reciclador Bronze", "Reciclador Prata", "Reciclador Ouro"),
                new UsuarioCidadao.PreferenciasNotificacao(true, false)
        ));
        salvarUsuarioSeed(new UsuarioCidadao(
                "Bruno Carvalho", "bruno.carvalho@email.com", "f6g7h8i9j0",
                new UsuarioCidadao.EnderecoCidadao("Vila Rica", "47801-010", GeoLocation.of(-44.9822, -12.1392)),
                340, "bronze",
                List.of(
                        new UsuarioCidadao.RegistroDescarte("2026-07-10", "PC-002", "vidro", 1.8, 35)
                ),
                "2025-06-02",
                null, null
        ));
        salvarUsuarioSeed(new UsuarioCidadao(
                "Camila Duarte", "camila.duarte@email.com", "k1l2m3n4o5",
                new UsuarioCidadao.EnderecoCidadao("Junco", "47802-020", GeoLocation.of(-44.9958, -12.1522)),
                890, "prata",
                List.of(
                        new UsuarioCidadao.RegistroDescarte("2026-06-20", "PC-003", "organico", 3.0, 60),
                        new UsuarioCidadao.RegistroDescarte("2026-08-05", "PC-003", "organico", 2.5, 50)
                ),
                "2024-11-20",
                List.of("Reciclador Bronze", "Reciclador Prata"),
                null
        ));
        salvarUsuarioSeed(new UsuarioCidadao(
                "Elaine Gomes", "elaine.gomes@email.com", "u1v2w3x4y5",
                new UsuarioCidadao.EnderecoCidadao("Bela Vista", "47804-040", GeoLocation.of(-44.9782, -12.1352)),
                2100, "platina",
                List.of(
                        new UsuarioCidadao.RegistroDescarte("2026-03-01", "PC-005", "reciclavel", 5.0, 100),
                        new UsuarioCidadao.RegistroDescarte("2026-08-28", "PC-005", "organico", 3.3, 66)
                ),
                "2023-08-05",
                List.of("Reciclador Bronze", "Reciclador Prata", "Reciclador Ouro", "Reciclador Platina"),
                new UsuarioCidadao.PreferenciasNotificacao(true, true)
        ));
    }

    private void salvarUsuarioSeed(UsuarioCidadao usuario) {
        usuariosCidadaos.put(usuario.getEmail().toLowerCase(), usuario);
    }

    private void seedCooperativasParceiras() {
        salvarCooperativaSeed(new CooperativaParceira(
                "COOP-001", "Cooperativa Recicla Vida", "12.345.678/0001-01", "Marta Nogueira",
                new CooperativaParceira.EnderecoCooperativa("Distrito Industrial", "Barreiras", "BA"),
                List.of("papel", "plastico", "metal"), 15000,
                List.of(
                        new CooperativaParceira.VolumeProcessadoMensal("2026-07", 13950, 10450.0),
                        new CooperativaParceira.VolumeProcessadoMensal("2026-08", 14200, 10800.0)
                ),
                24,
                new CooperativaParceira.ContratoMunicipal("CM-2024-011", "2024-01-01", "2027-01-01", 8000.0),
                List.of("ISO 14001"), 4.7
        ));
        salvarCooperativaSeed(new CooperativaParceira(
                "COOP-002", "Cooperativa Verde Futuro", "23.456.789/0001-02", "Roberto Farias",
                new CooperativaParceira.EnderecoCooperativa("Vila Rica", "Barreiras", "BA"),
                List.of("vidro", "plastico"), 8000,
                List.of(
                        new CooperativaParceira.VolumeProcessadoMensal("2026-08", 6800, 4650.0)
                ),
                12,
                new CooperativaParceira.ContratoMunicipal("CM-2024-014", "2024-03-01", "2026-12-01", 4500.0),
                null, 4.2
        ));
        salvarCooperativaSeed(new CooperativaParceira(
                "COOP-003", "Cooperativa Mãos Que Reciclam", "34.567.890/0001-03", "Patrícia Melo",
                new CooperativaParceira.EnderecoCooperativa("Centro", "Barreiras", "BA"),
                List.of("papel", "papelao"), 20000,
                List.of(
                        new CooperativaParceira.VolumeProcessadoMensal("2026-08", 19800, 13900.0)
                ),
                35,
                new CooperativaParceira.ContratoMunicipal("CM-2023-005", "2023-06-01", "2027-06-01", 11000.0),
                List.of("ISO 9001", "ISO 14001"), 4.9
        ));
        salvarCooperativaSeed(new CooperativaParceira(
                "COOP-004", "Cooperativa União Sustentável", "67.890.123/0001-06", "Tiago Ribeiro",
                new CooperativaParceira.EnderecoCooperativa("Nova Barreiras", "Barreiras", "BA"),
                List.of("plastico", "metal", "vidro"), 18000,
                List.of(
                        new CooperativaParceira.VolumeProcessadoMensal("2026-08", 17100, 12300.0)
                ),
                27,
                new CooperativaParceira.ContratoMunicipal("CM-2023-009", "2023-09-01", "2026-09-01", 9500.0),
                List.of("ISO 14001"), 4.6
        ));
    }

    private void salvarCooperativaSeed(CooperativaParceira coop) {
        cooperativasParceiras.put(coop.getCodigoCooperativa(), coop);
    }

    private void seedAuditoriasAmbientais() {
        salvarAuditoriaSeed(new AuditoriaAmbiental(
                "AUD-001", "2026-01", "reducao_emissao_co2", 1200.0, "kg_co2_evitado",
                1000.0, 120.0, "Secretaria de Meio Ambiente", "conforme",
                null, "https://ecosmart.gov/evidencias/2026-01-co2.pdf", null
        ));
        salvarAuditoriaSeed(new AuditoriaAmbiental(
                "AUD-002", "2026-02", "taxa_reciclagem_municipal", 32.0, "%",
                35.0, 91.4, "Secretaria de Meio Ambiente", "nao_conforme",
                new AuditoriaAmbiental.PlanoAcaoCorretiva(
                        "Ampliar campanhas educativas nos bairros com menor adesão", "2026-04-30"
                ),
                null, null
        ));
        salvarAuditoriaSeed(new AuditoriaAmbiental(
                "AUD-003", "2026-03", "volume_residuos_desviados_aterro", 45000.0, "kg",
                40000.0, 112.5, "Consultoria GreenAudit", "conforme",
                null, null, null
        ));
        salvarAuditoriaSeed(new AuditoriaAmbiental(
                "AUD-004", "2026-01", "engajamento_cidadao", 4200.0, "usuarios_ativos",
                5000.0, 84.0, "Secretaria de Meio Ambiente", "nao_conforme",
                new AuditoriaAmbiental.PlanoAcaoCorretiva(
                        "Lançar campanha de indicação com bônus de pontos", "2026-05-31"
                ),
                null, null
        ));
    }

    private void salvarAuditoriaSeed(AuditoriaAmbiental aud) {
        auditoriasAmbientais.put(aud.getCodigoAuditoria(), aud);
    }

    // =========================================================================
    // 1. OPERAÇÕES DE PONTOS DE COLETA (PILAR AMBIENTAL)
    // =========================================================================

    public List<PontoColeta> listarPontosColeta(String status, Integer ocupacaoMinima, String tipoResiduo) {
        return pontosColeta.values().stream()
                .filter(p -> status == null || p.getStatus().equalsIgnoreCase(status))
                .filter(p -> ocupacaoMinima == null || p.getNivelOcupacaoPercentual() > ocupacaoMinima)
                .filter(p -> tipoResiduo == null || p.getTipoResiduo().contains(tipoResiduo.toLowerCase()))
                .collect(Collectors.toList());
    }

    public PontoColeta buscarPontoPorCodigo(String codigoPonto) {
        PontoColeta ponto = pontosColeta.get(codigoPonto);
        if (ponto == null) {
            throw new ResourceNotFoundException("Ponto de coleta não encontrado para o código: " + codigoPonto);
        }
        return ponto;
    }

    public PontoColeta criarPontoColeta(PontoColeta novoPonto) {
        if (pontosColeta.containsKey(novoPonto.getCodigoPonto())) {
            throw new ResourceConflictException("Já existe um ponto de coleta cadastrado com o código: " + novoPonto.getCodigoPonto());
        }
        if (novoPonto.getDataInstalacao() == null || novoPonto.getDataInstalacao().isBlank()) {
            novoPonto.setDataInstalacao(LocalDate.now().toString());
        }
        pontosColeta.put(novoPonto.getCodigoPonto(), novoPonto);
        return novoPonto;
    }

    public PontoColeta atualizarTelemetriaPonto(String codigoPonto, Integer novoNivelOcupacao, Integer novaBateria) {
        PontoColeta ponto = buscarPontoPorCodigo(codigoPonto);
        if (novoNivelOcupacao == null || novoNivelOcupacao < 0 || novoNivelOcupacao > 100) {
            throw new BusinessRuleException("O nível de ocupação percentual deve estar entre 0 e 100%");
        }
        ponto.setNivelOcupacaoPercentual(novoNivelOcupacao);
        if (novaBateria != null) {
            if (novaBateria < 0 || novaBateria > 100) {
                throw new BusinessRuleException("O nível de bateria do sensor IoT deve estar entre 0 e 100%");
            }
            if (ponto.getSensorIot() == null) {
                ponto.setSensorIot(new PontoColeta.SensorIot("EcoSense X200", novaBateria, 26.5, Instant.now().toString()));
            } else {
                ponto.getSensorIot().setBateriaPercentual(novaBateria);
                ponto.getSensorIot().setUltimaLeitura(Instant.now().toString());
            }
        }
        return ponto;
    }

    public void removerPontoColeta(String codigoPonto) {
        PontoColeta ponto = buscarPontoPorCodigo(codigoPonto);
        pontosColeta.remove(ponto.getCodigoPonto());
    }

    // =========================================================================
    // 2. OPERAÇÕES DE COLETAS AGENDADAS (PILAR GOVERNANÇA / OPERACIONAL)
    // =========================================================================

    public List<ColetaAgendada> listarColetasAgendadas(String status) {
        return coletasAgendadas.values().stream()
                .filter(c -> status == null || c.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    public ColetaAgendada buscarColetaPorIdRota(String idRota) {
        ColetaAgendada coleta = coletasAgendadas.get(idRota);
        if (coleta == null) {
            throw new ResourceNotFoundException("Rota de coleta agendada não encontrada: " + idRota);
        }
        return coleta;
    }

    public ColetaAgendada criarColetaAgendada(ColetaAgendada novaColeta) {
        if (coletasAgendadas.containsKey(novaColeta.getIdRota())) {
            throw new ResourceConflictException("Já existe uma coleta agendada com o id_rota: " + novaColeta.getIdRota());
        }
        for (String codigoPonto : novaColeta.getPontosAtendidos()) {
            PontoColeta ponto = pontosColeta.get(codigoPonto);
            if (ponto == null) {
                throw new BusinessRuleException("Ponto de coleta informado na rota não existe: " + codigoPonto);
            }
            if ("inativo".equalsIgnoreCase(ponto.getStatus())) {
                throw new BusinessRuleException("Não é permitido agendar coleta para ponto inativo: " + codigoPonto);
            }
        }
        coletasAgendadas.put(novaColeta.getIdRota(), novaColeta);
        return novaColeta;
    }

    public ColetaAgendada atualizarStatusColeta(String idRota, String novoStatus, String motivoCancelamento) {
        ColetaAgendada coleta = buscarColetaPorIdRota(idRota);
        if (novoStatus == null || !List.of("agendado", "em_andamento", "concluido", "cancelado").contains(novoStatus)) {
            throw new BusinessRuleException("Status de coleta inválido: " + novoStatus);
        }
        if ("cancelado".equals(novoStatus) && (motivoCancelamento == null || motivoCancelamento.isBlank())) {
            throw new BusinessRuleException("O motivo de cancelamento é obrigatório quando o status é 'cancelado'");
        }
        coleta.setStatus(novoStatus);
        if (motivoCancelamento != null && !motivoCancelamento.isBlank()) {
            coleta.setMotivoCancelamento(motivoCancelamento);
        }
        return coleta;
    }

    public void removerColetaAgendada(String idRota) {
        ColetaAgendada coleta = buscarColetaPorIdRota(idRota);
        coletasAgendadas.remove(coleta.getIdRota());
    }

    // =========================================================================
    // 3. OPERAÇÕES DE USUÁRIOS CIDADÃOS (PILAR SOCIAL)
    // =========================================================================

    public List<UsuarioCidadao> listarUsuariosCidadaos(Integer pontuacaoMinima) {
        return usuariosCidadaos.values().stream()
                .filter(u -> pontuacaoMinima == null || u.getPontuacaoRecompensas() > pontuacaoMinima)
                .collect(Collectors.toList());
    }

    public UsuarioCidadao buscarUsuarioPorEmail(String email) {
        UsuarioCidadao usuario = usuariosCidadaos.get(email.toLowerCase());
        if (usuario == null) {
            throw new ResourceNotFoundException("Cidadão não encontrado com o e-mail: " + email);
        }
        return usuario;
    }

    public UsuarioCidadao cadastrarUsuarioCidadao(UsuarioCidadao novoUsuario) {
        String chave = novoUsuario.getEmail().toLowerCase();
        if (usuariosCidadaos.containsKey(chave)) {
            throw new ResourceConflictException("Já existe um cidadão cadastrado com o e-mail: " + novoUsuario.getEmail());
        }
        if (novoUsuario.getDataCadastro() == null || novoUsuario.getDataCadastro().isBlank()) {
            novoUsuario.setDataCadastro(LocalDate.now().toString());
        }
        if (novoUsuario.getPontuacaoRecompensas() == null) {
            novoUsuario.setPontuacaoRecompensas(0);
        }
        novoUsuario.setNivelEngajamento(calcularNivelEngajamento(novoUsuario.getPontuacaoRecompensas()));
        usuariosCidadaos.put(chave, novoUsuario);
        return novoUsuario;
    }

    public UsuarioCidadao registrarDescarteCidadao(String email, UsuarioCidadao.RegistroDescarte descarte) {
        UsuarioCidadao usuario = buscarUsuarioPorEmail(email);
        PontoColeta ponto = buscarPontoPorCodigo(descarte.getPontoColetaCodigo());
        if (!"ativo".equalsIgnoreCase(ponto.getStatus())) {
            throw new BusinessRuleException("O ponto de coleta " + ponto.getCodigoPonto() + " não está ativo para receber descartes");
        }
        int pontosCalculados = descarte.getPontosGanhos() != null
                ? descarte.getPontosGanhos()
                : (int) Math.round(descarte.getPesoKg() * 20);
        descarte.setPontosGanhos(pontosCalculados);
        if (descarte.getData() == null || descarte.getData().isBlank()) {
            descarte.setData(LocalDate.now().toString());
        }

        List<UsuarioCidadao.RegistroDescarte> historico = new ArrayList<>(usuario.getHistoricoDescarte());
        historico.add(descarte);
        usuario.setHistoricoDescarte(historico);

        int novaPontuacao = usuario.getPontuacaoRecompensas() + pontosCalculados;
        usuario.setPontuacaoRecompensas(novaPontuacao);
        usuario.setNivelEngajamento(calcularNivelEngajamento(novaPontuacao));
        return usuario;
    }

    private String calcularNivelEngajamento(int pontuacao) {
        if (pontuacao >= 2000) return "platina";
        if (pontuacao >= 1000) return "ouro";
        if (pontuacao >= 500) return "prata";
        return "bronze";
    }

    // =========================================================================
    // 4. OPERAÇÕES DE COOPERATIVAS PARCEIRAS (PILAR SOCIAL / GOVERNANÇA)
    // =========================================================================

    public List<CooperativaParceira> listarCooperativas(String material, Integer capacidadeMinima) {
        return cooperativasParceiras.values().stream()
                .filter(c -> material == null || c.getTiposMaterialAceitos().contains(material.toLowerCase()))
                .filter(c -> capacidadeMinima == null || c.getCapacidadeProcessamentoMensalKg() > capacidadeMinima)
                .collect(Collectors.toList());
    }

    public CooperativaParceira buscarCooperativaPorCodigo(String codigoCooperativa) {
        CooperativaParceira coop = cooperativasParceiras.get(codigoCooperativa);
        if (coop == null) {
            throw new ResourceNotFoundException("Cooperativa parceira não encontrada: " + codigoCooperativa);
        }
        return coop;
    }

    public CooperativaParceira cadastrarCooperativa(CooperativaParceira novaCooperativa) {
        if (novaCooperativa.getCodigoCooperativa() == null || novaCooperativa.getCodigoCooperativa().isBlank()) {
            novaCooperativa.setCodigoCooperativa("COOP-00" + (cooperativasParceiras.size() + 1));
        }
        if (cooperativasParceiras.containsKey(novaCooperativa.getCodigoCooperativa())) {
            throw new ResourceConflictException("Já existe cooperativa com o código: " + novaCooperativa.getCodigoCooperativa());
        }
        boolean cnpjDuplicado = cooperativasParceiras.values().stream()
                .anyMatch(c -> c.getCnpj().equalsIgnoreCase(novaCooperativa.getCnpj()));
        if (cnpjDuplicado) {
            throw new ResourceConflictException("Já existe cooperativa cadastrada com o CNPJ: " + novaCooperativa.getCnpj());
        }
        cooperativasParceiras.put(novaCooperativa.getCodigoCooperativa(), novaCooperativa);
        return novaCooperativa;
    }

    // =========================================================================
    // 5. OPERAÇÕES DE AUDITORIA AMBIENTAL (PILAR GOVERNANÇA)
    // =========================================================================

    public List<AuditoriaAmbiental> listarAuditorias(String statusConformidade) {
        return auditoriasAmbientais.values().stream()
                .filter(a -> statusConformidade == null || a.getStatusConformidade().equalsIgnoreCase(statusConformidade))
                .collect(Collectors.toList());
    }

    public AuditoriaAmbiental buscarAuditoriaPorCodigo(String codigoAuditoria) {
        AuditoriaAmbiental auditoria = auditoriasAmbientais.get(codigoAuditoria);
        if (auditoria == null) {
            throw new ResourceNotFoundException("Registro de auditoria ambiental não encontrado: " + codigoAuditoria);
        }
        return auditoria;
    }

    public AuditoriaAmbiental registrarAuditoria(AuditoriaAmbiental novaAuditoria) {
        if (novaAuditoria.getCodigoAuditoria() == null || novaAuditoria.getCodigoAuditoria().isBlank()) {
            novaAuditoria.setCodigoAuditoria("AUD-00" + (auditoriasAmbientais.size() + 1));
        }
        if (auditoriasAmbientais.containsKey(novaAuditoria.getCodigoAuditoria())) {
            throw new ResourceConflictException("Já existe auditoria registrada com o código: " + novaAuditoria.getCodigoAuditoria());
        }

        double percentual = Math.round((novaAuditoria.getValorMedido() / novaAuditoria.getMetaMunicipal()) * 1000.0) / 10.0;
        novaAuditoria.setPercentualAtingimento(percentual);

        if (novaAuditoria.getStatusConformidade() == null || novaAuditoria.getStatusConformidade().isBlank()) {
            novaAuditoria.setStatusConformidade(percentual >= 95.0 ? "conforme" : "nao_conforme");
        }

        if ("nao_conforme".equalsIgnoreCase(novaAuditoria.getStatusConformidade())
                && novaAuditoria.getPlanoAcaoCorretiva() == null) {
            throw new BusinessRuleException("Auditorias com status 'nao_conforme' exigem obrigatoriamente um plano_acao_corretiva");
        }

        auditoriasAmbientais.put(novaAuditoria.getCodigoAuditoria(), novaAuditoria);
        return novaAuditoria;
    }
}

