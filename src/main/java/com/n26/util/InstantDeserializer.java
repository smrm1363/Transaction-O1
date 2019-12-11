package com.n26.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
/**
 * This is a deserializer for Dates Json
 */
public class InstantDeserializer extends StdDeserializer<LocalDateTime> {

    public InstantDeserializer() {
        this(null);
    }
    public InstantDeserializer(Class<?> vc) {
        super(vc);
    }
    @Override
    public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        Instant instant;
        try {
             instant=Instant.parse(node.asText());
        } catch (Exception e) {
            throw new DeserializeException("Instant field deserialization failed");
        }
        return instant.atZone(ZoneOffset.UTC).toLocalDateTime();
    }
}