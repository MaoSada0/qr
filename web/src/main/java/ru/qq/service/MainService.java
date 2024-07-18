package ru.qq.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface MainService {
    ResponseEntity<Resource> getResponseFromApi(String text, Short size);
}
