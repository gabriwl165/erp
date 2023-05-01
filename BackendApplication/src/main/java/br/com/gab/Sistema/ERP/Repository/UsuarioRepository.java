package br.com.gab.Sistema.ERP.Repository;

import br.com.gab.Sistema.ERP.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    @Query("select u from Usuario u where u.username = :login and u.password = :senha")
    public Usuario buscarUsuario(String login, String senha);

    @Query("select u from Usuario u where u.username = :login")
    public Usuario existeUsuarioComLoginMesmoLogin(String login);

    @Query("select u from Usuario u where u.id = :id")
    public Usuario buscarPorID(Long id);

    Usuario findByUsername(String username);

//    @Query("select u from Usuario u where u.username = :username")
//    Optional<Usuario> findByUsername(String username);

}
