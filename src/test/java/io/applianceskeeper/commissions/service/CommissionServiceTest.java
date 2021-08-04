package io.applianceskeeper.commissions.service;

import io.applianceskeeper.commissions.data.CommissionsRepository;
import io.applianceskeeper.commissions.models.Commission;
import io.applianceskeeper.technicians.models.TechnicianTerm;
import io.applianceskeeper.technicians.service.TechniciansTermsService;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class CommissionServiceTest {

    @InjectMocks
    private CommissionService service;

    @Mock
    private CommissionsRepository repository;
    @Mock
    private TechniciansTermsService termsService;

    @Test
    void shouldFindCommissionById() throws NotFoundException {
        //Given
        Commission commission = new Commission();
        commission.setId(5L);
        given(repository.findById(commission.getId())).willReturn(Optional.of(commission));

        //when
        Commission commissionFromBackend = service.findById(commission.getId());

        //then
        assertThat(commissionFromBackend).isEqualTo(commission);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenCommissionNotFound() throws NotFoundException {
        //Given
        Commission commission = new Commission();
        given(repository.findById(anyLong())).willReturn(Optional.empty());

        //when
        assertThrows(NotFoundException.class, () -> service.findById(anyLong()));
    }

    @Test
    void shouldReturnListOfCommissionsBelongsToClient() throws NotFoundException {
        //given
        Commission commission = new Commission();
        Commission commission1 = new Commission();
        Commission commission2 = new Commission();
        given(repository.findAllByClientId(anyLong())).willReturn(List.of(commission1, commission2, commission));

        //when
        List<Commission> commissionList = service.getAllCommissionsByClientId(anyLong());

        //then
        assertThat(commissionList).hasSize(3);
    }

    @Test
    void shouldThrowExceptionIfClientDoesNotExists() {
        //given
        given(repository.findAllByClientId(anyLong())).willReturn(Collections.emptyList());

        //when + then
        assertThrows(NotFoundException.class, () -> service.getAllCommissionsByClientId(anyLong()));
    }

    @Test
    void withPageIngAndSortingShouldInvokeFindAllMethodWithoutSearchTerm() {
        //given
        PageRequest pageRequest = PageRequest.of(2, 2);

        //when
        service.getSortedPagedFilteredCommissions("", pageRequest);

        //then
        then(repository).should(times(1)).findAll(anyString(), any(Pageable.class));
    }

    @Test
    void withPageIngAndSortingAndSearchTermShouldInvokeFindAllMethodWithSearchTerm() {
        //given
        PageRequest pageRequest = PageRequest.of(2, 2);

        //when
        service.getSortedPagedFilteredCommissions("example", pageRequest);

        //then
        then(repository).should(times(1)).findAll(anyString(), any(Pageable.class));
    }

    @Test
    void shouldSaveCommission() {
        //given
        Commission commission = new Commission();
        var repairDate = new TechnicianTerm();
        repairDate.setId(3L);
        commission.setRepairDate(repairDate);
        commission.setId(3L);
        given(repository.save(any(Commission.class))).willReturn(commission);
        given(termsService.findById(anyLong())).willReturn(repairDate);

        //when
        Commission commissionFromBackend = service.save(commission);

        //then
        assertAll(
                () -> assertThat(commissionFromBackend).isEqualTo(commission),
                () -> then(termsService).should(times(1)).findById(anyLong())
        );
        ;
    }

    @Test
    void shouldReturnAllCommissions() {
        //given
        Commission commission = new Commission();
        Commission commission1 = new Commission();
        Commission commission2 = new Commission();
        List<Commission> commissionList = List.of(commission1, commission2, commission);
        given(repository.findAll()).willReturn(commissionList);

        //when
        List<Commission> commissions = service.findAll();

        //then
        assertThat(commissions).hasSize(commissionList.size());
    }

    @Test
    void shouldReturnCommissionsCount() {
        //given
        given(repository.count()).willReturn(4L);

        //when
        Long result = service.getCommissionsCount();

        //then
        assertEquals(4L, result);
    }
}
