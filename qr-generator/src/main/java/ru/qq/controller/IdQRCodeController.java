package ru.qq.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.qq.entity.QRCode;
import ru.qq.service.MainService;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/qr/{id}")
public class IdQRCodeController {

    private final MainService mainService;

    @GetMapping
    public ResponseEntity<byte[]> findById(@PathVariable("id") Long id) {
        QRCode qrCode = mainService.findQRCodeById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(qrCode.getQrBinaryContent().getFileAsArrayOfBytes(), headers, HttpStatus.OK);
    }



}
