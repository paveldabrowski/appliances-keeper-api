package io.applianceskeeper.appliances.utils;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@AllArgsConstructor
public abstract class ApplianceAbstractController<T, ID> implements ControllersMethodsProvider<T>  {

    private final ApplianceAbstractService<T, ID> service;

    @GetMapping
    public ResponseEntity<List<T>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(params = {"name"})
    public ResponseEntity<List<T>> findAllByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(service.findAllByName(name));
    }

    @PostMapping
    public ResponseEntity<T> add(@RequestBody T t) {
        return ResponseEntity.ok(service.add(t));
    }

    @GetMapping(params = {"nameExists"})
    public ResponseEntity<Boolean> checkIfNameExists(@RequestParam("nameExists") String name) {
        return ResponseEntity.ok(service.checkIfNameExists(name));
    }
}
