package org.agileware.codename.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.agileware.codename.Contratto;
import org.agileware.codename.DataLuogo;
import org.agileware.codename.Id;
import org.agileware.codename.Persona;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FindContratto {

	@KafkaListener(topics = "contract")
	@SendTo
	public Contratto perform(Id idProposta) {
		log.debug("Payload richiesta: {}", idProposta);
		return Contratto.builder()
				.intestatario(Persona.builder()
						.nome("Roberto")
						.cognome("Lo Giacco")
						.codiceFiscale("LGCRRT11X23X111X")
						.nascita(DataLuogo.builder()
								.citta("Somewhere")
								.provincia("XX")
								.data(LocalDate.of(1975, 2, 18))
								.build())
						.sesso(Persona.Sesso.M)
						.build())
				.finanziato(new BigDecimal("1234.56"))
				.stipulato(LocalDate.now())
				.build();
	}
}
