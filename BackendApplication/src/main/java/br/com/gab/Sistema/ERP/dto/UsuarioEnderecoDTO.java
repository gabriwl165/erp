package br.com.gab.Sistema.ERP.dto;

import br.com.gab.Sistema.ERP.Generics.DTO;
import br.com.gab.Sistema.ERP.Generics.Model;
import br.com.gab.Sistema.ERP.Model.UsuarioEndereco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEnderecoDTO extends DTO<UsuarioEndereco> {

    private String siglaUF;
    private String endereco;
    private String numero;
    private String bairro;

    @Override
    public UsuarioEnderecoDTO toDTO(UsuarioEndereco usuarioEndereco) {
        UsuarioEnderecoDTO usuarioEnderecoDTO = new UsuarioEnderecoDTO();
        usuarioEnderecoDTO.setSiglaUF(usuarioEndereco.getSiglaUF());
        usuarioEnderecoDTO.setEndereco(usuarioEndereco.getEndereco());
        usuarioEnderecoDTO.setNumero(usuarioEndereco.getNumero());
        usuarioEnderecoDTO.setBairro(usuarioEndereco.getBairro());
        return usuarioEnderecoDTO;
    }

    @Override
    public UsuarioEndereco toEntity() {
        UsuarioEndereco usuarioEndereco = new UsuarioEndereco();
        usuarioEndereco.setSiglaUF(this.getSiglaUF());
        usuarioEndereco.setEndereco(this.getEndereco());
        usuarioEndereco.setNumero(this.getNumero());
        usuarioEndereco.setBairro(this.getBairro());
        return usuarioEndereco;
    }

    @Override
    public UsuarioEndereco getModel() {
        return new UsuarioEndereco();
    }
}
