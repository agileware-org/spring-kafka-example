package org.agileware.codename;

import java.io.IOException;
import java.util.List;

import org.agileware.codename.Soggetto.Summary;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ObjectNode;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value=Persona.class, name = "persona"),
  @JsonSubTypes.Type(value=Azienda.class, name = "azienda")
})
//@JsonDeserialize(using = Soggetto.Deserializer.class)
public interface Soggetto {
	@JsonView(Summary.class)
	String getId();
	@JsonView(Summary.class)
	String getDisplay();
	@JsonView(Full.class)
	List<Contatto> getContatti();
	
//	public static class Deserializer extends JsonDeserializer<Soggetto> {
//
//		@Override
//		public Soggetto deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
//			ObjectMapper mapper = (ObjectMapper) p.getCodec();
//			ObjectNode root = mapper.readTree(p);
//			if (root.has("@type") && root.get("@type").asText().equals("persona")) {
//				return mapper.readValue(root.toString(), Persona.class);
//			}
//			return mapper.readValue(root.toString(), Azienda.class);
//		}
//	}
	public static interface Summary {
	}
	
	public static interface Full {
	}
}
