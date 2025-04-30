package com.BugAndResolution.BugAndResolution.converter;

import com.BugAndResolution.BugAndResolution.util.Encryptor;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EncryptionConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return attribute == null ? null : Encryptor.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData == null ? null : Encryptor.decrypt(dbData);
    }
}
