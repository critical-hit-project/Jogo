package com.critcal_hit.jogo.service;

import com.critcal_hit.jogo.model.Jogo;
import com.critcal_hit.jogo.repository.JogoRepository;
import com.critical_hit.lib.dto.jogo.JogoRequest;
import com.critical_hit.lib.dto.jogo.JogoResponse;
import com.critical_hit.lib.exeption.JogoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JogoService {

    private final JogoRepository jogoRepository;


    @Transactional
    public JogoResponse cadastrar(JogoRequest request) {
        Jogo jogo = Jogo.builder()
                .nome(request.nome())
                .genero(request.genero())
                .descricao(request.descricao())
                .preco(request.preco())
                .habilitado(false)
                .build();

        jogo = jogoRepository.save(jogo);
        return toResponse(jogo);
    }

    @Transactional
    public JogoResponse editar(JogoRequest request, Long id) {
        Jogo jogo = jogoRepository.findById(id).orElseThrow(() ->
                new JogoNaoEncontradoException("Jogo não encontrado"));

        jogo.setNome(request.nome());
        jogo.setGenero(request.genero());
        jogo.setDescricao(request.descricao());
        jogo.setPreco(request.preco());

        return toResponse(jogo);
    }

    @Transactional
    public JogoResponse alterarStatus(Long id) {
        Jogo jogo = jogoRepository.findById(id)
                .orElseThrow(() -> new JogoNaoEncontradoException("Jogo não encontrado com ID: " + id));

        jogo.setHabilitado(!jogo.getHabilitado());
        return toResponse(jogoRepository.save(jogo));
    }

    @Transactional(readOnly = true)
    public JogoResponse buscarPorId(Long id) {
        return jogoRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new JogoNaoEncontradoException("Jogo não encontrado com ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<JogoResponse> listarHabilitados() {
        return jogoRepository.findByHabilitadoTrue().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<JogoResponse> buscarPorIds(List<Long> ids) {
        return jogoRepository.findByIdIn(ids).stream()
                .map(this::toResponse)
                .toList();
    }

    private JogoResponse toResponse(Jogo j) {
        boolean disponivel = Boolean.TRUE.equals(j.getHabilitado());

        return new JogoResponse(
                j.getId(),
                j.getNome(),
                j.getGenero(),
                j.getDescricao(),
                j.getPreco(),
                disponivel
        );
    }
}