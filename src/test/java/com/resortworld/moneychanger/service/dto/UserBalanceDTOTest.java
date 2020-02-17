package com.resortworld.moneychanger.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.resortworld.moneychanger.web.rest.TestUtil;

public class UserBalanceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserBalanceDTO.class);
        UserBalanceDTO userBalanceDTO1 = new UserBalanceDTO();
        userBalanceDTO1.setId(1L);
        UserBalanceDTO userBalanceDTO2 = new UserBalanceDTO();
        assertThat(userBalanceDTO1).isNotEqualTo(userBalanceDTO2);
        userBalanceDTO2.setId(userBalanceDTO1.getId());
        assertThat(userBalanceDTO1).isEqualTo(userBalanceDTO2);
        userBalanceDTO2.setId(2L);
        assertThat(userBalanceDTO1).isNotEqualTo(userBalanceDTO2);
        userBalanceDTO1.setId(null);
        assertThat(userBalanceDTO1).isNotEqualTo(userBalanceDTO2);
    }
}
