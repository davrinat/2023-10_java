package ru.otus.model;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;


@Getter
@Setter
@NoArgsConstructor
@Table("phone")
public class Phone {

    @Id
    private Long clientId;

    @Nonnull
    private String phone;

    @PersistenceCreator
    public Phone(Long clientId, String phone) {
        this.clientId = clientId;
        this.phone = phone;
    }
}
