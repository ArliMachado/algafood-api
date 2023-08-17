package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/formasPagamento")
public class FormaPagamentoController {

  @Autowired
  private FormaPagamentoRepository formaPagamentoRepository;

  @Autowired
  private CadastroFormaPagamentoService cadastroFormaPagamento;

  @GetMapping
  public List<FormaPagamento> listar() {
    return formaPagamentoRepository.findAll();
  }

  @GetMapping("/{formaPagamentoId}")
  public ResponseEntity<FormaPagamento> buscarPorId(@PathVariable Long formaPagamentoId) {
    Optional<FormaPagamento> formaPagamento = formaPagamentoRepository.findById(formaPagamentoId);

    if (formaPagamento.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(formaPagamento.get());
  }

  @PostMapping
  public ResponseEntity<FormaPagamento> adicionar(@RequestBody FormaPagamento formaPagamento) {
    formaPagamento = cadastroFormaPagamento.salvar(formaPagamento);

    return ResponseEntity.ok(formaPagamento);
  }

}
