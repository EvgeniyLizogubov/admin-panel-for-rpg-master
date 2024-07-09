package com.example.demo.util.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.ZoneId;

public class LocalDateToLongConverter extends StdConverter<LocalDate, String> implements Converter<LocalDate, String> {
    @Override
    public String convert(LocalDate value) {
        return String.valueOf(value.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }
}
