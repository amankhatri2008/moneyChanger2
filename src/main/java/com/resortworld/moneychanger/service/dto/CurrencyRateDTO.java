package com.resortworld.moneychanger.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.resortworld.moneychanger.domain.CurrencyRate} entity.
 */
public class CurrencyRateDTO implements Serializable {

    private Long id;

    @NotNull
    private String currency;

    @NotNull
    private BigDecimal buyRate;

    @NotNull
    private BigDecimal sellRate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(BigDecimal buyRate) {
        this.buyRate = buyRate;
    }

    public BigDecimal getSellRate() {
        return sellRate;
    }

    public void setSellRate(BigDecimal sellRate) {
        this.sellRate = sellRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CurrencyRateDTO currencyRateDTO = (CurrencyRateDTO) o;
        if (currencyRateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), currencyRateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CurrencyRateDTO{" +
            "id=" + getId() +
            ", currency='" + getCurrency() + "'" +
            ", buyRate=" + getBuyRate() +
            ", sellRate=" + getSellRate() +
            "}";
    }
}
