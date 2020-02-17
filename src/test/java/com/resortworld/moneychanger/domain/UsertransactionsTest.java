package com.resortworld.moneychanger.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.resortworld.moneychanger.web.rest.TestUtil;

public class UsertransactionsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Usertransactions.class);
        Usertransactions usertransactions1 = new Usertransactions();
        usertransactions1.setId(1L);
        Usertransactions usertransactions2 = new Usertransactions();
        usertransactions2.setId(usertransactions1.getId());
        assertThat(usertransactions1).isEqualTo(usertransactions2);
        usertransactions2.setId(2L);
        assertThat(usertransactions1).isNotEqualTo(usertransactions2);
        usertransactions1.setId(null);
        assertThat(usertransactions1).isNotEqualTo(usertransactions2);
    }
}
