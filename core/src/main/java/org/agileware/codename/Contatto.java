package org.agileware.codename;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@With
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contatto {

	private Tipo tipo;
	private String valore;
	
	public enum Tipo {
		EMAIL, TELEFONO, FAX, MOBILE;
	}
}
