package ru.idgroup.otus.patterns;

import ru.idgroup.otus.patterns.handler.ComplexProcessor;
import ru.idgroup.otus.patterns.listener.ListenerPrinterConsole;
import ru.idgroup.otus.patterns.model.Message;
import ru.idgroup.otus.patterns.processor.LoggerProcessor;
import ru.idgroup.otus.patterns.processor.ProcessorConcatFields;
import ru.idgroup.otus.patterns.processor.ProcessorUpperField10;

import java.util.List;

public class Demo {
    public static void main(String[] args) {
        var processors = List.of(new ProcessorConcatFields(),
                new LoggerProcessor(new ProcessorUpperField10()));

        var complexProcessor = new ComplexProcessor(processors, ex -> {});
        var listenerPrinter = new ListenerPrinterConsole();
        complexProcessor.addListener(listenerPrinter);

        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        complexProcessor.removeListener(listenerPrinter);
    }
}
