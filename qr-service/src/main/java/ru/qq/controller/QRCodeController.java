package ru.qq.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.qq.service.MainService;

import java.util.NoSuchElementException;

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
    public ResponseEntity<Void> deleteQr(@PathVariable("id") Long id){
        mainService.deleteQRCodeById(id);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "no such element error"));
    }
}
