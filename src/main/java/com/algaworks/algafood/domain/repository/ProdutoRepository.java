package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;

@Repository
public interface ProdutoRepository extends CustomJpaRepository<Produto, Long> {

  @Query("from Produto where id like :productId and restaurante.id = :restauranteId")
  Optional<Produto> findById(@Param("productId") Long productId, @Param("restauranteId") Long restauranteId);

  List<Produto> findByRestaurante(Restaurante restaurante);

}
