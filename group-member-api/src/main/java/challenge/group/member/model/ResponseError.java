package challenge.group.member.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A DTO representing a default response error.
 */
public class ResponseError implements Serializable {

    private static final long serialVersionUID = 8011622748112619204L;

    private Map<String, String> response = new LinkedHashMap<>();

    public ResponseError() {
    }

    public Map<String, String> getResponse() {
        return response;
    }

    public void put(String label, String message) {
        this.response.put(label, message);
    }
}
