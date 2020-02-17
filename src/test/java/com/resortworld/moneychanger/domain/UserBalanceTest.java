package com.resortworld.moneychanger.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.resortworld.moneychanger.web.rest.TestUtil;

public class UserBalanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserBalance.class);
        UserBalance userBalance1 = new UserBalance();
        userBalance1.setId(1L);
        UserBalance userBalance2 = new UserBalance();
        userBalance2.setId(userBalance1.getId());
        assertThat(userBalance1).isEqualTo(userBalance2);
        userBalance2.setId(2L);
        assertThat(userBalance1).isNotEqualTo(userBalance2);
        userBalance1.setId(null);
        assertThat(userBalance1).isNotEqualTo(userBalance2);
    }
}
