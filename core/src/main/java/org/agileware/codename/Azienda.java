package org.agileware.codename;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Builder;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

@Builder
@With
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class Azienda implements Soggetto {
	public enum Tipo { SASS, SRL, SPA }
	
	@JsonView(Full.class)
	private String ragioneSociale;
	@JsonView(Full.class)
	private String partitaIva;
	@JsonView(Full.class)
	private Tipo tipo;
	@JsonView(Full.class)
	private Persona legaleRappresentante;
	private List<Contatto> contatti;
	
	@JsonView(Summary.class)
	public String getId() {
		return partitaIva;
	}
	
	public String getDisplay() {
		return ragioneSociale;
	}

	@Override
	public List<Contatto> getContatti() {
		return contatti;
	}
}
