package ru.qq.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.qq.payload.QRCodeGetPayload;
import ru.qq.service.MainService;

import java.util.Base64;

@Controller
@RequestMapping("qq")
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("generateQR")
    public String mainPage(){
        return "index";
    }

    @PostMapping("generateQR")
    public String generateQR(@RequestParam(value = "text", required = true) String text, @RequestParam(value = "size", required = true) Short size, Model model) {
        String qrCodeUri = "/qq/qr?text=" + text + "&size=" + size;
        model.addAttribute("qrCodeUrl", qrCodeUri);
        return "index";
    }

    @GetMapping("/qr")
    public ResponseEntity<Resource> getQRCode(@RequestParam(value = "text", required = true) String text, @RequestParam(value = "size", required = true) Short size) {
        return mainService.getResponseFromApi(new QRCodeGetPayload(text, size));
    }
/*
    @GetMapping("/show-qr")
    public String showQRCode(@RequestParam(value = "text", required = true) String text, Model model) {
        String apiUrl = qrApiUrl + "?text=" + text;

        ResponseEntity<byte[]> response = restTemplate.getForEntity(apiUrl, byte[].class);

        if (response.getStatusCode() == HttpStatus.OK) {
            byte[] imageBytes = response.getBody();
            String base64Image = java.util.Base64.getEncoder().encodeToString(imageBytes);
            model.addAttribute("image", base64Image);
            model.addAttribute("text", text);
        }

        return "hello";
    }
*/
}
