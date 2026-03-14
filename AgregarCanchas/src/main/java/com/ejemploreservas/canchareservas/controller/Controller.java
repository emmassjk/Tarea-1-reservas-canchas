package com.ejemploreservas.canchareservas.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ejemploreservas.canchareservas.model.Cancha;

@RestController
public class Controller {

    private List<Cancha> canchas = new ArrayList<>();
    private AtomicLong idGenerator = new AtomicLong(1);

    @GetMapping("/canchas")
    public List<Cancha> getAllCanchas() {
        return canchas;
    }

    @GetMapping("/canchas/{id}")
    public Optional<Cancha> getCanchaById(@PathVariable Long id) {
        return canchas.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    @PostMapping("/canchas")
    public Cancha addCancha(@RequestBody Cancha cancha) {
        cancha.setId(idGenerator.getAndIncrement());
        canchas.add(cancha);
        return cancha;
    }
}
