package com.resortworld.moneychanger.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.resortworld.moneychanger.web.rest.TestUtil;

public class UsertransactionsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsertransactionsDTO.class);
        UsertransactionsDTO usertransactionsDTO1 = new UsertransactionsDTO();
        usertransactionsDTO1.setId(1L);
        UsertransactionsDTO usertransactionsDTO2 = new UsertransactionsDTO();
        assertThat(usertransactionsDTO1).isNotEqualTo(usertransactionsDTO2);
        usertransactionsDTO2.setId(usertransactionsDTO1.getId());
        assertThat(usertransactionsDTO1).isEqualTo(usertransactionsDTO2);
        usertransactionsDTO2.setId(2L);
        assertThat(usertransactionsDTO1).isNotEqualTo(usertransactionsDTO2);
        usertransactionsDTO1.setId(null);
        assertThat(usertransactionsDTO1).isNotEqualTo(usertransactionsDTO2);
    }
}
