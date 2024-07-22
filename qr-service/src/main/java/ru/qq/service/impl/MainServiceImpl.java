package ru.qq.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.qq.entity.BinaryContent;
import ru.qq.entity.QRCode;
import ru.qq.payload.QRCodeGetPayload;
import ru.qq.repository.QRCodeRepository;
import ru.qq.service.MainService;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class MainServiceImpl implements MainService {

    private final QRCodeRepository qrCodeRepository;

    @Autowired
    private final RestTemplate restTemplate;

    @Value("${qr.api.url}")
    private String qrApiUrl;

    @Override
    public QRCode saveQRCode(QRCodeGetPayload qrCodeGetPayload) {
        String apiUrl = qrApiUrl + "?text=" + qrCodeGetPayload.data() + "&size=" + qrCodeGetPayload.size();

        ResponseEntity<byte[]> response = restTemplate.getForEntity(apiUrl, byte[].class);
        byte[] qrCodeBytes = response.getBody(); // TODO: обработать ошибки

        BinaryContent binaryContent = BinaryContent.builder()
                .fileAsArrayOfBytes(qrCodeBytes)
                .build();

        QRCode qrCode = QRCode.builder()
                .data(qrCodeGetPayload.data())
                .size(qrCodeGetPayload.size())
                .qrBinaryContent(binaryContent)
                .build();

        return qrCodeRepository.save(qrCode);
    }

    @Override
    public QRCode getQRCodeById(Long id) {
        return qrCodeRepository.findById(id).orElseThrow();
    }

    @Override
    public void deleteQRCodeById(Long id) {
        qrCodeRepository.deleteById(id);
    }
}
