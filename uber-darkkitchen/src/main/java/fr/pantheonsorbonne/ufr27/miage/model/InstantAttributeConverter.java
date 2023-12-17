package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.sql.Timestamp;
import java.time.Instant;

@Converter(autoApply = true)
public class InstantAttributeConverter implements AttributeConverter<Instant, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(Instant instant) {
        if (instant == null)
            return null;
        else {
            Timestamp t = Timestamp.from(instant);
            return t;
        }
    }

    @Override
    public Instant convertToEntityAttribute(Timestamp timestamp) {
        //System.out.println(timestamp + " " + timestamp.toInstant() + " " + timestamp.getTimezoneOffset());
        return (timestamp == null ? null : timestamp.toInstant());
    }

}