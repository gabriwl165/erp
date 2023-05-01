package br.com.gab.Sistema.ERP.utils;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DateUtilsTest {

    @Test
    void itShouldReturnDateFormatted() throws ParseException {
        // Given
        String data = "30/08/2002";
        // When
        Date dataFormatada = DateUtils.dd_MM_YYYY_ToDate(data);
        // Then
        assertThat(dataFormatada.compareTo(new Date(1030676400000L))).isEqualTo(0);
    }

    @Test
    void itShouldThrowAnIllegalStateException() {
        // Given
        String data = "30-08-2002";
        // When
        // Then
        assertThatThrownBy(() -> DateUtils.dd_MM_YYYY_ToDate(data)).isInstanceOf(IllegalStateException.class);
    }
}