package ru.qq.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.qq.client.QRCodeGeneratorRestClient;
import ru.qq.repository.BinaryContentRepository;
import ru.qq.entity.BinaryContent;
import ru.qq.entity.QRCode;
import ru.qq.payload.QRCodeGetPayload;
import ru.qq.repository.QRCodeRepository;
import ru.qq.service.MainService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RequiredArgsConstructor
@Service
public class MainServiceImpl implements MainService {

    private final QRCodeRepository qrCodeRepository;
    private final BinaryContentRepository binaryContentRepository;

    @Autowired
    private final QRCodeGeneratorRestClient qrCodeGeneratorRestClient;

    @Autowired
    private final RestTemplate restTemplate;

    @Value("${qr.api.uri}")
    private String qrApiUri;

    @Override
    public QRCode saveQRCode(QRCodeGetPayload qrCodeGetPayload) {

        byte[] qrCodeBytes = qrCodeGeneratorRestClient.generateQRCode(qrCodeGetPayload).orElseThrow();

        BinaryContent binaryContent = BinaryContent.builder()
                .fileAsArrayOfBytes(qrCodeBytes)
                .build();

        binaryContentRepository.save(binaryContent);

        QRCode qrCode = QRCode.builder()
                .data(qrCodeGetPayload.data())
                .size(qrCodeGetPayload.size())
                .qrBinaryContent(binaryContent)
                .build();

        return qrCodeRepository.save(qrCode);
    }

    @Override
    public QRCode getQRCodeById(Long id) {
        return qrCodeRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void deleteQRCodeById(Long id) {
        if(!qrCodeRepository.existsById(id)) throw new NoSuchElementException();

        qrCodeRepository.deleteById(id);
    }

    @Override
    public List<QRCode> getAllQRCode() {
        return qrCodeRepository.findAll();
    }

    @Override
    public ByteArrayResource getZipAllQr() {
        List<byte[]> images = this.getAllQRCode()
                .stream()
                .map(q -> q.getQrBinaryContent().getFileAsArrayOfBytes())
                .toList();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            for (int i = 0; i < images.size(); i++) {
                ZipEntry zipEntry = new ZipEntry("image, id: " + (i + 1) + ".png");
                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.write(images.get(i));
                zipOutputStream.closeEntry();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ByteArrayResource(byteArrayOutputStream.toByteArray());
    }
}
