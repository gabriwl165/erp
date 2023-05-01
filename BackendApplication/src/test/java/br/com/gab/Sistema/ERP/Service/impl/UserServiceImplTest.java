package br.com.gab.Sistema.ERP.Service.impl;

import br.com.gab.Sistema.ERP.Exception.RoleNotFoundException;
import br.com.gab.Sistema.ERP.Exception.UsuarioNotFoundException;
import br.com.gab.Sistema.ERP.Model.RoleModel;
import br.com.gab.Sistema.ERP.Model.Usuario;
import br.com.gab.Sistema.ERP.Repository.RoleModelRepository;
import br.com.gab.Sistema.ERP.Repository.UsuarioRepository;
import br.com.gab.Sistema.ERP.utils.PasswordEncoderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserServiceImpl userService;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private RoleModelRepository roleModelRepository;

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(usuarioRepository, roleModelRepository, new PasswordEncoderImpl());
    }

    @Test
    void itShouldSaveNewUser() {
        // Given
        Usuario usuario = new Usuario(1L, "Gab", "gab_bora", "eita", new ArrayList<>());
        // When
        ArgumentCaptor<Usuario> usuarioArgumentCaptor = ArgumentCaptor.forClass(Usuario.class);
        // Then
        userService.saveUser(usuario);
        then(usuarioRepository).should().save(usuarioArgumentCaptor.capture());
        Usuario argumentCaptorValue = usuarioArgumentCaptor.getValue();
        assertThat(argumentCaptorValue).as("It should has save a new user").isEqualTo(usuario);
    }

    @Test
    void itShouldReturnUserByTheirUsername() {
        // Given
        String userUsername = "gab";
        Usuario usuario = new Usuario(1L, "Gab", userUsername, "eita", new ArrayList<>());
        usuarioRepository.save(usuario);
        // When
        given(usuarioRepository.findByUsername(userUsername)).willReturn(usuario);
        Usuario user = userService.getUser(userUsername);
        // Then
        assertThat(user).isEqualTo(usuario);
    }

    @Test
    void itShouldGetAllUsers() {
        // Given
        List<Usuario> listaUsuarios = new ArrayList<>();
        Usuario usuario1 = new Usuario(1L, "Gab", "eita", "eita", new ArrayList<>());
        listaUsuarios.add(usuario1);
        Usuario usuario2 = new Usuario(2L, "Gabb", "bbora", "eeita", new ArrayList<>());
        listaUsuarios.add(usuario2);
        Usuario usuario3 = new Usuario(3L, "Gaab", "qqueisso", "eiita", new ArrayList<>());
        listaUsuarios.add(usuario3);
        // When
        // Then
        given(usuarioRepository.findAll()).willReturn(Arrays.asList(usuario1, usuario2, usuario3));

        List<Usuario> users = userService.getUsers();
        Integer pos = 0;
        assertEquals(listaUsuarios, users);
    }

    @Test
    void itShouldAddRoleToCustomer() {
        // Given
        String username = "eeiita";
        Usuario usuario = new Usuario(1L, "gaaab", username, "senha", new ArrayList<>());
        String roleName = "ROLE_ADMIN";
        RoleModel roleModel = new RoleModel(15L, roleName);

        given(usuarioRepository.findByUsername(username)).willReturn(usuario);
        given(roleModelRepository.findByName(roleName)).willReturn(roleModel);

        ArgumentCaptor<Usuario> usuarioArgumentCaptor = ArgumentCaptor.forClass(Usuario.class);

        // When
        userService.addRoleToUser(username, roleName);

        // Then
        then(usuarioRepository).should().save(usuarioArgumentCaptor.capture());
        Usuario usuarioArgumentCaptorValue = usuarioArgumentCaptor.getValue();

        usuario.getRoles().add(roleModel);

        assertThat(usuarioArgumentCaptorValue).isEqualTo(usuario);
    }

    @Test
    void itShouldThrowUsuarioNotFoundWhenAddRoleToUser() {
        // Given
        String username = "eeiita";
        given(usuarioRepository.findByUsername(username)).willReturn(null);

        // When
        // Then
        Throwable throwable = catchThrowable(() -> userService.addRoleToUser(username, "ROLE_ADMIN"));
        assertThat(throwable).isInstanceOf(UsuarioNotFoundException.class);
        // TWO WAY TO DO SAME THING
        assertThatThrownBy(() -> userService.addRoleToUser(username, "ROLE_ADMIN")).isInstanceOf(UsuarioNotFoundException.class);

    }

    @Test
    void itShouldThrowRoleNotFoundWhenAddRoleToUser() {
        // Given
        String username = "eeiita";
        Usuario usuario = new Usuario(1L, "gaaab", username, "senha", new ArrayList<>());
        given(usuarioRepository.findByUsername(username)).willReturn(usuario);
        String roleName = "ROLE_ADMIN";
        given(roleModelRepository.findByName(roleName)).willReturn(null);

        // When
        // Then
        assertThatThrownBy(() -> userService.addRoleToUser(username, roleName)).isInstanceOf(RoleNotFoundException.class);

    }

    @Test
    void itShouldSaveNewRoleModel() {
        // Given
        RoleModel roleModel = new RoleModel(20L, "ROLE_CUSTOMER");
        // When
        ArgumentCaptor<RoleModel> roleModelArgumentCaptor = ArgumentCaptor.forClass(RoleModel.class);
        // Then
        userService.saveRole(roleModel);
        then(roleModelRepository).should().save(roleModelArgumentCaptor.capture());
        RoleModel roleModelArgumentCaptorValue = roleModelArgumentCaptor.getValue();
        assertThat(roleModelArgumentCaptorValue).isEqualTo(roleModel);
    }

    @Test
    void itShouldThrowUsernameNotFoundWhenLoadByUsername() {
        // Given
        String username = "eita";
        given(usuarioRepository.findByUsername(username)).willReturn(null);
        // When
        // Then
        assertThatThrownBy(() -> userService.loadUserByUsername(username))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("Não achamos nada aqui fio");
    }

    @Test
    void itShouldReturnUserDetailsWhenLoadUserByUsername() {
        // Given
        String username = "gab";
        Usuario usuario = new Usuario(1L, "gaaab", username, "senha", new ArrayList<>());
        RoleModel roleModel = new RoleModel(20L, "ROLE_CUSTOMER");
        usuario.getRoles().add(roleModel);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        usuario.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        given(usuarioRepository.findByUsername(username)).willReturn(usuario);
        // When
        UserDetails userDetails = userService.loadUserByUsername(username);
        // Then
        assertThat(userDetails).isEqualTo(new User(usuario.getUsername(), usuario.getPassword(), authorities));
    }
}
   /*

   */
