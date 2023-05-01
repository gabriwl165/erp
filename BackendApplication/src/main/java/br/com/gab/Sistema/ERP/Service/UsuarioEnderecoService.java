package br.com.gab.Sistema.ERP.Service;

import br.com.gab.Sistema.ERP.dto.UsuarioEnderecoDTO;

public interface UsuarioEnderecoService {
    Boolean salvar(Long idUsuario, UsuarioEnderecoDTO dto);
}
