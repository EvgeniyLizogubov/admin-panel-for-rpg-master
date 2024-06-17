package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Configuration
@Slf4j
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        
        Converter<Long, LocalDate> longToLocalDate = context -> context.getSource() == null
                ? null
                : Instant.ofEpochMilli(context.getSource()).atZone(ZoneId.systemDefault()).toLocalDate();
        
        Converter<LocalDate, Long> localDateToLong = context -> context.getSource() == null
                ? null
                : context.getSource().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        
        mapper.addConverter(longToLocalDate, Long.class, LocalDate.class);
        mapper.addConverter(localDateToLong, LocalDate.class, Long.class);
        return mapper;
    }
}
