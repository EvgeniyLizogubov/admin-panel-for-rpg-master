package com.example.demo.util.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class LongToLocalDateConverter extends StdConverter<String, LocalDate> implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String value) {
        return Instant.ofEpochMilli(Long.parseLong(value)).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
