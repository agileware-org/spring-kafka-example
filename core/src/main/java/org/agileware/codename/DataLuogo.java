package org.agileware.codename;

import java.time.LocalDate;

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
public class DataLuogo {
	private LocalDate data;
	private String citta;
	private String provincia;
	private String indirizzo;
	private String numeroCivico;
}
