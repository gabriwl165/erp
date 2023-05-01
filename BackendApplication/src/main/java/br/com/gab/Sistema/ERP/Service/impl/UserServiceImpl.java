package br.com.gab.Sistema.ERP.Service.impl;

import br.com.gab.Sistema.ERP.Exception.RoleNotFoundException;
import br.com.gab.Sistema.ERP.Exception.UsuarioNotFoundException;
import br.com.gab.Sistema.ERP.Model.RoleModel;
import br.com.gab.Sistema.ERP.Model.Usuario;
import br.com.gab.Sistema.ERP.Repository.RoleModelRepository;
import br.com.gab.Sistema.ERP.Repository.UsuarioRepository;
import br.com.gab.Sistema.ERP.Service.UserService;
import br.com.gab.Sistema.ERP.Service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final RoleModelRepository roleModelRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Usuario saveUser(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public RoleModel saveRole(RoleModel role) {
        return roleModelRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        Usuario user = usuarioRepository.findByUsername(username);
        if(user == null){
            throw new UsuarioNotFoundException();
        }
        RoleModel role = roleModelRepository.findByName(roleName);
        if(role == null){
            throw new RoleNotFoundException();
        }
        user.getRoles().add(role);
        usuarioRepository.save(user);
    }

    @Override
    public Usuario getUser(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public List<Usuario> getUsers() {
        List<Usuario> listaUsuario = new ArrayList<>();
        usuarioRepository.findAll().forEach(u -> {
            listaUsuario.add(u);
        });
        return listaUsuario;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if(usuario == null){
            System.err.println("Não achamos nada aqui fio");
            throw new UsernameNotFoundException("Não achamos nada aqui fio");
        } else {
            System.err.println("User found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        usuario.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new User(usuario.getUsername(), usuario.getPassword(), authorities);
    }
}
