package org.vaadin.example;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.*;

@Service
public class EntityService {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:2223/api/data";

    public EntityService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<DataModel> findAll() {
        DataModel[] data = restTemplate.getForObject(baseUrl, DataModel[].class);
        return Arrays.asList(data);
    }

    public Optional<DataModel> findById(UUID id) {
        DataModel data = restTemplate.getForObject(baseUrl + "/" + id, DataModel.class);
        return Optional.ofNullable(data);
    }

    public void save(DataModel entity) {
        if (entity.get_id() == null) {
            restTemplate.postForObject(baseUrl, entity, String.class);
        } else {
            restTemplate.put(baseUrl + "/" + entity.get_id(), entity);
        }
    }

    public void delete(DataModel entity) {
        restTemplate.delete(baseUrl + "/" + entity.get_id());
    }
}