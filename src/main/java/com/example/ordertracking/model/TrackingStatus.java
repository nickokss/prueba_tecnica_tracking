package com.example.ordertracking.model;

public enum TrackingStatus {
    RECOGIDO_EN_ALMACEN(1),
    EN_REPARTO(2),
    INCIDENCIA_EN_ENTREGA(3),
    ENTREGADO(4);

    private final int code;

    TrackingStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static TrackingStatus fromCode(int code) {
        for (TrackingStatus status : TrackingStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código de estado inválido: " + code);
    }
}
