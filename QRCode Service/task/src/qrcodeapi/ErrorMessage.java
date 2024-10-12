package qrcodeapi;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ErrorMessage {
    HashMap<String, String> map = new HashMap<>();

    public ErrorMessage(String message) {
        map.put("error", message);
    }

    public ResponseEntity<Map<String, String>> getResponse() {
        return ResponseEntity
                .badRequest()
                .body(map);
    }
}
