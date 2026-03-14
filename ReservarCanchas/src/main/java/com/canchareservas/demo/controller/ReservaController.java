package com.canchareservas.demo.controller;

import com.canchareservas.demo.model.Reserva;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private List<Reserva> listaReservas = new ArrayList<>();

    // GET: Lista todas las reservas
    @GetMapping
    public List<Reserva> listarTodas() {
        return listaReservas;
    }

    // POST: Crea una nueva reserva
    @PostMapping
    public Reserva crearReserva(@RequestBody Reserva nuevaReserva) {
        if (nuevaReserva.getId() == null) {
            nuevaReserva.setId((long) (listaReservas.size() + 1));
        }
        listaReservas.add(nuevaReserva);
        return nuevaReserva;
    }

    // GET: Reservas de la cancha con ID específico
    @GetMapping("/cancha/{canchaId}")
    public List<Reserva> listarPorCancha(@PathVariable Long canchaId) {
        return listaReservas.stream()
                .filter(r -> r.getCanchaId().equals(canchaId))
                .collect(Collectors.toList());
    }
}