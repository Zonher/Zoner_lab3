package ru.nikita.lab2.dao.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.nikita.lab2.api.enumeration.HairColor;

@Converter(autoApply = true)
public class HairColorToDBConverter implements AttributeConverter<HairColor, String> {
    @Override
    public String convertToDatabaseColumn(HairColor hairColor) {
        return hairColor.name().toLowerCase();
    }

    @Override
    public HairColor convertToEntityAttribute(String value) {
        return HairColor.valueOf(value.toUpperCase());
    }
}
