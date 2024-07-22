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
import ru.qq.service.QRCodeGeneratorService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("api/generate-qr")
@RequiredArgsConstructor
public class ApiController {

    private final QRCodeGeneratorService qrCodeGeneratorService;

    @Autowired
    private final HttpHeaders httpHeadersPng;

    @GetMapping("body")
    public ResponseEntity<byte[]> getQRCodeImage(@RequestBody QRCodeGetPayload qrCodeGetPayload) {
        byte[] imageBytes = qrCodeGeneratorService.generateQrImage(qrCodeGetPayload);

        return new ResponseEntity<>(imageBytes, httpHeadersPng, HttpStatus.OK);
    }

    @GetMapping("param")
    public ResponseEntity<byte[]> getQRCodeImageAnother(@RequestParam(value = "text", required = true) String text,
                                                 @RequestParam(value = "size", required = false) Short size) {

        QRCodeGetPayload qrCodeGetPayload = new QRCodeGetPayload(text, size);

        byte[] imageBytes = qrCodeGeneratorService.generateQrImage(qrCodeGetPayload);

        return new ResponseEntity<>(imageBytes, httpHeadersPng, HttpStatus.OK);
    }


}
