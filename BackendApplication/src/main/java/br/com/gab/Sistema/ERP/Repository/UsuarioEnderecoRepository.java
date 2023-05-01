package br.com.gab.Sistema.ERP.Repository;

import br.com.gab.Sistema.ERP.Model.UsuarioEndereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UsuarioEnderecoRepository extends JpaRepository<UsuarioEndereco, Long> {
}
