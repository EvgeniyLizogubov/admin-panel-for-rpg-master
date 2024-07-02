package com.example.demo.util;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class LongToLocalDateConverter extends StdConverter<String, LocalDate> {
    @Override
    public LocalDate convert(String s) {
        return Instant.ofEpochMilli(Long.parseLong(s)).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
