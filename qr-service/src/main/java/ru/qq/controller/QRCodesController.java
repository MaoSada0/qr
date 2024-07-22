package ru.qq.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.qq.payload.QRCodeGetPayload;
import ru.qq.service.MainService;

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



}
