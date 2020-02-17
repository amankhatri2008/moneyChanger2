package com.resortworld.moneychanger.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class UsertransactionsMapperTest {

    private UsertransactionsMapper usertransactionsMapper;

    @BeforeEach
    public void setUp() {
        usertransactionsMapper = new UsertransactionsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(usertransactionsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(usertransactionsMapper.fromId(null)).isNull();
    }
}
