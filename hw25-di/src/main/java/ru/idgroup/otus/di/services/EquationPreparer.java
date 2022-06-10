package ru.idgroup.otus.di.services;

import ru.idgroup.otus.di.model.Equation;

import java.util.List;

public interface EquationPreparer {
    List<Equation> prepareEquationsFor(int base);
}
