package br.com.ecosmart.controller;

import br.com.ecosmart.model.UsuarioCidadao;
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
@RequestMapping("/api/usuarios-cidadaos")
public class UsuarioCidadaoController {

    private final EcoSmartDataStore dataStore;

    public UsuarioCidadaoController(EcoSmartDataStore dataStore) {
        this.dataStore = dataStore;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioCidadao>> listarUsuarios(
            @RequestParam(required = false) Integer pontuacaoMinima) {
        return ResponseEntity.ok(dataStore.listarUsuariosCidadaos(pontuacaoMinima));
    }

    @GetMapping("/{email}")
    public ResponseEntity<UsuarioCidadao> buscarPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(dataStore.buscarUsuarioPorEmail(email));
    }

    @PostMapping
    public ResponseEntity<UsuarioCidadao> cadastrarUsuario(@Valid @RequestBody UsuarioCidadao novoUsuario) {
        UsuarioCidadao criado = dataStore.cadastrarUsuarioCidadao(novoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PostMapping("/{email}/descartes")
    public ResponseEntity<UsuarioCidadao> registrarDescarte(
            @PathVariable String email,
            @Valid @RequestBody UsuarioCidadao.RegistroDescarte descarte) {
        UsuarioCidadao atualizado = dataStore.registrarDescarteCidadao(email, descarte);
        return ResponseEntity.ok(atualizado);
    }
}

