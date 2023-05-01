package br.com.gab.Sistema.ERP.Model;

import br.com.gab.Sistema.ERP.Generics.Model;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "usuario_endereco")
@Entity
@Getter
@Setter
public class UsuarioEndereco extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "siglaUF")
    private String siglaUF;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "numero")
    private String numero;

    @Column(name = "bairro")
    private String bairro;

}
