package com.resortworld.moneychanger.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A UserBalance.
 */
@Entity
@Table(name = "user_balance")
public class UserBalance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "balance", precision = 21, scale = 2, nullable = false)
    private BigDecimal balance;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("userBalances")
    private User name;

    @OneToOne(mappedBy = "currencyRate")
    @JsonIgnore
    private CurrencyRate currencyMany;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public UserBalance balance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public User getName() {
        return name;
    }

    public UserBalance name(User user) {
        this.name = user;
        return this;
    }

    public void setName(User user) {
        this.name = user;
    }

    public CurrencyRate getCurrencyMany() {
        return currencyMany;
    }

    public UserBalance currencyMany(CurrencyRate currencyRate) {
        this.currencyMany = currencyRate;
        return this;
    }

    public void setCurrencyMany(CurrencyRate currencyRate) {
        this.currencyMany = currencyRate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserBalance)) {
            return false;
        }
        return id != null && id.equals(((UserBalance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserBalance{" +
            "id=" + getId() +
            ", balance=" + getBalance() +
            "}";
    }
}
