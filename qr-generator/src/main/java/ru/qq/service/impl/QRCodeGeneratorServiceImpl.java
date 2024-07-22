package ru.qq.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.qq.dao.BinaryContentDAO;
import ru.qq.payload.QRCodeGetPayload;
import ru.qq.repository.QRCodeRepository;
import ru.qq.entity.BinaryContent;
import ru.qq.entity.QRCode;
import ru.qq.service.QRCodeGeneratorService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class QRCodeGeneratorServiceImpl implements QRCodeGeneratorService {

    private final QRCodeRepository qrCodeRepository;
    private final BinaryContentDAO binaryContentDAO;

    @Override
    public byte[] generateQrImage(QRCodeGetPayload qrCodeGetPayload){
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix;

        Short size = qrCodeGetPayload.size();

        try {
            bitMatrix = qrCodeWriter.encode(qrCodeGetPayload.data(),
                    BarcodeFormat.QR_CODE,
                    size,
                    size);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }


        var binaryContent = getBinaryContent(bitMatrix);

        QRCode qrCode = QRCode.builder()
                .qrBinaryContent(binaryContent)
                .size(size)
                .data(qrCodeGetPayload.data())
                .build();

        return qrCodeRepository.save(qrCode)
                .getQrBinaryContent()
                .getFileAsArrayOfBytes();
    }

    private BinaryContent getBinaryContent(BitMatrix bitMatrix) {
        BinaryContent transientBinaryContent = BinaryContent.builder()
                .fileAsArrayOfBytes(bitMatrixToByteArray(bitMatrix))
                .build();


        return binaryContentDAO.save(transientBinaryContent);
    }

    private byte[] bitMatrixToByteArray(BitMatrix bitMatrix) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

        try {
            ImageIO.write(image, "png", baos);
            baos.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        } finally {
            try {
                baos.close();
            } catch (IOException e) {

            }
        }

        return baos.toByteArray();
    }
}
