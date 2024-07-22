package ru.qq.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.qq.entity.QRCode;
import ru.qq.payload.QRCodeGetPayload;
import ru.qq.service.MainService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/qr-service")
@RequiredArgsConstructor
public class QRCodesController {
    private final MainService mainService;

    @Autowired
    private final HttpHeaders httpHeadersPng;

    @PostMapping("new")
    public ResponseEntity<byte[]> saveQr(@RequestParam(value = "text", required = true) String text,
                                         @RequestParam(value = "size", required = false) Short size){

        byte[] qrBytes = mainService.saveQRCode(new QRCodeGetPayload(text, size))
                .getQrBinaryContent()
                .getFileAsArrayOfBytes();


        return new ResponseEntity<>(qrBytes, httpHeadersPng, HttpStatus.OK);
    }

    @GetMapping("all/data")
    public ResponseEntity<List<QRCode>> getAllQRCodeData() {
        return ResponseEntity.ok(mainService.getAllQRCode());
    }

    @GetMapping("all/zip-download")
    public ResponseEntity<ByteArrayResource> getAllQRCodeImage() throws IOException {
        ByteArrayResource resource = mainService.getZipAllQr();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"QrCodes.zip\"");

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }



}
