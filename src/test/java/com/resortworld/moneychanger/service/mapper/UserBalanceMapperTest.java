package com.resortworld.moneychanger.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class UserBalanceMapperTest {

    private UserBalanceMapper userBalanceMapper;

    @BeforeEach
    public void setUp() {
        userBalanceMapper = new UserBalanceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(userBalanceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userBalanceMapper.fromId(null)).isNull();
    }
}
