package br.com.gab.Sistema.ERP.Service.impl;

import br.com.gab.Sistema.ERP.EmailSender.SendEmail;
import br.com.gab.Sistema.ERP.Model.Usuario;
import br.com.gab.Sistema.ERP.Repository.UsuarioRepository;
import br.com.gab.Sistema.ERP.Service.UsuarioService;
import br.com.gab.Sistema.ERP.utils.PasswordEncoderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UsuarioServiceImpl implements UsuarioService  {

    @Autowired
    private SendEmail sendEmail;

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, SendEmail sendEmail) {
        this.usuarioRepository = usuarioRepository;
        this.sendEmail = sendEmail;
    }


    @Override
    public Boolean resetarSenha(Long id){
        try {
            Usuario usuario = usuarioRepository.buscarPorID(id);
            String novaSenha = genereteNewRandomString();
            sendEmail.sendEmail(usuario.getEmail(), novaSenha);
            usuario.setPassword(PasswordEncoderImpl.criptografar(novaSenha));
            usuarioRepository.save(usuario);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public String genereteNewRandomString(){
        // create a string of all characters
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        // create random string builder
        StringBuilder sb = new StringBuilder();

        // create an object of Random class
        Random random = new Random();

        // specify length of random string
        int length = 10;

        for (int i = 0; i < length; i++) {

            // generate random index number
            int index = random.nextInt(alphabet.length());

            // get character specified by index
            // from the string
            char randomChar = alphabet.charAt(index);

            // append the character to string builder
            sb.append(randomChar);
        }

        return sb.toString();
    }

}
