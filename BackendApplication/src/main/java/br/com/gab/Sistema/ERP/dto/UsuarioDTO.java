package br.com.gab.Sistema.ERP.dto;

import br.com.gab.Sistema.ERP.Generics.DTO;
import br.com.gab.Sistema.ERP.Model.enums.FuncaoEnum;
import br.com.gab.Sistema.ERP.Model.Usuario;
import br.com.gab.Sistema.ERP.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class UsuarioDTO extends DTO<Usuario> {

    private Long id;
    private String login;
    private String senha;
    private String email;
    private String dataNascimento;
    private String funcao;

    @Override
    public UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        try {
            usuarioDTO.setId(usuario.getId());
            usuarioDTO.setLogin(usuario.getUsername());
            usuarioDTO.setEmail(usuario.getEmail());
            usuarioDTO.setDataNascimento(usuario.getDataNascimento().toString().split(" ")[0]);
            if (usuario.getFuncao() != null) usuarioDTO.setFuncao(usuario.getFuncao().toString());
            return usuarioDTO;
        } catch (Exception e){
            log.info(e.getMessage());
            return usuarioDTO;
        }
//        this.setDataNascimento(usuario.getDataNascimento());
    }

    public Usuario toEntity() throws ParseException {
        Usuario usuario = new Usuario();
        usuario.setId(this.getId());
        usuario.setUsername(this.getLogin());
        usuario.setPassword(this.getSenha());
        usuario.setEmail(this.getEmail());
        usuario.setDataNascimento(DateUtils.YYYY_MM_dd_ToDate(this.getDataNascimento()));
        usuario.setFuncao(FuncaoEnum.getEnum(this.getFuncao()));
        return usuario;
    }


    @Override
    public Usuario getModel() {
        return new Usuario();
    }


}
