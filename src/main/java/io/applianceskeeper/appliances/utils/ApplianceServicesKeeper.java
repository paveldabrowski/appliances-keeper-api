package io.applianceskeeper.appliances.utils;

import java.util.List;

public interface ApplianceServicesKeeper<T> {

    List<T> findAll();

    List<T> findAllByName(String name);

    T add(T t);

    boolean checkIfNameExists(String name);

}
