package ru.idgroup.otus.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;


import java.util.Map;

class AtmTest {
    Atm atm;

    @BeforeEach
    void setUp() {
        atm = new Atm();
    }

    @Test
    void simpleTest() {
        //given
        Map<BanknoteType, Integer> money = Map.of(BanknoteType.FIVE_THOUSAND, 10, BanknoteType.HALF_THOUSAND, 5, BanknoteType.HUNDRED, 13);
        //when
        atm.put(money);
        //then
        assertThat(atm.total()).isEqualTo(53800);

        //given
        int total = 6000;
        //when
        atm.withdraw(total);
        //then
        assertThat(atm.total()).isEqualTo(47800);

        //given
        int minTotal = 1500;
        //when
        atm.withdraw(minTotal);
        //then
        assertThat(atm.total()).isEqualTo(46300);

        //given
        int exTotal = 10;
        //when
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    atm.withdraw(exTotal);
                });
        //then
        assertThat(atm.total()).isEqualTo(46300);

        //given
        int hundredTotal = 1300;
        //when
        atm.withdraw(hundredTotal);
        //then
        assertThat(atm.total()).isEqualTo(45000);

        //given
        int newHundredTotal = 700;
        //when
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    atm.withdraw(newHundredTotal);
                });
        //then
        assertThat(atm.total()).isEqualTo(45000);

    }

}
