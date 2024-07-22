package ru.qq.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.qq.client.QRCodeServRestClient;
import ru.qq.exception.BadRequestException;
import ru.qq.payload.QRCodeGetPayload;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class QRCodeServRestClientImpl implements QRCodeServRestClient {

    private final RestClient restClient;

    @Override
    public byte[] createQRCode(QRCodeGetPayload qrCodeGetPayload) {
        try {
            return restClient.post()
                    .uri("/new")
                    .body(qrCodeGetPayload)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(byte[].class);
        } catch (HttpClientErrorException.BadRequest exception){
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }
}
