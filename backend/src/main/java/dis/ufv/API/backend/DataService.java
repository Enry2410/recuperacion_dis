package dis.ufv.API.backend;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DataService {
    private static final String FILE_PATH = "src/main/resources/cp-national-datafile.json";
    private Gson gson = new Gson();

    public List<DataModel> getAllData() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
        return gson.fromJson(json, new TypeToken<List<DataModel>>() {}.getType());
    }
    public void saveAllData(List<DataModel> data) throws IOException {
        Files.write(Paths.get(FILE_PATH), gson.toJson(data).getBytes());
    }
    public Optional<DataModel> getDataById(UUID id) throws IOException {
        List<DataModel> data = getAllData();
        return data.stream().filter(d -> d.get_id().equals(id)).findFirst();
    }
    public void addData(DataModel newData) throws IOException {
        List<DataModel> existingData = getAllData();
        existingData.add(newData);
        saveAllData(existingData);
    }
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

}
