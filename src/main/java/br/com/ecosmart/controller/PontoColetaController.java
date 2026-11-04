package br.com.ecosmart.controller;

import br.com.ecosmart.model.PontoColeta;
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
@RequestMapping("/api/pontos-coleta")
public class PontoColetaController {

    private final EcoSmartDataStore dataStore;

    public PontoColetaController(EcoSmartDataStore dataStore) {
        this.dataStore = dataStore;
    }

    @GetMapping
    public ResponseEntity<List<PontoColeta>> listarPontos(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer ocupacaoMinima,
            @RequestParam(required = false) String tipoResiduo) {
        return ResponseEntity.ok(dataStore.listarPontosColeta(status, ocupacaoMinima, tipoResiduo));
    }

    @GetMapping("/{codigoPonto}")
    public ResponseEntity<PontoColeta> buscarPorCodigo(@PathVariable String codigoPonto) {
        return ResponseEntity.ok(dataStore.buscarPontoPorCodigo(codigoPonto));
    }

    @PostMapping
    public ResponseEntity<PontoColeta> criarPonto(@Valid @RequestBody PontoColeta novoPonto) {
        PontoColeta criado = dataStore.criarPontoColeta(novoPonto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PatchMapping("/{codigoPonto}/telemetria")
    public ResponseEntity<PontoColeta> atualizarTelemetria(
            @PathVariable String codigoPonto,
            @RequestBody Map<String, Integer> payload) {
        Integer novoNivel = payload.get("nivel_ocupacao_percentual");
        Integer novaBateria = payload.get("bateria_percentual");
        PontoColeta atualizado = dataStore.atualizarTelemetriaPonto(codigoPonto, novoNivel, novaBateria);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{codigoPonto}")
    public ResponseEntity<Void> removerPonto(@PathVariable String codigoPonto) {
        dataStore.removerPontoColeta(codigoPonto);
        return ResponseEntity.noContent().build();
    }
}
