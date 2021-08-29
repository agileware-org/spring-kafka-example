package org.agileware.codename.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.agileware.codename.Azienda;
import org.agileware.codename.Id;
import org.agileware.codename.Persona;
import org.agileware.codename.Soggetto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/subjects", produces = MediaType.APPLICATION_JSON_VALUE)
public class SoggettoController {
	@Autowired
	private ReplyingKafkaTemplate<String, Id, Soggetto> find;
	
	@Autowired
	private ReplyingKafkaTemplate<String, Soggetto, Soggetto> add;
	
	@GetMapping(value = "/{subjectId}")
	public EntityModel<Soggetto> find(@PathVariable Id subjectId) {
		try {
			RequestReplyFuture<String, Id, Soggetto> future = find.sendAndReceive(new ProducerRecord<>("subjects.find", subjectId), Duration.of(3, ChronoUnit.SECONDS));
			ConsumerRecord<String, Soggetto> response = future.get(3, TimeUnit.SECONDS);
			return toEntityModel(response.value());
		} catch (ExecutionException ee) {
			throw new RuntimeException(ee.getMessage());
		} catch (InterruptedException | TimeoutException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public EntityModel<Soggetto> add(@RequestBody Soggetto subject) {
		try {
			// clears id
			if (subject instanceof Persona) {
				subject = ((Persona)subject).withCodiceFiscale(null);
			} else {
				subject = ((Azienda)subject).withPartitaIva(null);				
			}
			RequestReplyFuture<String, Soggetto, Soggetto> future = add.sendAndReceive(new ProducerRecord<>("subjects.add", (Soggetto)subject), Duration.of(3, ChronoUnit.SECONDS));
			ConsumerRecord<String, Soggetto> response = future.get(3, TimeUnit.SECONDS);
			return toEntityModel(response.value());
		} catch (ExecutionException ee) {
			throw new RuntimeException(ee.getMessage());
		} catch (InterruptedException | TimeoutException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static EntityModel<Soggetto> toEntityModel(Soggetto soggetto) {
		return EntityModel.of(soggetto).add(linkTo(SoggettoController.class).slash(soggetto.getId()).withSelfRel());
	}
}
