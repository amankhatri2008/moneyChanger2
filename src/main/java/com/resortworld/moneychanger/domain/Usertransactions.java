package com.resortworld.moneychanger.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

import com.resortworld.moneychanger.domain.enumeration.Action;

/**
 * A Usertransactions.
 */
@Entity
@Table(name = "usertransactions")
public class Usertransactions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "from_quantity", precision = 21, scale = 2, nullable = false)
    private BigDecimal from_quantity;

    @NotNull
    @Column(name = "to_quantity", precision = 21, scale = 2, nullable = false)
    private BigDecimal toQuantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    private Action action;

    @OneToOne(mappedBy = "currency")
    @JsonIgnore
    private CurrencyRate from;

    @OneToOne(mappedBy = "currency")
    @JsonIgnore
    private CurrencyRate to;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("usertransactions")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getFrom_quantity() {
        return from_quantity;
    }

    public Usertransactions from_quantity(BigDecimal from_quantity) {
        this.from_quantity = from_quantity;
        return this;
    }

    public void setFrom_quantity(BigDecimal from_quantity) {
        this.from_quantity = from_quantity;
    }

    public BigDecimal getToQuantity() {
        return toQuantity;
    }

    public Usertransactions toQuantity(BigDecimal toQuantity) {
        this.toQuantity = toQuantity;
        return this;
    }

    public void setToQuantity(BigDecimal toQuantity) {
        this.toQuantity = toQuantity;
    }

    public Action getAction() {
        return action;
    }

    public Usertransactions action(Action action) {
        this.action = action;
        return this;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public CurrencyRate getFrom() {
        return from;
    }

    public Usertransactions from(CurrencyRate currencyRate) {
        this.from = currencyRate;
        return this;
    }

    public void setFrom(CurrencyRate currencyRate) {
        this.from = currencyRate;
    }

    public CurrencyRate getTo() {
        return to;
    }

    public Usertransactions to(CurrencyRate currencyRate) {
        this.to = currencyRate;
        return this;
    }

    public void setTo(CurrencyRate currencyRate) {
        this.to = currencyRate;
    }

    public User getUser() {
        return user;
    }

    public Usertransactions user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Usertransactions)) {
            return false;
        }
        return id != null && id.equals(((Usertransactions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Usertransactions{" +
            "id=" + getId() +
            ", from_quantity=" + getFrom_quantity() +
            ", toQuantity=" + getToQuantity() +
            ", action='" + getAction() + "'" +
            "}";
    }
}
