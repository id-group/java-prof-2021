package ru.idgroup.otus.patterns.processor.homework;

import ru.idgroup.otus.patterns.model.Message;
import ru.idgroup.otus.patterns.processor.Processor;

import java.time.LocalDateTime;

public class ProcessorEven implements Processor {
    private DateTimeProvider dateTimeProvider;

    public ProcessorEven(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        if( dateTimeProvider.getDate().getSecond() % 2 == 0)
            throw new UnsupportedOperationException();

        return message;
    }
}
