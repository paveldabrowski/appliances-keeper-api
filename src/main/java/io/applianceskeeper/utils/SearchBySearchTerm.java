package io.applianceskeeper.utils;

public interface SearchBySearchTerm<T> {

    T findAllBySearchTerm(String searchTerm);
}
