package com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

  private static final String MSG_USUARIO_EM_USO = "Usuário de código %d não pode ser removido, pois está em uso";

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Transactional
  public Usuario salvar(Usuario usuario) {

    usuarioRepository.detach(usuario);

    var usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());

    if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
      throw new NegocioException(String.format("Já existe um usuário cadastrado com o e-mail %s", usuario.getEmail()));
    }

    return usuarioRepository.save(usuario);
  }

  @Transactional
  public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
    Usuario usuario = buscarOuFalhar(usuarioId);

    if (usuario.senhaNaoCoincideCom(senhaAtual)) {
      throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
    }

    usuario.setSenha(novaSenha);
  }

  @Transactional
  public void excluir(Long usuarioId) {
    try {
      usuarioRepository.deleteById(usuarioId);
      usuarioRepository.flush();
    } catch (EmptyResultDataAccessException e) {
      throw new UsuarioNaoEncontradoException(usuarioId);
    } catch (DataIntegrityViolationException e) {
      throw new EntidadeEmUsoException(
          String.format(MSG_USUARIO_EM_USO, usuarioId));
    }
  }

  public Usuario buscarOuFalhar(Long usuarioid) {
    return usuarioRepository.findById(usuarioid)
        .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioid));
  }

}
