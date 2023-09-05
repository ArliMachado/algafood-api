package com.algaworks.algafood;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.math.BigDecimal;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@SpringBootTest
class CadastroCozinhaIT {

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Autowired
	CadastroRestauranteService cadastroRestaurante;

	@Test
	public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");

		novaCozinha = cadastroCozinha.salvar(novaCozinha);

		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}

	@Test
	public void deveFalhar_QuandoCadastrarCozinhaSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		ConstraintViolationException erroEsperado = Assertions.assertThrows(ConstraintViolationException.class, () -> {
			cadastroCozinha.salvar(novaCozinha);
		});

		assertThat(erroEsperado).isNotNull();
	}

	@Test
	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Argentina");

		novaCozinha = cadastroCozinha.salvar(novaCozinha);
		Long cozinhaId = novaCozinha.getId();

		Restaurante restaurante = new Restaurante();

		restaurante.setNome("Cozinha Boa");
		restaurante.setTaxaFrete(new BigDecimal(5));
		restaurante.setCozinha(novaCozinha);

		cadastroRestaurante.salvar(restaurante);

		EntidadeEmUsoException erroEsperado = Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
			cadastroCozinha.excluir(cozinhaId);
		});

		assertThat(erroEsperado).isNotNull();

	}

	@Test
	public void deveFalhar_QuandoExcluirCozinhaInexistente() {
		CozinhaNaoEncontradaException erroEsperado = Assertions.assertThrows(CozinhaNaoEncontradaException.class, () -> {
			cadastroCozinha.excluir(10000L);
		});

		assertThat(erroEsperado).isNotNull();
	}

}
