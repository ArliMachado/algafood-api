package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.assembler.ProdutoModelAssembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

  @Autowired
  private CadastroRestauranteService cadastroRestaurante;

  @Autowired
  private ProdutoModelAssembler produtoModelAssembler;

  @GetMapping
  public List<ProdutoModel> listar(@PathVariable Long restauranteId) {
    Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

    return produtoModelAssembler.toCollectionModel(restaurante.getProdutos());
  }

  @GetMapping("/{produtoId}")
  public List<ProdutoModel> buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
    Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
    // Pro

    return produtoModelAssembler.toCollectionModel(restaurante.getProdutos());
  }

  @DeleteMapping("/{formaPagamentoId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
    cadastroRestaurante.desassociarFormaPagamento(restauranteId, formaPagamentoId);
  }

  @PutMapping("/{formaPagamentoId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
    cadastroRestaurante.associarFormaPagamento(restauranteId, formaPagamentoId);
  }

}
