package ru.idgroup.otus.di;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.idgroup.otus.di.appcontainer.AppComponentsContainerImpl;
import ru.idgroup.otus.di.config.AppConfig;
import ru.idgroup.otus.di.services.EquationPreparer;
import ru.idgroup.otus.di.services.IOService;
import ru.idgroup.otus.di.services.PlayerService;

import java.io.PrintStream;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {

    @Disabled //надо удалить
    @DisplayName("Из контекста тремя способами должен корректно доставаться компонент с проставленными полями")
    @ParameterizedTest(name = "Достаем по: {0}")
    @CsvSource(value = {"GameProcessor, ru.idgroup.otus.di.services.GameProcessor",
            "GameProcessorImpl, ru.idgroup.otus.di.services.GameProcessor",
            "gameProcessor, ru.idgroup.otus.di.services.GameProcessor",

            "IOService, ru.idgroup.otus.di.services.IOService",
            "IOServiceStreams, ru.idgroup.otus.di.services.IOService",
            "ioService, ru.idgroup.otus.di.services.IOService",

            "PlayerService, ru.idgroup.otus.di.services.PlayerService",
            "PlayerServiceImpl, ru.idgroup.otus.di.services.PlayerService",
            "playerService, ru.idgroup.otus.di.services.PlayerService",

            "EquationPreparer, ru.idgroup.otus.di.services.EquationPreparer",
            "EquationPreparerImpl, ru.idgroup.otus.di.services.EquationPreparer",
            "equationPreparer, ru.idgroup.otus.di.services.EquationPreparer"
    })
    public void shouldExtractFromContextCorrectComponentWithNotNullFields(String classNameOrBeanId, Class<?> rootClass) throws Exception {
        var ctx = new AppComponentsContainerImpl(AppConfig.class);

        assertThat(classNameOrBeanId).isNotEmpty();
        Object component;
        if (classNameOrBeanId.charAt(0) == classNameOrBeanId.toUpperCase().charAt(0)) {
            Class<?> gameProcessorClass = Class.forName("ru.idgroup.otus.di.services." + classNameOrBeanId);
            assertThat(rootClass).isAssignableFrom(gameProcessorClass);

            component = ctx.getAppComponent(gameProcessorClass);
        } else {
            component = ctx.getAppComponent(classNameOrBeanId);
        }
        assertThat(component).isNotNull();
        assertThat(rootClass).isAssignableFrom(component.getClass());

        var fields = Arrays.stream(component.getClass().getDeclaredFields())
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .peek(f -> f.setAccessible(true))
                .collect(Collectors.toList());

        for (var field: fields){
            var fieldValue = field.get(component);
            assertThat(fieldValue).isNotNull().isInstanceOfAny(IOService.class, PlayerService.class,
                    EquationPreparer.class, PrintStream.class, Scanner.class);
        }

    }
}
