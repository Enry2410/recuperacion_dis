package dis.ufv.API.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/data")
public class DataController {
    private final DataService dataService;

    @Autowired
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping
    public ResponseEntity<List<DataModel>> getAllData() {
        try {
            List<DataModel> data = dataService.getAllData();
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<String> addData(@RequestBody DataModel newData) {
        try {
            UUID newId = UUID.randomUUID();
            newData.set_id(newId);
            dataService.addData(newData);
            return new ResponseEntity<>("Data added successfully", HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateData(@PathVariable UUID id, @RequestBody DataModel updatedData) {
        try {
            if (!id.equals(updatedData.get_id())) {
                return new ResponseEntity<>("Mismatch between _id in path and _id in the request body", HttpStatus.BAD_REQUEST);
            }

            boolean isUpdated = dataService.updateData(id, updatedData);
            if (isUpdated) {
                return new ResponseEntity<>("Data updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Data with specified _id not found", HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteData(@PathVariable UUID id) {
        try {
            boolean isDeleted = dataService.deleteData(id);
            if (isDeleted) {
                return new ResponseEntity<>("Data deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Data with specified _id not found", HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}