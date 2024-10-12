package qrcodeapi.imagecreator;

import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import qrcodeapi.ErrorMessage;

@Component
public class ImageCreator {
    private enum CorrectTypes {
        PNG("png"), GIF("gif"), JPEG("jpeg");

        CorrectTypes(String type) {
            this.type = type;
        }

        private final String type;

        public static boolean contains(String type) {
            for (CorrectTypes correctType : CorrectTypes.values()) {
                if (correctType.type.equals(type)) {
                    return true;
                }
            }
            return false;
        }
    }

    public enum CorrectCorrections {
        L("L"), M("M"), Q("Q"), H("H");

        CorrectCorrections(String correction) {
            this.correction = correction;
        }

        private final String correction;

        public static boolean contains(String type) {
            for (CorrectCorrections correctCorrection : CorrectCorrections.values()) {
                if (correctCorrection.correction.equals(type)) {
                    return true;
                }
            }
            return false;
        }

        public static ErrorCorrectionLevel getCorrection(String correction) {
            return switch (correction) {
                case "L" -> ErrorCorrectionLevel.L;
                case "M" -> ErrorCorrectionLevel.M;
                case "Q" -> ErrorCorrectionLevel.Q;
                case "H" -> ErrorCorrectionLevel.H;
                default -> throw new IllegalArgumentException("Argument must be either L, M, Q or H");
            };
        }
    }

    public ResponseEntity<?> createImage(String contents, int size, String type, String correction) throws WriterException {
        if (contents == null || contents.isBlank()) {
            return new ErrorMessage("Contents cannot be null or blank").getResponse();
        }

        if (size < 150 || size > 350) {
            return new ErrorMessage("Image size must be between 150 and 350 pixels").getResponse();
        }

        if (!CorrectCorrections.contains(correction)) {
            return new ErrorMessage("Permitted error correction levels are L, M, Q, H").getResponse();
        }

        if (!CorrectTypes.contains(type)) {
            return new ErrorMessage("Only png, jpeg and gif image types are supported").getResponse();
        }

        Image image = Image.of(type);
        return image.createImage(contents, size, correction);
    }
}
