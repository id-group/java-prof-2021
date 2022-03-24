package ru.idgroup.otus.atm;

import java.util.HashMap;
import java.util.Map;

public class Application {
    public static void main(String ... args) {
        Atm atm = new Atm();

        Map<BanknoteType, Integer> money = Map.of(BanknoteType.FIVE_THOUSAND, 10, BanknoteType.HALF_THOUSAND, 5, BanknoteType.HUNDRED, 13);
        atm.put(money);
        System.out.println( atm.total() );
    }
}
