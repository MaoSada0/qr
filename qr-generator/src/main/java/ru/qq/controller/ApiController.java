package ru.qq.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.qq.payload.QRCodeGetPayload;
import ru.qq.service.QRCodeGeneratorService;

@RestController
@RequestMapping("api/generate-qr")
@RequiredArgsConstructor
public class ApiController {

    private final QRCodeGeneratorService qrCodeGeneratorService;

    @Autowired
    private final HttpHeaders httpHeadersPng;

    @GetMapping("body")
    public ResponseEntity<byte[]> getQRCodeImage(@Valid @RequestBody QRCodeGetPayload qrCodeGetPayload){
        byte[] imageBytes = qrCodeGeneratorService.generateQrImage(qrCodeGetPayload);
        return new ResponseEntity<>(imageBytes, httpHeadersPng, HttpStatus.OK);
    }

    @GetMapping("param")
    public ResponseEntity<byte[]> getQRCodeImageAnother(@RequestParam(value = "text", required = true) String text,
                                                 @RequestParam(value = "size", required = false) Short size){
        QRCodeGetPayload qrCodeGetPayload = new QRCodeGetPayload(text, size);

        byte[] imageBytes = qrCodeGeneratorService.generateQrImage(qrCodeGetPayload);

        return new ResponseEntity<>(imageBytes, httpHeadersPng, HttpStatus.OK);

    }


}
