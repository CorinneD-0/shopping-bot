package com.beautybot.repository;

import com.beautybot.domain.product.ProductComponent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.File;
import java.util.List;

public class JsonProductRepository implements ProductRepository {
    private final File file;
    private final ObjectMapper mapper;

    public JsonProductRepository(String filename) {
        this.file = new File(filename);
        // Abilitiamo supporto base per il polymorphic
        BasicPolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator
            .builder()
            .allowIfSubType("com.beautybot.domain.product")
            .build();
        this.mapper = new ObjectMapper()
            .activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL);
    }

    @Override
    public List<ProductComponent> load() {
        try {
            if (!file.exists()) {
                return List.of();
            }
            CollectionType type = mapper.getTypeFactory()
                .constructCollectionType(List.class, ProductComponent.class);
            return mapper.readValue(file, type);
        } catch (Exception e) {
            throw new RuntimeException("Errore caricamento JSON", e);
        }
    }

    @Override
    public void save(List<ProductComponent> items) {
        try {
            mapper.writerWithDefaultPrettyPrinter()
                  .writeValue(file, items);
        } catch (Exception e) {
            throw new RuntimeException("Errore salvataggio JSON", e);
        }
    }
}
