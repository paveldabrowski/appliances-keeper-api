package io.applianceskeeper.appliances.utils;

import java.util.List;

public interface ApplianceServicesKeeper<T, ID> {

    List<T> findAll();

    List<T> findAllByName(String name);

    T add(T t);

    boolean checkIfNameExists(String name);

    T findById(ID id);
}
