package ru.nikita.lab2.dao.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.nikita.lab2.api.enumeration.Gender;

@Converter(autoApply = true)
public class GenderToDBConverter implements AttributeConverter<Gender, String> {
    @Override
    public String convertToDatabaseColumn(Gender gender) {
        return gender.name().toLowerCase();
    }

    @Override
    public Gender convertToEntityAttribute(String value) {
        return Gender.valueOf(value.toUpperCase());
    }
}
