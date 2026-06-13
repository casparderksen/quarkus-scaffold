package org.example.shared.infrastructure.adapter.out.persistence.jpa.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.net.URI;

@Converter(autoApply = true)
public final class UriAttributeConverter implements AttributeConverter<URI, String> {

    @Override
    public String convertToDatabaseColumn(URI uri) {
        return (uri == null) ? null : uri.toString();
    }

    @Override
    public URI convertToEntityAttribute(String value) {
        return (value == null) ? null : URI.create(value);
    }
}
