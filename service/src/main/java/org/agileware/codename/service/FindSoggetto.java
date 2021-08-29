package org.agileware.codename.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

import org.agileware.codename.Azienda;
import org.agileware.codename.Contatto;
import org.agileware.codename.Contatto.Tipo;
import org.agileware.codename.DataLuogo;
import org.agileware.codename.Id;
import org.agileware.codename.Persona;
import org.agileware.codename.Persona.Sesso;
import org.agileware.codename.Soggetto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FindSoggetto {

	@KafkaListener(topics = "subjects.find")
	@SendTo()
	public Soggetto find(Id id) {
		log.info("Payload richiesta: {}", id);
		return Persona.builder()
				.codiceFiscale(id.toString())
				.nome("Roberto")
				.cognome("Lo Giacco")
				.contatti(Arrays.asList(
						Contatto.builder().tipo(Tipo.EMAIL).valore("rlogiacco@agileware.org").build(),
						Contatto.builder().tipo(Tipo.EMAIL).valore("info@agileware.org").build(),
						Contatto.builder().tipo(Tipo.EMAIL).valore("rlogiacco@example.com").build(),
						Contatto.builder().tipo(Tipo.MOBILE).valore("3331234567").build(),
						Contatto.builder().tipo(Tipo.TELEFONO).valore("0612345678").build()
				))
				.nascita(DataLuogo.builder()
						.citta("Roma")
						.provincia("RM")
						.data(LocalDate.of(1975, 1, 15))
						.build())
				.sesso(Sesso.M).build();
	}
}
