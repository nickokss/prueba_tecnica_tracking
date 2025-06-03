package com.example.ordertracking.service;

import com.example.ordertracking.model.OrderTracking;
import com.example.ordertracking.model.OrderTrackingWrapper;
import com.example.ordertracking.model.TrackingStatus;
import com.example.ordertracking.repository.OrderTrackingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderTrackingService {

    private final OrderTrackingRepository repository;

    public OrderTrackingService(OrderTrackingRepository repository) {
        this.repository = repository;
    }

    private String getEstadoNombre(int statusId) {
        return switch (statusId) {
            case 1 -> "RECOGIDO EN ALMACÉN";
            case 2 -> "EN REPARTO";
            case 3 -> "INCIDENCIA EN ENTREGA";
            case 4 -> "ENTREGADO";
            default -> "ESTADO DESCONOCIDO";
        };
    }

    public List<String> processTrackingUpdates(List<OrderTracking> trackings) {
        List<String> mensajes = new ArrayList<>();

        for (OrderTracking tracking : trackings) {
            List<OrderTracking> existentes = repository.findByOrderIdOrderByChangeStatusDateAsc(tracking.getOrderId());

            if (isValidTransition(existentes, tracking)) {
                tracking.setInsertionDate(LocalDateTime.now());
                repository.save(tracking);

                String nombreEstado = getEstadoNombre(tracking.getTrackingStatusId());
                mensajes.add("El pedido con orderId " + tracking.getOrderId() + " tiene el estado " + nombreEstado);
            } else {
                mensajes.add("El pedido con orderId " + tracking.getOrderId() + " recibió un estado inválido y no fue guardado");
            }
        }
        
        return mensajes;
    }

    private boolean isValidTransition(List<OrderTracking> existingTrackings, OrderTracking newTracking) {
        if (existingTrackings.isEmpty()) {
            // Solo permitir estado inicial
            return newTracking.getTrackingStatusId() == 1;
        }

        OrderTracking lastTracking = existingTrackings.get(existingTrackings.size() - 1);
        int lastStatus = lastTracking.getTrackingStatusId();

        if (lastStatus == 4) {
            // Ya fue entregado, no se permite ninguna otra actualización
            return false;
        }

        if (lastStatus == newTracking.getTrackingStatusId()) {
            // Estado repetido, no tiene sentido volver a guardar lo mismo
            return false;
        }

        if (newTracking.getTrackingStatusId() == 1) {
            // No se puede volver al estado inicial
            return false;
        }

        // Cualquier otra transición se permite
        return true;
    }


    public List<OrderTracking> parseJson(MultipartFile file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        OrderTrackingWrapper wrapper = mapper.readValue(file.getInputStream(), OrderTrackingWrapper.class);
        return wrapper.getOrderTrackings();
    }

    public List<OrderTracking> parseXml(MultipartFile file) throws Exception {
        JAXBContext context = JAXBContext.newInstance(OrderTrackingWrapper.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        OrderTrackingWrapper wrapper = (OrderTrackingWrapper) unmarshaller.unmarshal(file.getInputStream());
        return wrapper.getOrderTrackings();
    }
}
