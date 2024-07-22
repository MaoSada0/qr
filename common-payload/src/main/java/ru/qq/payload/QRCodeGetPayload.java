
package ru.qq.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record QRCodeGetPayload(
        @NotNull
        @Size(max = 255, min = 1)
        String data,

        @NotNull
        @Min(100)
        @Max(1200)
        Short size) {
}
