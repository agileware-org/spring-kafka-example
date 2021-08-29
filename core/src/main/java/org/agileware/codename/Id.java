package org.agileware.codename;

import java.io.IOException;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@AllArgsConstructor
@Jacksonized
//@JsonDeserialize(using = Id.Deserializer.class)
//@JsonSerialize(using = Id.Serializer.class)
public class Id {
	@NotNull
	private String value;
	
	public String toString() {
		return value;
	}
	
	public static class Deserializer extends JsonDeserializer<Id> {

		@Override
		public Id deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			return null;
		}
		
	}
	
	public static class Serializer extends JsonSerializer<Id> {

		@Override
		public void serialize(Id value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			gen.writeString(value.getValue());
		}
		
	}
}
