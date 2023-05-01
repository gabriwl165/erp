package br.com.gab.Sistema.ERP.Model;

import br.com.gab.Sistema.ERP.Generics.Model;
import br.com.gab.Sistema.ERP.Model.enums.FuncaoEnum;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Table(name = "usuario")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends Model implements Serializable, UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "login")
    private String username;

    @NotNull
    @Column(name = "senha")
    private String password;

    @Column(name = "dataNascimento")
    private Date dataNascimento;

    @Column(name = "primeiro_nome")
    private String firstName;

    @Column(name = "ultimo_nome")
    private String lastName;

    @Column(name = "funcao")
    private FuncaoEnum funcao;

    @Column(name = "email")
    private String email;

    @Column(name = "idEndereco")
    private Long idEndereco;

    @OneToOne(fetch = FetchType.LAZY)
    private UsuarioEndereco usuarioEndereco;

    @ManyToMany
    @JoinTable(name = "USUARIO_ROLE", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<RoleModel> roles;

    public Usuario(Long id, String firstName, String username, String password, List<RoleModel> lista){
        this.id =  id;
        this.firstName =  firstName;
        this.username =  username;
        this.password =  password;
        this.roles = lista;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
