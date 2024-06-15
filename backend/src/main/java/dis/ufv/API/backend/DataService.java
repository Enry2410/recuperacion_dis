package dis.ufv.API.backend;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class DataService {
    private static final String FILE_PATH = "src/main/resources/cp-national-datafile.json";     //Path to the unsorted JSON
    private static final String MSCODE_PATH = "src/main/resources/MsCode_json.json";            //Path to the sorted JSON
    private Gson gson = new Gson();

    // Method to retrieve the data from the unsorted JSON
    public List<DataModel> getAllData() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
        return gson.fromJson(json, new TypeToken<List<DataModel>>() {}.getType());
    }

    //Method to save new data to the unsorted JSON
    public void saveAllData(List<DataModel> data) throws IOException {
        Files.write(Paths.get(FILE_PATH), gson.toJson(data).getBytes());
    }

    // Method to get the information from a specific object by its ID (Unused but potentially helpful)
    public Optional<DataModel> getDataById(UUID id) throws IOException {
        List<DataModel> data = getAllData();
        return data.stream().filter(d -> d.get_id().equals(id)).findFirst();
    }

    // Method to add new data to the existing unsorted JSON
    public void addData(DataModel newData) throws IOException {
        List<DataModel> existingData = getAllData();
        existingData.add(newData);
        saveAllData(existingData);
    }

    // Method to edit the existing data on the unsorted JSON
    public boolean updateData(UUID id, DataModel updatedData) throws IOException {
        List<DataModel> existingData = getAllData();
        Optional<DataModel> dataToUpdate = existingData.stream()
                .filter(data -> data.get_id().equals(id))
                .findFirst();

        if (dataToUpdate.isPresent()) {
            DataModel data = dataToUpdate.get();
            data.setMsCode(updatedData.getMsCode());
            data.setYear(updatedData.getYear());
            data.setEstCode(updatedData.getEstCode());
            data.setEstimate(updatedData.getEstimate());
            data.setSE(updatedData.getSE());
            data.setLowerCIB(updatedData.getLowerCIB());
            data.setUpperCIB(updatedData.getUpperCIB());
            data.setFlag(updatedData.getFlag());

            saveAllData(existingData);
            return true;
        } else {
            return false;
        }
    }

    // Method to delete specific data by its id
    public boolean deleteData(UUID id) throws IOException {
        List<DataModel> existingData = getAllData();
        Optional<DataModel> dataToDelete = existingData.stream()
                .filter(data -> data.get_id().equals(id))
                .findFirst();

        if (dataToDelete.isPresent()) {
            existingData.remove(dataToDelete.get());
            saveAllData(existingData);
            return true;
        } else {
            return false;
        }
    }

    // Method to retrieve data from the sorted JSON
    public List<DataModel> getAllReadOnlyData() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(MSCODE_PATH)));
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

        List<DataModel> dataList = new ArrayList<>();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            JsonArray dataArray = entry.getValue().getAsJsonArray();

            for (JsonElement element : dataArray) {
                DataModel data = gson.fromJson(element, DataModel.class);
                dataList.add(data);
            }
        }
        return dataList;
    }
}
