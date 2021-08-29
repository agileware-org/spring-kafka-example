package org.agileware.codename.service;

import java.util.UUID;

import org.agileware.codename.Azienda;
import org.agileware.codename.Persona;
import org.agileware.codename.Soggetto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AddSoggetto {

	
	@KafkaListener(topics = "subjects.add")
	@SendTo()
	public Soggetto add(Soggetto soggetto) {
		log.info("Payload richiesta: {}", soggetto);
		if (soggetto instanceof Persona) {
			return ((Persona)soggetto).withCodiceFiscale(UUID.randomUUID().toString());
		} else {
			return ((Azienda)soggetto).withPartitaIva(UUID.randomUUID().toString());
		}
	}
}
