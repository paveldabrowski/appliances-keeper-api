package io.applianceskeeper.appliances.utils;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ControllersMethodsProvider<T> {

    ResponseEntity<List<T>> findAll();
}
