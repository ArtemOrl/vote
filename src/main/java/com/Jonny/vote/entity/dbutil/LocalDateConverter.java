package com.Jonny.vote.entity.dbutil;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;


@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {
    @Override
    public Date convertToDatabaseColumn(LocalDate ld) {
        return Date.valueOf(ld);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date date) {
        return date.toLocalDate();
    }
}
