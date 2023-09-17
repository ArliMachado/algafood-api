package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.api.assembler.ProdutoInputDisassembler;
import com.algaworks.algafood.api.assembler.ProdutoModelAssembler;
import com.algaworks.algafood.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {

  @Autowired
  private ProdutoRepository produtoRepository;

  @Autowired
  private ProdutoModelAssembler produtoModelAssembler;

  @Autowired
  private ProdutoInputDisassembler produtoInputDisassembler;

}
