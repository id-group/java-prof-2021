package ru.idgroup.otus.atm;

public enum BanknoteType {
    HUNDRED(100), HALF_THOUSAND(500), THOUSAND(1000), TOW_THOUSAND(2000), FIVE_THOUSAND(5000);

    private final int nominal;

    BanknoteType(int nominal) {
        this.nominal = nominal;
    }

    public int getNominal() {
        return nominal;
    }
}
