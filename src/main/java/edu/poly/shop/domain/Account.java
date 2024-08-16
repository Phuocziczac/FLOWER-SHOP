package edu.poly.shop.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    private String username;
    private String password;
    private String fullname;
    private String email;
    private String image;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Order> orders;

    private boolean isLogin = false;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Authority> authorities;

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                ", isLogin=" + isLogin +
                '}';
    }
    @PrePersist
    @PreUpdate
    private void manageOrders() {
        if (orders != null) {
            for (Order order : orders) {
                order.setAccount(this);
            }
        }
    }
}
