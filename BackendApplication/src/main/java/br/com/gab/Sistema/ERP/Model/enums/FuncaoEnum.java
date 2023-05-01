package br.com.gab.Sistema.ERP.Model.enums;

public enum FuncaoEnum {

    RECEPCAO, GESTOR, MODERADOR;

    public static FuncaoEnum getEnum(String tipo){
        if(tipo.equalsIgnoreCase("RECEPCAO")) return FuncaoEnum.RECEPCAO;
        else if(tipo.equalsIgnoreCase("GESTOR")) return FuncaoEnum.GESTOR;
        else return FuncaoEnum.MODERADOR;
    }

}
