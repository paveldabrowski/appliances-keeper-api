package io.applianceskeeper.appliances.utils;


import lombok.AllArgsConstructor;
import java.util.List;

@AllArgsConstructor
public abstract class ApplianceAbstractService<T, S> implements ApplianceServicesKeeper<T> {

    protected ApplianceRepositoriesKeeper<T, S> repository;

    @Override
    public List<T> findAll() {
        return repository.findAllByOrderByNameAsc();
    }

    @Override
    public List<T> findAllByName(String name) {
        return repository.findAllByNameContainsIgnoreCaseOrderByName(name);
    }

    @Override
    public T add(T t) {
        return repository.save(t);
    }

    @Override
    public boolean checkIfNameExists(String name) {
        return repository.existsByNameIgnoreCase(name);
    }
}
