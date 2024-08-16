package edu.poly.shop.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "username")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;
    @Override
    public String toString() {
        return "Authority{" +
                "id='" + id + '\'' +
                // Không bao gồm thông tin `account` để tránh vòng lặp
                '}';
    }
}
