package challenge.campaign.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A DTO representing a default response error.
 */
public class ResponseError implements Serializable {

    private static final long serialVersionUID = -1352269860286855975L;

    private Map<String, Object> response = new LinkedHashMap<>();

    public ResponseError() {
    }

    public Map<String, Object> getResponse() {
        return response;
    }

    public void put(String label, String message) {
        this.response.put(label, message);
    }
}
