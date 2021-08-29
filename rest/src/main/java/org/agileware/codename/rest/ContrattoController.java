package org.agileware.codename.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.agileware.codename.Contratto;
import org.agileware.codename.Id;
import org.agileware.codename.Persona;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/contracts", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContrattoController {
	
	@Autowired
	private ReplyingKafkaTemplate<String, Id, Contratto> template;

	@GetMapping(value = "/{contractNo}")
	public EntityModel<Contratto> find(@PathVariable Id contractNo) {
		try {
			// Esegui la richiesta entro il tempo limite
			RequestReplyFuture<String, Id, Contratto> future = template.sendAndReceive(new ProducerRecord<>("contract", contractNo), Duration.of(1000, ChronoUnit.SECONDS));
			// Recupera la risposta entro il tempo limite
			ConsumerRecord<String, Contratto> response = future.get(10000, TimeUnit.SECONDS);
			// Converti la risposta
			return toEntityModel(response.value());
		} catch (ExecutionException ee) {
			throw new RuntimeException(ee.getMessage());
		} catch (InterruptedException | TimeoutException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public static EntityModel<Contratto> toEntityModel(Contratto contratto) {
		return EntityModel.of(contratto)
				.addIf(contratto.getIntestatario() != null , () -> linkTo(SoggettoController.class).slash(contratto.getIntestatario().getId()).withRel("intestatario"))
				.add(linkTo(ContrattoController.class).slash(contratto.getId()).withSelfRel());
	}
}
