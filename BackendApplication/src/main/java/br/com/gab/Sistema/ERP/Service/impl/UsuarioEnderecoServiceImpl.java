package br.com.gab.Sistema.ERP.Service.impl;

import br.com.gab.Sistema.ERP.Exception.UsuarioNotFoundException;
import br.com.gab.Sistema.ERP.Model.Usuario;
import br.com.gab.Sistema.ERP.Model.UsuarioEndereco;
import br.com.gab.Sistema.ERP.Repository.UsuarioEnderecoRepository;
import br.com.gab.Sistema.ERP.Repository.UsuarioRepository;
import br.com.gab.Sistema.ERP.dto.UsuarioEnderecoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioEnderecoServiceImpl {

    private final UsuarioEnderecoRepository usuarioEnderecoRepository;

    private final UsuarioRepository usuarioRepository;

    public UsuarioEnderecoServiceImpl(UsuarioRepository usuarioRepository, UsuarioEnderecoRepository usuarioEnderecoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioEnderecoRepository = usuarioEnderecoRepository;
    }

    public Boolean salvar(Long idUsuario, UsuarioEnderecoDTO dto) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            UsuarioEndereco usuarioEndereco = dto.toEntity();
            usuarioEnderecoRepository.save(usuarioEndereco);
            usuario.setUsuarioEndereco(usuarioEndereco);
            usuarioRepository.save(usuario);
            return true;
        } else {
            throw new UsuarioNotFoundException();
        }

    }

}
