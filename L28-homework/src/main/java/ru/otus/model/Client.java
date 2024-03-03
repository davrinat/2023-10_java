package ru.otus.model;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table("client")
public class Client {

    @Id
    private Long id;

    @Nonnull
    private String name;

    @MappedCollection(idColumn = "client_id")
    private Phone phone;

    @MappedCollection(idColumn = "client_id")
    private Address address;

    @PersistenceCreator
    public Client(Long id, String name, Address address, Phone phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public Client(String name, Address address, Phone phone) {
        this.id = null;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
}
