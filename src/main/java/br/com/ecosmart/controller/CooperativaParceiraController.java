package br.com.ecosmart.controller;

import br.com.ecosmart.model.CooperativaParceira;
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
@RequestMapping("/api/cooperativas-parceiras")
public class CooperativaParceiraController {

    private final EcoSmartDataStore dataStore;

    public CooperativaParceiraController(EcoSmartDataStore dataStore) {
        this.dataStore = dataStore;
    }

    @GetMapping
    public ResponseEntity<List<CooperativaParceira>> listarCooperativas(
            @RequestParam(required = false) String material,
            @RequestParam(required = false) Integer capacidadeMinima) {
        return ResponseEntity.ok(dataStore.listarCooperativas(material, capacidadeMinima));
    }

    @GetMapping("/{codigoCooperativa}")
    public ResponseEntity<CooperativaParceira> buscarPorCodigo(@PathVariable String codigoCooperativa) {
        return ResponseEntity.ok(dataStore.buscarCooperativaPorCodigo(codigoCooperativa));
    }

    @PostMapping
    public ResponseEntity<CooperativaParceira> cadastrarCooperativa(
            @Valid @RequestBody CooperativaParceira novaCooperativa) {
        CooperativaParceira criada = dataStore.cadastrarCooperativa(novaCooperativa);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }
}
