package br.com.gab.Sistema.ERP.Service;

import br.com.gab.Sistema.ERP.Model.RoleModel;
import br.com.gab.Sistema.ERP.Model.Usuario;

import java.util.List;

public interface UserService {

    Usuario saveUser(Usuario usuario);
    RoleModel saveRole(RoleModel role);
    void addRoleToUser(String username, String roleName);

    Usuario getUser(String username);

    List<Usuario> getUsers();

}
