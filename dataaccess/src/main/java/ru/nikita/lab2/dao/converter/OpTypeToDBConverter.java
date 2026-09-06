package ru.nikita.lab2.dao.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.nikita.lab2.api.enumeration.OpType;

@Converter(autoApply = true)
public class OpTypeToDBConverter implements AttributeConverter<OpType, String> {
    @Override
    public String convertToDatabaseColumn(OpType opType) {
        return opType.name().toLowerCase();
    }

    @Override
    public OpType convertToEntityAttribute(String value) {
        return OpType.valueOf(value.toUpperCase());
    }
}
