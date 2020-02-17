package com.resortworld.moneychanger.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.resortworld.moneychanger.domain.UserBalance} entity.
 */
public class UserBalanceDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal balance;


    private Long nameId;

    private String nameLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getNameId() {
        return nameId;
    }

    public void setNameId(Long userId) {
        this.nameId = userId;
    }

    public String getNameLogin() {
        return nameLogin;
    }

    public void setNameLogin(String userLogin) {
        this.nameLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserBalanceDTO userBalanceDTO = (UserBalanceDTO) o;
        if (userBalanceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userBalanceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserBalanceDTO{" +
            "id=" + getId() +
            ", balance=" + getBalance() +
            ", nameId=" + getNameId() +
            ", nameLogin='" + getNameLogin() + "'" +
            "}";
    }
}
