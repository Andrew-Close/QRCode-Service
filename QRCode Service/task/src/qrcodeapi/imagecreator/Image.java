package qrcodeapi.imagecreator;

import com.google.zxing.WriterException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import qrcodeapi.imagecreator.types.GIFImage;
import qrcodeapi.imagecreator.types.JPEGImage;
import qrcodeapi.imagecreator.types.PNGImage;

import java.awt.image.BufferedImage;

@Component
public interface Image {
    static Image of(String type) {
        return switch (type) {
            case "png" -> new PNGImage();
            case "gif" -> new GIFImage();
            case "jpeg" -> new JPEGImage();
            default -> throw new IllegalArgumentException("Argument must be either png, gif or jpeg");
        };
    }

    ResponseEntity<BufferedImage> createImage(String contents, int size, String correction) throws WriterException;
}
