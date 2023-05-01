package br.com.gab.Sistema.ERP.Service.impl;

import br.com.gab.Sistema.ERP.Exception.UsuarioNotFoundException;
import br.com.gab.Sistema.ERP.Model.Usuario;
import br.com.gab.Sistema.ERP.Model.UsuarioEndereco;
import br.com.gab.Sistema.ERP.Repository.UsuarioEnderecoRepository;
import br.com.gab.Sistema.ERP.Repository.UsuarioRepository;
import br.com.gab.Sistema.ERP.dto.UsuarioEnderecoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class UsuarioEnderecoServiceImplTest {

    @Mock
    private UsuarioEnderecoServiceImpl usuarioEnderecoService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioEnderecoRepository usuarioEnderecoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        usuarioEnderecoService = new UsuarioEnderecoServiceImpl(usuarioRepository, usuarioEnderecoRepository);
    }

    @Test
    void itShouldThrowAnUsuarioNotFoundException() {
        // Given
        UsuarioEnderecoDTO usuarioEnderecoDTO = new UsuarioEnderecoDTO("SP", "Rua Carlos Drummond de Andrade", "413", "Inocoop");
        // When
        long idUsuario = 1L;
        given(usuarioRepository.findById(idUsuario)).willReturn(Optional.empty());
        // Then
        assertThatThrownBy(() -> usuarioEnderecoService.salvar(idUsuario, usuarioEnderecoDTO)).isInstanceOf(UsuarioNotFoundException.class);
    }

    @Test
    void itShouldSaveAddressAtTheCustomer() {
        // Given
        UsuarioEnderecoDTO usuarioEnderecoDTO = new UsuarioEnderecoDTO("SP", "Rua Carlos Drummond de Andrade", "413", "Inocoop");
        long idUsuario = 1L;
        Usuario usuario = new Usuario(idUsuario, "Gab", "eeita", "eeita", new ArrayList<>());
        given(usuarioRepository.findById(idUsuario)).willReturn(Optional.of(usuario));
        // When
        usuarioEnderecoService.salvar(idUsuario, usuarioEnderecoDTO);
        // Then
        ArgumentCaptor<UsuarioEndereco> usuarioEnderecoArgumentCaptor = ArgumentCaptor.forClass(UsuarioEndereco.class);
        ArgumentCaptor<Usuario> usuarioArgumentCaptor = ArgumentCaptor.forClass(Usuario.class);

        then(usuarioEnderecoRepository).should().save(usuarioEnderecoArgumentCaptor.capture());
        then(usuarioRepository).should().save(usuarioArgumentCaptor.capture());

        UsuarioEndereco usuarioEnderecoArgumentCaptorValue = usuarioEnderecoArgumentCaptor.getValue();
        Usuario usuarioArgumentCaptorValue = usuarioArgumentCaptor.getValue();

        assertThat(usuarioEnderecoArgumentCaptorValue).isEqualToComparingFieldByField(usuarioEnderecoDTO.toEntity());
        assertThat(usuarioArgumentCaptorValue).isEqualToComparingFieldByField(usuario);

    }
}