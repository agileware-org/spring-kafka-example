package org.agileware.codename.rest;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.agileware.codename.Contratto;
import org.agileware.codename.rest.ContrattoController;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.connect.json.JsonSerializer;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.concurrent.SettableListenableFuture;

@WebMvcTest(ContrattoController.class)
class ContrattoControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Mock
	SendResult<Object, Object> response;
	
	@MockBean
	KafkaTemplate<Object, Object> template;
	
	@Autowired
	@InjectMocks
	private ContrattoController controller;
	
	@Test
	void test() throws Exception {
		SettableListenableFuture<SendResult<Object, Object>> future = new SettableListenableFuture<>();
		when(response.getProducerRecord()).thenReturn(new ProducerRecord<Object, Object>("findContrattoCredito", Contratto.builder().build()));
		future.set(response);
		when(template.send(eq("findContrattoCredito"), anyString())).thenReturn(future);
		mvc.perform(get("/contracts/IT55I0301503200000000123456"))
			.andDo(print()) //log the rest call and content
			.andExpect(status().isOk());
	}
}
