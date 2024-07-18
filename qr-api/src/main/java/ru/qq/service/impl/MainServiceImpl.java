package ru.qq.service.impl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.qq.model.dao.QRCodeDAO;
import ru.qq.model.entity.QRCode;
import ru.qq.service.MainService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    private final QRCodeDAO qrCodeDAO;

    @Override
    public List<QRCode> getAllQRCode(){
        return qrCodeDAO.findAll();
    }

    @Override
    public QRCode addQRCode(QRCode qrCode){
        return qrCodeDAO.save(qrCode);
    }

    @Override
    public QRCode findQRCodeById(Long id){
        return qrCodeDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }
}
