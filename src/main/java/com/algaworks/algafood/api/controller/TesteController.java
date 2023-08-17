package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {

  @Autowired
  private RestauranteRepository restauranteRepository;

  @Autowired
  private CozinhaRepository cozinhaRepository;

  @GetMapping("/restaurantes/por-taxa-frete")
  public List<Restaurante> restaurantesPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
    return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
  }

  @GetMapping("/restaurantes/por-nome-e-cozinha")
  public List<Restaurante> restaurantesPorNomeEcozinha(String nome, Long cozinhaId) {
    return restauranteRepository.consultarPorNome(nome, cozinhaId);
  }

  @GetMapping("/restaurantes/primeiro-por-nome")
  public Restaurante restaurantePorNome(String nome) {
    return restauranteRepository.findFirstRestauranteByNomeContaining(nome).get();
  }

  @GetMapping("/restaurantes/top2-por-nome")
  public List<Restaurante> restaurantesTop2PorNome(String nome) {
    return restauranteRepository.findTop2ByNomeContaining(nome);
  }

  @GetMapping("/restaurantes/count-por-cozinha")
  public int restaurantesCountPorCozinha(Long cozinhaId) {
    return restauranteRepository.countByCozinhaId(cozinhaId);
  }

  @GetMapping("/cozinhas/exists")
  public boolean cozinhaExists(String nome) {
    return cozinhaRepository.existsByNome(nome);
  }

  @GetMapping("/restaurantes/por-nome-e-frete")
  public List<Restaurante> restaurantesPorNomeFrete(String nome,
      BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
    // return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
    return restauranteRepository.criteriaFind(nome, taxaFreteInicial, taxaFreteFinal);

  }

  @GetMapping("/restaurantes/com-frete-gratis")
  public List<Restaurante> restaurantesComFreteGratis(String nome) {

    return restauranteRepository.findComFreteGratis(nome);

  }

  @GetMapping("/restaurantes/primeiro")
  public Optional<Restaurante> restaurantePrimeiro() {
    return restauranteRepository.buscarPrimeiro();
  }

  @GetMapping("/cozinhas/primeiro")
  public Optional<Cozinha> cozinhaPrimeiro() {
    return cozinhaRepository.buscarPrimeiro();
  }
}
