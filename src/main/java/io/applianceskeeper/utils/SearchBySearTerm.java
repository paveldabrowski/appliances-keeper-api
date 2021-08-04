package io.applianceskeeper.utils;

public interface SearchBySearTerm<T> {

    T findAllBySearchTerm(String searchTerm);
}
