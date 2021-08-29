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
public class Persona implements Soggetto {

	public enum Sesso {
		M, F, ND
	}

	@JsonView(Summary.class)
	private String nome;
	@JsonView(Summary.class)
	private String cognome;
	@JsonView(Full.class)
	private DataLuogo nascita;
	@JsonView(Full.class)
	private Sesso sesso;
	@JsonView(Full.class)
	private String codiceFiscale;
	@JsonView(Full.class)
	private List<Contatto> contatti;

	@JsonView(Summary.class)
	public String getId() {
		return codiceFiscale;
	}

	@JsonView(Summary.class)
	public String getDisplay() {
		return nome + " " + cognome;
	}

	@Override
	public List<Contatto> getContatti() {
		return contatti;
	}
}
