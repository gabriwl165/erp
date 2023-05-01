package br.com.gab.Sistema.ERP.Controller;

import br.com.gab.Sistema.ERP.Generics.ControllerGenerics;
import br.com.gab.Sistema.ERP.Generics.RetornoDTO;
import br.com.gab.Sistema.ERP.Model.Usuario;
import br.com.gab.Sistema.ERP.Repository.UsuarioRepository;
import br.com.gab.Sistema.ERP.Service.UsuarioService;
import br.com.gab.Sistema.ERP.dto.LoginDTO;
import br.com.gab.Sistema.ERP.dto.UsuarioDTO;
import br.com.gab.Sistema.ERP.utils.CriptografiaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/usuario")
public class UsuarioController extends ControllerGenerics<UsuarioDTO, Usuario> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public UsuarioRepository getService() {
        return usuarioRepository;
    }


    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RetornoDTO login(@RequestBody LoginDTO loginDTO) {
        RetornoDTO retornoDTO = new RetornoDTO();
        try {
            Usuario usuario = usuarioRepository.buscarUsuario(loginDTO.getLogin(), CriptografiaUtils.getStringCriptografada(loginDTO.getSenha()));
            retornoDTO.setSuccess(usuario != null);
            return retornoDTO;
        } catch (Exception e) {
            retornoDTO.setSuccess(false);
            return retornoDTO;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/resetarSenha", method = RequestMethod.POST)
    public RetornoDTO resetarSenhaUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        RetornoDTO retornoDTO = new RetornoDTO();

        try {
            retornoDTO.setSuccess(usuarioService.resetarSenha(usuarioDTO.getId()));
            return retornoDTO;
        } catch (Exception e) {
            retornoDTO.setSuccess(false);
            return retornoDTO;
        }
    }

    @Override
    public void beforeSave(UsuarioDTO usuarioDTO) throws Exception {
        Usuario usuario = usuarioRepository.existeUsuarioComLoginMesmoLogin(usuarioDTO.getLogin());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        if (usuario != null && usuarioDTO.getId() == null) throw new Exception("Já existe Usuário com mesmo login");
    }

    @Override
    public RetornoDTO salvar(@RequestBody UsuarioDTO dto) {
        RetornoDTO retornoDTO = new RetornoDTO();
        try {
            beforeSave(dto);
            Usuario usuario = dto.toEntity();
            this.getService().save(usuario);
            retornoDTO.setObject(usuario.getId());
            retornoDTO.setMessage("Salvo com sucesso");
            retornoDTO.setSuccess(true);
            return retornoDTO;
        } catch (Exception e) {
            retornoDTO.setMessage(e.getMessage());
            return retornoDTO;
        }
    }
}
