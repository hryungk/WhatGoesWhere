package org.perscholas.whatgoeswhere.models;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Converter from BestOption enum to String for JPA persisting
 */
@Converter(autoApply=true)
public class BestOptionConverter implements AttributeConverter<BestOption, String> {

	@Override
	public String convertToDatabaseColumn(BestOption bestOption) {
		if(bestOption == null)
			return null;
		return bestOption.getValue();
	}

	@Override
	public BestOption convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		
		return Stream.of(BestOption.values())
				.filter(c -> c.getValue().equals(value))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}

}
