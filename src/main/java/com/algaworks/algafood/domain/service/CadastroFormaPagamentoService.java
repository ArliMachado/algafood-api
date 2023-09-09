package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradoException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaPagamentoService {

  @Autowired
  private FormaPagamentoRepository formaPagamentoRepository;

  @Transactional
  public FormaPagamento salvar(FormaPagamento formaPagamento) {

    return formaPagamentoRepository.save(formaPagamento);

  }

  public FormaPagamento buscarOuFalhar(Long formaPagamentoId) {
    return formaPagamentoRepository.findById(formaPagamentoId)
        .orElseThrow(() -> new FormaPagamentoNaoEncontradoException(formaPagamentoId));
  }

}
