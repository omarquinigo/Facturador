package controlador;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class GenerarQR {

    // tamaño QR
    private final int qrTamAncho = 400;
    private final int qrTamAlto = 400;
    private final String formato = "jpg";

    public void GenerarQR(String texto, String archivo) {
        BitMatrix matriz;
        Writer writer = new QRCodeWriter();
        try {
            matriz = writer.encode(texto, BarcodeFormat.QR_CODE, qrTamAncho, qrTamAlto);
        } catch (WriterException e) {
            e.printStackTrace(System.err);
            return;
        }
        BufferedImage imagen = new BufferedImage(qrTamAncho,
                qrTamAlto, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < qrTamAlto; y++) {
            for (int x = 0; x < qrTamAncho; x++) {
                int valor = (matriz.get(x, y) ? 0 : 1) & 0xff;
                imagen.setRGB(x, y, (valor == 0 ? 0 : 0xFFFFFF));
            }
        }
        try {
            FileOutputStream qrCode = new FileOutputStream(Rutas.getRutaQR() + archivo + "." + formato);
            ImageIO.write(imagen, formato, qrCode);
            qrCode.close();
        } catch (Exception e) {
        }
    }
}
