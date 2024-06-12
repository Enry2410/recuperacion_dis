package dis.ufv.API.backend;
import java.util.UUID;

public class DataModel {
    private String MsCode;
    private String Year;
    private String EstCode;
    private Float Estimate;
    private Float SE;
    private Float LowerCIB;
    private Float UpperCIB;
    private String Flag;
    private UUID _id;

    // Getters
    public String getMsCode() { return MsCode; }
    public String getYear() {
        return Year;
    }
    public String getEstCode() {
        return EstCode;
    }
    public Float getEstimate() {
        return Estimate;
    }
    public Float getSE() {
        return SE;
    }
    public Float getLowerCIB() {
        return LowerCIB;
    }
    public Float getUpperCIB() {
        return UpperCIB;
    }
    public String getFlag() {
        return Flag;
    }
    public UUID get_id() {
        return _id;
    }

    // Setters
    public void setMsCode(String msCode) {
        this.MsCode = msCode;
    }
    public void setYear(String year) {
        this.Year = year;
    }
    public void setEstCode(String estCode) {
        this.EstCode = estCode;
    }
    public void setEstimate(Float estimate) {
        this.Estimate = estimate;
    }
    public void setSE(Float se) {
        this.SE = se;
    }
    public void setLowerCIB(Float lowerCIB) {
        this.LowerCIB = lowerCIB;
    }
    public void setUpperCIB(Float upperCIB) {
        this.UpperCIB = upperCIB;
    }
    public void setFlag(String flag) {
        this.Flag = flag;
    }
    public void set_id(UUID _id) {
        this._id = _id;
    }
}