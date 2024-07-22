package ru.qq.service.impl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import ru.qq.repository.QRCodeRepository;
import ru.qq.entity.QRCode;
import ru.qq.service.MainService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    private final QRCodeRepository qrCodeRepository;

    @Override
    public List<QRCode> getAllQRCode(){
        return qrCodeRepository.findAll();
    }

    @Override
    public QRCode addQRCode(QRCode qrCode){
        return qrCodeRepository.save(qrCode);
    }

    @Override
    public QRCode findQRCodeById(Long id){
        return qrCodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
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
