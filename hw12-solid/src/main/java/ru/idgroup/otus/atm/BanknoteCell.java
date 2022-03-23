package ru.idgroup.otus.atm;

public class BanknoteCell {
    private final BanknoteType banknoteType;
    private int amn = 0;

    public BanknoteCell(BanknoteType banknote) {
        banknoteType = banknote;
    }

    public BanknoteType getBanknoteType() {
        return banknoteType;
    }

    public int getAmn() {
        return amn;
    }

    public int put(int amn) {
        this.amn += amn;
        return this.amn;
    }

    public int withdraw(int amn) {
        if( amn > this.amn )
            throw new IllegalArgumentException("Банкнот с номиналом " + banknoteType.getNominal() + " недостаточно.");
        this.amn -= amn;
        return this.amn;
    }

    public int total() {
        return banknoteType.getNominal() * amn;
    }
}
