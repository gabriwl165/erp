package br.com.gab.Sistema.ERP.Model;

import br.com.gab.Sistema.ERP.Model.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "ROLES")
@AllArgsConstructor
@NoArgsConstructor
public class RoleModel implements Serializable, GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleId;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false, unique = true)
//    private RoleName roleName;

    private String name;

    @Override
    public String getAuthority() {
        return this.name;
    }
}
