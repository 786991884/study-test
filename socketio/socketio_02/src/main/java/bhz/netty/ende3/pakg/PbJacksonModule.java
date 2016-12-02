package bhz.netty.ende3.pakg;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Module for handling json structure as it defined in spec
 */
public class PbJacksonModule extends Module {

    private static final Version VERSION = new Version(1, 0, 0, null, null, null);
    private static final String KEY_VALUE = "_v";
    private static final String KEY_CLASS = "_c";

    @Override
    public String getModuleName() {
        return "ctipModule";
    }

    @Override
    public Version version() {
        return VERSION;
    }

    @Override
    public void setupModule(SetupContext context) {
        context.addBeanDeserializerModifier(new BeanDeserializerModifier() {
            @Override
            public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
                return wrap(deserializer);
            }

            @Override
            public JsonDeserializer<?> modifyArrayDeserializer(DeserializationConfig config, ArrayType valueType, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
                return wrap(deserializer);
            }

            @Override
            public JsonDeserializer<?> modifyCollectionDeserializer(DeserializationConfig config, CollectionType type, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
                return wrap(deserializer);
            }

            @Override
            public JsonDeserializer<?> modifyCollectionLikeDeserializer(DeserializationConfig config, CollectionLikeType type, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
                return wrap(deserializer);
            }

            /*
                        @Override
                        public JsonDeserializer<?> modifyMapDeserializer(DeserializationConfig config, MapType type, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
                            return wrap(deserializer);
                        }

                        @Override
                        public JsonDeserializer<?> modifyMapLikeDeserializer(DeserializationConfig config, MapLikeType type, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
                            return wrap(deserializer);
                        }
            */
            private JsonDeserializer<?> wrap(JsonDeserializer<?> deserializer) {
                return CtipJsonDeserializer.create((JsonDeserializer<Object>) deserializer);
            }
        });

        context.addBeanSerializerModifier(new BeanSerializerModifier() {
            @Override
            public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription beanDesc, JsonSerializer<?> serializer) {
                return wrap(serializer);
            }

            @Override
            public JsonSerializer<?> modifyCollectionSerializer(SerializationConfig config, CollectionType valueType, BeanDescription beanDesc, JsonSerializer<?> serializer) {
                return wrap(serializer);
            }

            @Override
            public JsonSerializer<?> modifyMapSerializer(SerializationConfig config, MapType valueType, BeanDescription beanDesc, JsonSerializer<?> serializer) {
                return wrap(serializer);
            }

            private CtipJsonSerializer wrap(JsonSerializer<?> serializer) {
                return new CtipJsonSerializer((JsonSerializer) serializer);
            }
        });
    }

    private static class CtipJsonDeserializer extends JsonDeserializer<Object> implements ContextualDeserializer {

        private final JsonDeserializer<Object> deserializer;

        private CtipJsonDeserializer(JsonDeserializer<Object> deserializer) {
            this.deserializer = deserializer;
        }

        public static CtipJsonDeserializer create(JsonDeserializer<Object> deserializer) {
            return new CtipJsonDeserializer(deserializer);
        }

        @Override
        public JsonDeserializer<?> getDelegatee() {
            return this.deserializer;
        }

        @Override
        public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            if (p.getParsingContext().getParent().inRoot()) {
                return deserializer.deserialize(p, ctxt);
            }
            Object value = null;
            PbDataType<?> byId = null;
            while (p.getCurrentToken() != JsonToken.END_OBJECT) {
                final JsonToken fieldToken = p.nextToken();
                if (fieldToken != JsonToken.FIELD_NAME) {
                    continue;
                }
                String fieldName = p.getCurrentName();
                if (KEY_CLASS.equals(fieldName)) {
                    final int ctypeId = Integer.parseInt(p.nextTextValue());
                    byId = PbDataTypes.getById(ctypeId);
                } else if (KEY_VALUE.equals(fieldName)) {
                    // we need move iterator to value token
                    JsonToken jsonToken = p.nextToken();
                    if (jsonToken == JsonToken.VALUE_NULL) {
                        // simply null
                    } else if (jsonToken == JsonToken.START_ARRAY) {
                        value = ctxt.readValue(p, ArrayList.class);
                    } else if (jsonToken == JsonToken.START_OBJECT) {
                        value = ctxt.readValue(p, HashMap.class);
                    } else {
                        value = p.getText();
                    }
                } else {
                    throw new JsonParseException("Unexpected fieldName value:" + fieldName, p.getCurrentLocation());
                }
            }
            if (value instanceof String) {
                StringAdapter<?> adapter = byId.getAdapter();
                value = adapter.fromString((String) value);
            }
            return value;
        }

        @Override
        public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
            if (!(this.deserializer instanceof ContextualDeserializer)) {
                return this;
            }
            JsonDeserializer<?> delegateContextual = ((ContextualDeserializer) this.deserializer).createContextual(ctxt, property);
            if (delegateContextual == this.deserializer) {
                return this;
            }
            return new CtipJsonDeserializer((JsonDeserializer) delegateContextual);
        }
    }

    /**
     * Serializer do wrapping around objects into contaier with {@link #KEY_CLASS } and {@link #KEY_VALUE} fields,
     * also we do not wrap root values.
     */
    private static class CtipJsonSerializer extends JsonSerializer<Object> implements ContextualSerializer {

        private final JsonSerializer<Object> serializer;

        CtipJsonSerializer(JsonSerializer<Object> serializer) {
            this.serializer = serializer;
        }

        @Override
        public JsonSerializer<?> getDelegatee() {
            return serializer;
        }

        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            PbDataType<Object> dataType = PbDataTypes.getByValue(value);
            final int id = dataType.getId();
            final boolean root = gen.getOutputContext().inRoot();
            if (!root) {
                gen.writeStartObject();
                gen.writeStringField(KEY_CLASS, Integer.toString(id));
                gen.writeFieldName(KEY_VALUE);
            }

            // we cannon invoke gen.writeObject() because it will causes infinite recursion
            if (id == PbDataTypes.TYPE_MAP || id == PbDataTypes.TYPE_LIST || id == PbDataTypes.TYPE_OBJECT) {
                serializer.serialize(value, gen, serializers);
            } else {
                StringAdapter<Object> adapter = dataType.getAdapter();
                StringBuilder sb = new StringBuilder();
                adapter.toString(sb, value);
                gen.writeString(sb.toString());
            }
            if (!root) {
                gen.writeEndObject();
            }
        }

        @Override
        public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
            if (!(this.serializer instanceof ContextualSerializer)) {
                return this;
            }
            JsonSerializer<?> delegateContextual = ((ContextualSerializer) this.serializer).createContextual(prov, property);
            if (delegateContextual == this.serializer) {
                return this;
            }
            return new CtipJsonSerializer((JsonSerializer) delegateContextual);
        }
    }
}
