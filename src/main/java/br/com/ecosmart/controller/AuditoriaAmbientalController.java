package br.com.ecosmart.controller;

import br.com.ecosmart.model.AuditoriaAmbiental;
import br.com.ecosmart.service.EcoSmartDataStore;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auditorias-ambientais")
public class AuditoriaAmbientalController {

    private final EcoSmartDataStore dataStore;

    public AuditoriaAmbientalController(EcoSmartDataStore dataStore) {
        this.dataStore = dataStore;
    }

    @GetMapping
    public ResponseEntity<List<AuditoriaAmbiental>> listarAuditorias(
            @RequestParam(required = false) String statusConformidade) {
        return ResponseEntity.ok(dataStore.listarAuditorias(statusConformidade));
    }

    @GetMapping("/{codigoAuditoria}")
    public ResponseEntity<AuditoriaAmbiental> buscarPorCodigo(@PathVariable String codigoAuditoria) {
        return ResponseEntity.ok(dataStore.buscarAuditoriaPorCodigo(codigoAuditoria));
    }

    @PostMapping
    public ResponseEntity<AuditoriaAmbiental> registrarAuditoria(
            @Valid @RequestBody AuditoriaAmbiental novaAuditoria) {
        AuditoriaAmbiental criada = dataStore.registrarAuditoria(novaAuditoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }
}
