package ru.qq.service.impl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.qq.repository.QRCodeRepository;
import ru.qq.entity.QRCode;
import ru.qq.service.MainService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    private final QRCodeRepository qrCodeRepository;

    @Override
    public List<QRCode> getAllQRCode(){
        return qrCodeRepository.findAll();
    }

    @Override
    public QRCode addQRCode(QRCode qrCode){
        return qrCodeRepository.save(qrCode);
    }

    @Override
    public QRCode findQRCodeById(Long id){
        return qrCodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }
}
