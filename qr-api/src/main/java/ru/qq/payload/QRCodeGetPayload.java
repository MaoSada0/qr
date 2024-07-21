package ru.qq.payload;

public record QRCodeGetPayload(String data, Short size) {

    public QRCodeGetPayload{
        if(size < 100 || size > 1300){
            size = 400;
        }
    }

}
