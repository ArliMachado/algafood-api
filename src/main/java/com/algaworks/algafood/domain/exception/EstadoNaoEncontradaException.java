package com.algaworks.algafood.domain.exception;

public class EstadoNaoEncontradaException extends EntidadeNaoEncontradaException {
  private static final long serialVersionUID = 1L;

  public EstadoNaoEncontradaException(String message) {
    super(message);
  }

  public EstadoNaoEncontradaException(Long estadoId) {
    this(String.format("Não existe um cadastro de Estado com código %d", estadoId));
  }
}
