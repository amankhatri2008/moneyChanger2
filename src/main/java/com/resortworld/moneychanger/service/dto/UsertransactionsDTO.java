package com.resortworld.moneychanger.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import com.resortworld.moneychanger.domain.enumeration.Action;

/**
 * A DTO for the {@link com.resortworld.moneychanger.domain.Usertransactions} entity.
 */
public class UsertransactionsDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal from_quantity;

    @NotNull
    private BigDecimal toQuantity;

    @NotNull
    private Action action;


    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getFrom_quantity() {
        return from_quantity;
    }

    public void setFrom_quantity(BigDecimal from_quantity) {
        this.from_quantity = from_quantity;
    }

    public BigDecimal getToQuantity() {
        return toQuantity;
    }

    public void setToQuantity(BigDecimal toQuantity) {
        this.toQuantity = toQuantity;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UsertransactionsDTO usertransactionsDTO = (UsertransactionsDTO) o;
        if (usertransactionsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), usertransactionsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UsertransactionsDTO{" +
            "id=" + getId() +
            ", from_quantity=" + getFrom_quantity() +
            ", toQuantity=" + getToQuantity() +
            ", action='" + getAction() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
