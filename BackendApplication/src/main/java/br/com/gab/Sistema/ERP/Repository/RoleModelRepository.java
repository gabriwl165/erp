package br.com.gab.Sistema.ERP.Repository;

import br.com.gab.Sistema.ERP.Model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleModelRepository extends JpaRepository<RoleModel, Long> {
    RoleModel findByName(String roleName);
}
