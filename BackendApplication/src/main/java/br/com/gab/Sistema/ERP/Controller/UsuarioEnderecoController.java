package br.com.gab.Sistema.ERP.Controller;

import br.com.gab.Sistema.ERP.Generics.RetornoDTO;
import br.com.gab.Sistema.ERP.Repository.UsuarioEnderecoRepository;
import br.com.gab.Sistema.ERP.Repository.UsuarioRepository;
import br.com.gab.Sistema.ERP.Service.impl.UsuarioEnderecoServiceImpl;
import br.com.gab.Sistema.ERP.dto.UsuarioEnderecoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarioEndereco")
public class UsuarioEnderecoController {


    @Autowired
    private UsuarioEnderecoRepository usuarioEnderecoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioEnderecoRepository getService() {
        return usuarioEnderecoRepository;
    }
    public UsuarioEnderecoServiceImpl usuarioEnderecoService;


    @ResponseBody
    @PostMapping(value = "/salvar")
    public RetornoDTO salvar(@RequestParam("idUsuario") Long idUsuario, @RequestBody UsuarioEnderecoDTO dto) {
        RetornoDTO retornoDTO = new RetornoDTO();
        usuarioEnderecoService = new UsuarioEnderecoServiceImpl(usuarioRepository, usuarioEnderecoRepository);
        retornoDTO.setSuccess(usuarioEnderecoService.salvar(idUsuario, dto));
        retornoDTO.setMessage("Endereço salvo com sucesso");
        return retornoDTO;
    }
}
