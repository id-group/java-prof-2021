package ru.idgroup.otus.io.dataprocessor;


import ru.idgroup.otus.io.model.Measurement;

import java.util.List;

public interface Loader {

    List<Measurement> load();
}
