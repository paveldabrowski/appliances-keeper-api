package io.applianceskeeper.utils;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Optional;

public interface SortedPaginatedFiltered<T> {

    @GetMapping(params = {"searchTerm", "sort", "page", "size"})
    T getSortedPagedFiltered(Optional<String> searchTerm, Pageable pageable);
}
