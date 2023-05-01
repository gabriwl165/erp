package br.com.gab.Sistema.ERP.Service.impl;

import br.com.gab.Sistema.ERP.EmailSender.MockSendEmailImpl;
import br.com.gab.Sistema.ERP.Model.Usuario;
import br.com.gab.Sistema.ERP.Repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioServiceImpl usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        usuarioService = new UsuarioServiceImpl(usuarioRepository, new MockSendEmailImpl());
    }

    @Test
    void itShouldResetPasswordAndReturnTrue() {
        // Given
        long idUsuario = 1L;
        Usuario usuario = new Usuario(idUsuario, "Gab", "eeita", "eeita", new ArrayList<>());
        usuario.setEmail("gabrielmarcelino165@gmail.com");
        given(usuarioRepository.buscarPorID(idUsuario)).willReturn(usuario);
        // When
        // Then
        assertThat(usuarioService.resetarSenha(idUsuario)).isTrue();
        then(usuarioRepository).should().save(any(Usuario.class));
    }

    @Test
    void itShouldReturnFalseWhenTryToResetPassword() {
        // Given
        long idUsuario = 1L;
        given(usuarioRepository.buscarPorID(idUsuario)).willReturn(null);
        // When
        // Then
        assertThat(usuarioService.resetarSenha(idUsuario)).isFalse();

    }
}