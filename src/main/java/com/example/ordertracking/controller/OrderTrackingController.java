package com.example.ordertracking.controller;

import com.example.ordertracking.model.OrderTracking;
import com.example.ordertracking.service.OrderTrackingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/order/tracking")
public class OrderTrackingController {

    private final OrderTrackingService service;

    public OrderTrackingController(OrderTrackingService service) {
        this.service = service;
    }

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadTrackingFile(@RequestParam("file") MultipartFile file) {
        try {
            String contentType = file.getContentType();
            List<OrderTracking> trackings;

            if ("application/json".equalsIgnoreCase(contentType)) {
                trackings = service.parseJson(file);
            } else if ("application/xml".equalsIgnoreCase(contentType)) {
                trackings = service.parseXml(file);
            } else {
                return ResponseEntity.badRequest().body("Tipo de archivo no soportado: " + contentType);
            }

            List<String> mensajes = service.processTrackingUpdates(trackings);

            return ResponseEntity.ok(mensajes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error procesando archivo: " + e.getMessage());
        }
    }
}
