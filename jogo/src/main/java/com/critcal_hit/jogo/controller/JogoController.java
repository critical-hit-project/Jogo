package com.critcal_hit.jogo.controller;

import com.critical_hit.jogo.service.JogoService;
import com.critical_hit.lib.dto.jogo.JogoRequest;
import com.critical_hit.lib.dto.jogo.JogoResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jogos")
public class JogoController {

    private final JogoService jogoService;

    public JogoController(JogoService jogoService) {
        this.jogoService = jogoService;
    }

    @PostMapping
    public ResponseEntity<JogoResponse> cadastrar(@Valid @RequestBody JogoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jogoService.cadastrar(request));
    }

    @PatchMapping("/{id}/habilitar")
    public ResponseEntity<JogoResponse> habilitar(@PathVariable Long id) {
        return ResponseEntity.ok(jogoService.habilitar(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JogoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(jogoService.buscarPorId(id));
    }

    @GetMapping("/habilitados")
    public ResponseEntity<List<JogoResponse>> listarHabilitados() {
        return ResponseEntity.ok(jogoService.listarHabilitados());
    }

    @GetMapping("/busca-lote")
    public ResponseEntity<List<JogoResponse>> buscarPorIds(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(jogoService.buscarPorIds(ids));
    }
}