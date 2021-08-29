package org.agileware.codename.helper;

import org.agileware.codename.Id;
import org.springframework.core.convert.converter.Converter;

public class IdConverter implements Converter<String, Id>{

	@Override
	public Id convert(String source) {
		return Id.builder().value(source).build();
	}

}
