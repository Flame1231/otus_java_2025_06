package ru.otus.crm.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
@SuppressWarnings("java:S1182")
public class Client implements Cloneable {

    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Phone> phones = new ArrayList<>();

    public Client(String name) {
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address != null ? address.clone() : null;
        this.phones = new ArrayList<>();
        for (Phone phone : phones) {
            Phone phoneClone = phone.clone();
            phoneClone.setClient(this);
            this.phones.add(phoneClone);
        }
    }

    @Override
    @SuppressWarnings("java:S2975")
    public Client clone() {
        Address clonedAddress = this.address != null ? this.address.clone() : null;
        List<Phone> clonedPhones = new ArrayList<>();
        for (Phone phone : this.phones) {
            Phone phoneClone = phone.clone();
            phoneClone.setClient(this);
            clonedPhones.add(phoneClone);
        }
        return new Client(this.id, this.name, clonedAddress, clonedPhones);
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name='" + name + '\'' + ", address=" + address + ", phones=" + phones + '}';
    }
}
