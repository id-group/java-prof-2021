package ru.idgroup.otus.patterns.listener.homework;

import org.junit.jupiter.api.Test;
import ru.idgroup.otus.patterns.model.Message;
import ru.idgroup.otus.patterns.processor.Processor;
import ru.idgroup.otus.patterns.processor.homework.ProcessorEven;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProcessorEvenTest {

    @Test
    void processorTest() {

        //given

        var process = new ProcessorEven(() -> LocalDateTime.of(2022, 3, 26, 16, 20, 2));
        var message = new Message.Builder(10).field10("field10").build();

        //when
        assertThrows( UnsupportedOperationException.class, () -> process.process(message));

        //given
        var processOK = new ProcessorEven(() -> LocalDateTime.of(2022, 3, 26, 16, 20, 3));

        //when
        var newMessage = processOK.process(message);

        //then
        assertThat(newMessage).isEqualTo(message);

    }
}
