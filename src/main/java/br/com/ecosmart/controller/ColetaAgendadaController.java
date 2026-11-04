package br.com.ecosmart.controller;

import br.com.ecosmart.model.ColetaAgendada;
import br.com.ecosmart.service.EcoSmartDataStore;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/coletas-agendadas")
public class ColetaAgendadaController {

    private final EcoSmartDataStore dataStore;

    public ColetaAgendadaController(EcoSmartDataStore dataStore) {
        this.dataStore = dataStore;
    }

    @GetMapping
    public ResponseEntity<List<ColetaAgendada>> listarColetas(
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(dataStore.listarColetasAgendadas(status));
    }

    @GetMapping("/{idRota}")
    public ResponseEntity<ColetaAgendada> buscarPorIdRota(@PathVariable String idRota) {
        return ResponseEntity.ok(dataStore.buscarColetaPorIdRota(idRota));
    }

    @PostMapping
    public ResponseEntity<ColetaAgendada> criarColeta(@Valid @RequestBody ColetaAgendada novaColeta) {
        ColetaAgendada criada = dataStore.criarColetaAgendada(novaColeta);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @PatchMapping("/{idRota}/status")
    public ResponseEntity<ColetaAgendada> atualizarStatus(
            @PathVariable String idRota,
            @RequestBody Map<String, String> payload) {
        String novoStatus = payload.get("status");
        String motivoCancelamento = payload.get("motivo_cancelamento");
        ColetaAgendada atualizada = dataStore.atualizarStatusColeta(idRota, novoStatus, motivoCancelamento);
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/{idRota}")
    public ResponseEntity<Void> removerColeta(@PathVariable String idRota) {
        dataStore.removerColetaAgendada(idRota);
        return ResponseEntity.noContent().build();
    }
}
