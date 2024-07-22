package ru.qq.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.qq.service.MainService;

@RestController
@RequestMapping("api/qr-service")
@RequiredArgsConstructor
public class QRCodeController {

    private final MainService mainService;

    @Autowired
    private final HttpHeaders httpHeadersPng;

    @GetMapping("get/{id}")
    public ResponseEntity<byte[]> saveQr(@PathVariable("id") Long id){

        byte[] qrBytes = mainService.getQRCodeById(id)
                .getQrBinaryContent()
                .getFileAsArrayOfBytes();


        return new ResponseEntity<>(qrBytes, httpHeadersPng, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public void deleteQr(@PathVariable("id") Long id){
        mainService.deleteQRCodeById(id);
    }
}
