package org.vaadin.example;

import com.nimbusds.jose.shaded.gson.annotations.SerializedName;
import java.util.UUID;

public class DataModel {
    @SerializedName("mscode")
    private String MsCode = "";

    @SerializedName("year")
    private String Year = "";

    @SerializedName("estCode")
    private String EstCode = "";

    @SerializedName("estimate")
    private Float Estimate = 0.0f;

    @SerializedName("se")
    private Float SE = 0.0f;

    @SerializedName("lowerCIB")
    private Float LowerCIB = 0.0f;

    @SerializedName("upperCIB")
    private Float UpperCIB = 0.0f;

    @SerializedName("flag")
    private String Flag = "";

    @SerializedName("_id")
    private UUID _id = UUID.randomUUID();


    public DataModel(String msCode, String year, String estCode, float estimate, float se, float lowerCIB, float upperCIB, String flag, UUID id) {
        this.MsCode = msCode;
        this.Year = year;
        this.EstCode = estCode;
        this.Estimate = estimate;
        this.SE = se;
        this.LowerCIB = lowerCIB;
        this.UpperCIB = upperCIB;
        this.Flag = flag;
        this._id = id;
    }

    public DataModel() {}

    // Getters
    public String getMsCode() {
        return MsCode;
    }

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