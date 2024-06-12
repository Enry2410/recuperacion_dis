package org.vaadin.example;

import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EntityService implements Serializable {

    private final List<DataModel> entities = new ArrayList<>();

    public EntityService() {
        // Initial data
        entities.add(new DataModel("MS1", "2022", "E1", 10.5f, 1.2f, 8.1f, 12.9f, "Flag1", UUID.randomUUID()));
        entities.add(new DataModel("MS2", "2023", "E2", 11.2f, 1.4f, 9.0f, 13.4f, "Flag2", UUID.randomUUID()));
        entities.add(new DataModel("MS1", "2024", "E3", 9.8f, 1.1f, 7.6f, 12.0f, "Flag3", UUID.randomUUID()));
    }

    public List<DataModel> findAll() {
        return new ArrayList<>(entities);
    }

    public Optional<DataModel> findById(UUID id) {
        return entities.stream().filter(entity -> entity.get_id().equals(id)).findFirst();
    }

    public void save(DataModel entity) {
        findById(entity.get_id()).ifPresent(entities::remove);
        entities.add(entity);
    }

    public void delete(DataModel entity) {
        entities.remove(entity);
    }
}
