package io.applianceskeeper.appliances.model;

import io.applianceskeeper.clients.models.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppliancesWriteDTO {
    private Long id;
    private String serialNumber;
    private Model model;
    private Brand brand;
    private Client client;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppliancesWriteDTO)) return false;
        AppliancesWriteDTO that = (AppliancesWriteDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(serialNumber, that.serialNumber) &&
                Objects.equals(model, that.model) && Objects.equals(brand, that.brand) &&
                Objects.equals(client, that.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serialNumber, model, brand, client);
    }
}
