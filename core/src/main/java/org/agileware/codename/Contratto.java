package org.agileware.codename;

import java.math.BigDecimal;
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
public class Contratto {
	private String id;
	private Soggetto intestatario;
	private Soggetto garante;
	private LocalDate stipulato;
	
	private BigDecimal finanziato;
}
