package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.model.input.SenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
  @Autowired
  private UsuarioModelAssembler usuarioModelAssembler;

  @Autowired
  private UsuarioInputDisassembler usuarioInputDisassembler;

  @Autowired
  private CadastroUsuarioService cadastroUsuarioService;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @GetMapping
  private List<UsuarioModel> listar() {
    return usuarioModelAssembler.toCollectionModel(usuarioRepository.findAll());
  }

  @GetMapping("/{usuarioId}")
  private UsuarioModel buscar(@PathVariable Long usuarioId) {
    return usuarioModelAssembler.toModel(cadastroUsuarioService.buscarOuFalhar(usuarioId));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  private UsuarioModel adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
    Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);

    return usuarioModelAssembler.toModel(cadastroUsuarioService.salvar(usuario));

  }

  @PutMapping("/{usuarioId}")
  public UsuarioModel atualizar(@PathVariable Long usuarioId,
      @RequestBody @Valid UsuarioInput usuarioInput) {
    Usuario usuarioAtual = cadastroUsuarioService.buscarOuFalhar(usuarioId);
    usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
    usuarioAtual = cadastroUsuarioService.salvar(usuarioAtual);

    return usuarioModelAssembler.toModel(usuarioAtual);
  }

  @PutMapping("/{usuarioId}/senha")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
    cadastroUsuarioService.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
  }

}
