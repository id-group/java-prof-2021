package ru.idgroup.otus.io.dataprocessor;


import ru.idgroup.otus.io.model.Measurement;

import java.util.List;
import java.util.Map;

public interface Processor {

    Map<String, Double> process(List<Measurement> data);
}
