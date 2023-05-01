package br.com.gab.Sistema.ERP.Generics;

import java.text.ParseException;

public abstract class DTO<T> {

    public abstract Object toDTO(T t);
    public abstract T toEntity() throws ParseException;

    public abstract T getModel();

}
