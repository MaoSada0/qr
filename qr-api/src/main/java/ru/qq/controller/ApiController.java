package ru.qq.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.qq.model.entity.QRCode;
import ru.qq.service.MainService;
import ru.qq.service.QRCodeGeneratorService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("qq/api")
@RequiredArgsConstructor
public class ApiController {

    private final MainService mainService;
    private final QRCodeGeneratorService qrCodeGeneratorService;

    @GetMapping("all-data")
    public ResponseEntity<List<QRCode>> getAllQRCodeData() {


        return ResponseEntity.ok(mainService.getAllQRCode());
    }

    @GetMapping("all-image")
    public ResponseEntity<ByteArrayResource> getAllQRCodeImage() throws IOException {
        List<byte[]> images = mainService.getAllQRCode()
                .stream()
                .map(q -> q.getQrBinaryContent().getFileAsArrayOfBytes())
                .collect(Collectors.toList());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            for (int i = 0; i < images.size(); i++) {
                ZipEntry zipEntry = new ZipEntry("image" + (i + 1) + ".png");
                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.write(images.get(i));
                zipOutputStream.closeEntry();
            }
        }

        ByteArrayResource resource = new ByteArrayResource(byteArrayOutputStream.toByteArray());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"qrcodes.zip\"");

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @PostMapping("get-qr")
    public ResponseEntity<byte[]> getQRCodeImage(@RequestBody QRCode qrCode) {
        byte[] imageBytes = qrCodeGeneratorService.generateQrImage(qrCode);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @GetMapping("get-qr-another")
    public ResponseEntity<byte[]> getQRCodeImageAnother(@RequestParam(value = "text", required = true) String text,
                                                 @RequestParam(value = "size", required = false) Short size) {

        if(size == null) size = 400;
        QRCode qrCode = QRCode.builder()
                .data(text)
                .size(size).build();

        byte[] imageBytes = qrCodeGeneratorService.generateQrImage(qrCode);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> findById(@PathVariable("id") Long id) {
        QRCode qrCode = mainService.findQRCodeById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(qrCode.getQrBinaryContent().getFileAsArrayOfBytes(), headers, HttpStatus.OK);
    }
}
