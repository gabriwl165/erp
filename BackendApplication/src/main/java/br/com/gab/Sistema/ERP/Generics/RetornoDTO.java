package br.com.gab.Sistema.ERP.Generics;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RetornoDTO {

    private String message;
    private Object object;
    private Boolean success;

}
