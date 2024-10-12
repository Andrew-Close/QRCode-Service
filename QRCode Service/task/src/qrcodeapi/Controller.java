package qrcodeapi;

import com.google.zxing.WriterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qrcodeapi.imagecreator.ImageCreator;

@RestController
public class Controller {

    @GetMapping("/api/health")
    public ResponseEntity<Void> getHealth() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/qrcode")
    public ResponseEntity<?> getQRCode(@RequestParam(defaultValue = "250") int size,
                                       @RequestParam(defaultValue = "png") String type,
                                       @RequestParam(required = false) String contents,
                                       @RequestParam(defaultValue = "L") String correction) throws WriterException {
        System.out.println(size);
        ImageCreator returner = new ImageCreator();
        return returner.createImage(contents, size, type, correction);
    }
}
