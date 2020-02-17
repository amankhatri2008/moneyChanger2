package com.resortworld.moneychanger.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A CurrencyRate.
 */
@Entity
@Table(name = "currency_rate")
public class CurrencyRate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "currency", nullable = false, unique = true)
    private String currency;

    @NotNull
    @Column(name = "buy_rate", precision = 21, scale = 2, nullable = false)
    private BigDecimal buyRate;

    @NotNull
    @Column(name = "sell_rate", precision = 21, scale = 2, nullable = false)
    private BigDecimal sellRate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public CurrencyRate currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getBuyRate() {
        return buyRate;
    }

    public CurrencyRate buyRate(BigDecimal buyRate) {
        this.buyRate = buyRate;
        return this;
    }

    public void setBuyRate(BigDecimal buyRate) {
        this.buyRate = buyRate;
    }

    public BigDecimal getSellRate() {
        return sellRate;
    }

    public CurrencyRate sellRate(BigDecimal sellRate) {
        this.sellRate = sellRate;
        return this;
    }

    public void setSellRate(BigDecimal sellRate) {
        this.sellRate = sellRate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CurrencyRate)) {
            return false;
        }
        return id != null && id.equals(((CurrencyRate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CurrencyRate{" +
            "id=" + getId() +
            ", currency='" + getCurrency() + "'" +
            ", buyRate=" + getBuyRate() +
            ", sellRate=" + getSellRate() +
            "}";
    }
}
