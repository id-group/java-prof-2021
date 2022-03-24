package ru.idgroup.otus.atm;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Atm {
    private final Map<BanknoteType, BanknoteCell> cells = new HashMap<>();

    public Atm() {
        cells.put(BanknoteType.HUNDRED, new BanknoteCell(BanknoteType.HUNDRED));
        cells.put(BanknoteType.HALF_THOUSAND, new BanknoteCell(BanknoteType.HALF_THOUSAND));
        cells.put(BanknoteType.THOUSAND, new BanknoteCell(BanknoteType.THOUSAND));
        cells.put(BanknoteType.TOW_THOUSAND, new BanknoteCell(BanknoteType.TOW_THOUSAND));
        cells.put(BanknoteType.FIVE_THOUSAND, new BanknoteCell(BanknoteType.FIVE_THOUSAND));
    }

    public void put(Map<BanknoteType, Integer> banknotes) {
        banknotes.entrySet().forEach(banknoteTypeIntegerEntry -> {
            cells.get(banknoteTypeIntegerEntry.getKey()).put(banknoteTypeIntegerEntry.getValue());
        });
    }

    public void withdraw(int total) {
        if (total > total())
            throw new IllegalArgumentException("Нет такой суммы, попробуйте еще раз.");

        final List<BanknoteCell> collect = cells.values().stream()
                .filter(banknoteCell -> banknoteCell.total() > 0)
                .sorted(Collections.reverseOrder(Comparator.comparingInt(value -> value.getBanknoteType().getNominal())))
                .collect(Collectors.toList());

        Map<BanknoteType, Integer> withdrawBanknotes = new HashMap<>();
        Integer restTotal = total;
        for(BanknoteCell cell : collect) {
            final int amn = Math.floorDiv(restTotal, cell.getBanknoteType().getNominal());
            int minAmn = Math.min( cell.getAmn(), amn );
            withdrawBanknotes.put(cell.getBanknoteType(), minAmn);
            restTotal -= minAmn * cell.getBanknoteType().getNominal();
        }

        if(restTotal > 0)
            throw new IllegalArgumentException("Нельзя выдать такую сумму.");

        withdrawBanknotes.entrySet().forEach(banknoteTypeIntegerEntry -> {
            cells.get(banknoteTypeIntegerEntry.getKey()).withdraw(banknoteTypeIntegerEntry.getValue());
        });
    }

    public int total() {
        return cells.values().stream().mapToInt(BanknoteCell::total).sum();
    }
}
