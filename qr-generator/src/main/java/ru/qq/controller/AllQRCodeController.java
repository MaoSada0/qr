package ru.qq.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.qq.entity.QRCode;
import ru.qq.service.MainService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("api/get-all-qr")
@RequiredArgsConstructor
public class AllQRCodeController {

    private final MainService mainService;

    @GetMapping("data")
    public ResponseEntity<List<QRCode>> getAllQRCodeData() {
        return ResponseEntity.ok(mainService.getAllQRCode());
    }

    @GetMapping("zip-download")
    public ResponseEntity<ByteArrayResource> getAllQRCodeImage() throws IOException {
        ByteArrayResource resource = mainService.getZipAllQr();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"QrCodes.zip\"");

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
