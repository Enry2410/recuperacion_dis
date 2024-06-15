package dis.ufv.API.backend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DataControllerTest {

    @Mock
    private DataService dataService;

    @InjectMocks
    private DataController dataController;

    @Test
    public void testGetAllData() throws IOException {
        DataModel dataModel1 = new DataModel();
        DataModel dataModel2 = new DataModel();
        List<DataModel> expectedData = Arrays.asList(dataModel1, dataModel2);

        when(dataService.getAllData()).thenReturn(expectedData);

        ResponseEntity<List<DataModel>> response = dataController.getAllData();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedData, response.getBody());
    }

    @Test
    public void testUpdateData() throws IOException {
        UUID id = UUID.randomUUID();
        DataModel updatedData = new DataModel();
        updatedData.set_id(id);

        when(dataService.updateData(id, updatedData)).thenReturn(true);

        ResponseEntity<String> response = dataController.updateData(id, updatedData);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Data updated successfully", response.getBody());
    }

    @Test
    public void testDeleteData() throws IOException {
        UUID id = UUID.randomUUID();

        when(dataService.deleteData(id)).thenReturn(true);

        ResponseEntity<String> response = dataController.deleteData(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Data deleted successfully", response.getBody());
    }

    @Test
    public void testAddData() throws IOException {
        DataModel newData = new DataModel();
        newData.set_id(UUID.randomUUID());

        dataController.addData(newData);

        Mockito.verify(dataService, Mockito.times(1)).addData(newData);
    }


    @Test
    public void testGetAllReadOnlyData() throws IOException {
        DataModel dataModel1 = new DataModel();
        DataModel dataModel2 = new DataModel();
        List<DataModel> expectedData = Arrays.asList(dataModel1, dataModel2);

        when(dataService.getAllReadOnlyData()).thenReturn(expectedData);

        ResponseEntity<List<DataModel>> response = dataController.getAllReadOnlyData();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedData, response.getBody());
    }
}
