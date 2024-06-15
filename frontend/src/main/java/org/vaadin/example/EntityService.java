package org.vaadin.example;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;

@Service
public class EntityService {

    private static final Logger logger = LoggerFactory.getLogger(EntityService.class);
    private final RestTemplate restTemplate;                                                //RestTemplate for HTTP Requests
    private final String baseUrl = "http://localhost:2223/api/data";                        //URL for unsorted JSON
    private final String mscodeUrl = "http://localhost:2223/api/data/readonly";             //URL for sorted JSON

    public EntityService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Method to return data from the unsorted JSON
    public List<DataModel> findAll() {
        DataModel[] data = restTemplate.getForObject(baseUrl, DataModel[].class);
        return Arrays.asList(data);
    }

    //Method to return data from the sorted JSON
    public List<DataModel> findMs() {
        DataModel[] data = restTemplate.getForObject(mscodeUrl, DataModel[].class);
        return Arrays.asList(data);
    }

    //Method to retrieve data from a specific entry by its ID (Unused)
    public Optional<DataModel> findById(UUID id) {
        DataModel data = restTemplate.getForObject(baseUrl + "/" + id, DataModel.class);
        return Optional.ofNullable(data);
    }

    //Method to save new data via POST request to the unsorted JSON
    public void save(DataModel entity){
        entity.set_id(UUID.randomUUID());
        String response = restTemplate.postForObject(baseUrl, entity, String.class);
        logger.info("Response from server: {}", response);

        // Optionally, parse the response if needed
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            DataModel savedEntity = objectMapper.readValue(response, DataModel.class);
            logger.info("Parsed response to DataModel: {}", savedEntity);
        } catch (Exception e) {
            logger.error("Failed to parse response to DataModel", e);
        }
    }

    //Method to delete specific data via DELETE request from the unsorted JSON
    public void delete(DataModel entity) {
        restTemplate.delete(baseUrl + "/" + entity.get_id());
    }
}