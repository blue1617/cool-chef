package coolcook;

/**
 * Created by apreda on 30.04.2017.
 */
public class EmissionsResponse {

    private String prodEmissionsValue;
    private String transportEmissionsValue;
    private String totalEmissionsValue;


    public EmissionsResponse(String prodEmissionsValue, String transportEmissionsValue) {
        this.prodEmissionsValue = prodEmissionsValue;
        this.transportEmissionsValue = transportEmissionsValue;
        this.totalEmissionsValue = String.valueOf(Float.parseFloat
                (prodEmissionsValue) +
                Float.parseFloat(transportEmissionsValue));
    }

    public String getProdEmissionsValue() {
        return prodEmissionsValue;
    }

    public void setProdEmissionsValue(String prodEmissionsValue) {
        this.prodEmissionsValue = prodEmissionsValue;
    }

    public String getTransportEmissionsValue() {
        return transportEmissionsValue;
    }

    public void setTransportEmissionsValue(String transportEmissionsValue) {
        this.transportEmissionsValue = transportEmissionsValue;
    }

    public String getTotalEmissionsValue() {
        return totalEmissionsValue;
    }

    public void setTotalEmissionsValue(String totalEmissionsValue) {
        this.totalEmissionsValue = totalEmissionsValue;
    }
}
