package qrcodeapi.imagecreator.types;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import qrcodeapi.imagecreator.Image;
import qrcodeapi.imagecreator.ImageCreator;

import java.awt.image.BufferedImage;
import java.util.Map;

public class JPEGImage implements Image {
    @Override
    public ResponseEntity<BufferedImage> createImage(String contents, int size, String correction) {
        QRCodeWriter writer = new QRCodeWriter();
        Map<EncodeHintType, ?> hints = Map.of(EncodeHintType.ERROR_CORRECTION, ImageCreator.CorrectCorrections.getCorrection(correction));
        BufferedImage image;
        try {
            BitMatrix bitMatrix = writer.encode(contents, BarcodeFormat.QR_CODE, size, size, hints);
            image = MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }
}
