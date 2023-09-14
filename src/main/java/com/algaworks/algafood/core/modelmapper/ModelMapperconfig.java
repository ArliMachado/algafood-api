package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.model.EnderecoModel;
import com.algaworks.algafood.domain.model.Endereco;

@Configuration
public class ModelMapperconfig {

  @Bean
  public ModelMapper modelMapper() {

    var modelMapper = new ModelMapper();

    var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(
        Endereco.class, EnderecoModel.class);

    enderecoToEnderecoModelTypeMap.<String>addMapping(
        EnderecoSrc -> EnderecoSrc.getCidade().getEstado().getNome(),
        (EnderecoModelDest, value) -> EnderecoModelDest.getCidade().setEstado(value));

    return modelMapper;

  }
}
