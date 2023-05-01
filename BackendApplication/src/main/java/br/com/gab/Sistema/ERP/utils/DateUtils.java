package br.com.gab.Sistema.ERP.utils;

import br.com.gab.Sistema.ERP.Exception.DateUtilsConverterException;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class DateUtils {

    private final static String dd_MM_YYYY = "^\\d{2}/\\d{2}/\\d{4}$";
    private final static String YYYY_MM_dd = "^\\d{4}-\\d{2}-\\d{2}$";

    private static String pattern = "yyyy/MM/dd";

    public static Date dd_MM_YYYY_ToDate(String date) throws ParseException {
        if(date.matches(dd_MM_YYYY)) {
            String[] split = date.split("/");
            String dia = split[0];
            String mes = split[1];
            String ano = split[2];
            String dataFormatada = ano + "/" + mes + "/" + dia;

            return new SimpleDateFormat(pattern).parse(dataFormatada);
        }else{
            throw new DateUtilsConverterException("Failed to convert dd_MM_YYYY ");
        }
    }

    public static Date YYYY_MM_dd_ToDate(String date) throws ParseException {
        if(date.matches(YYYY_MM_dd)){
            String[] split = date.split("-");
            String ano = split[0];
            String mes = split[1];
            String dia = split[2];
            String dataFormatada = String.format("%s/%s/%s", ano, mes, dia);
            return new SimpleDateFormat(pattern).parse(dataFormatada);
        } else {
            throw new DateUtilsConverterException("Failed to convert YYYY_MM_dd ");
        }
    }

}
