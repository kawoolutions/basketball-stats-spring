package io.kawoolutions.bbstats.entity.converter;

import static io.kawoolutions.bbstats.entity.ContactType.ARENA;
import static io.kawoolutions.bbstats.entity.ContactType.CLUB;
import static io.kawoolutions.bbstats.entity.ContactType.PERSON;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import io.kawoolutions.bbstats.entity.ContactType;

@Converter(autoApply = false)
public class ContactTypeConverter implements AttributeConverter<ContactType, String> {
    @Override
    public String convertToDatabaseColumn(ContactType contactType) {
//        System.out.println( "convertToEntityAttribute(" + contactType + ")" );

        switch (contactType) {
            case PERSON:
                return "P";

            case CLUB:
                return "C";

            case ARENA:
                return "A";

            default:
                throw new IllegalArgumentException("Unknown enum value: " + contactType);
        }
    }

    @Override
    public ContactType convertToEntityAttribute(String value) {
//        System.out.println( "convertToEntityAttribute(\"" + value + "\")" );

        switch (value) {
            case "P":
                return PERSON;

            case "C":
                return CLUB;

            case "A":
                return ARENA;

            default:
                throw new IllegalArgumentException("Unknown DB value: " + value);
        }
    }
}
