package ru.idgroup.otus.annotations.usage;

import ru.idgroup.otus.annotations.annotations.After;
import ru.idgroup.otus.annotations.annotations.Before;
import ru.idgroup.otus.annotations.annotations.Test;

public class AnnotationTestClass {

    @Before
    public void beforeAll() {
        System.out.println("Before all annotation executed ....");
    }

    @Test
    public void simpleTest() {
        System.out.println("Test annotation executed ....");
    }

    @Test
    public void exceptionTest() {
        System.out.println("Test with exception annotation executed ....");
        throw new IllegalArgumentException("test exception ...");
    }

    @After
    public void afterAll() {
        System.out.println("After all annotation executed ....");
    }

}
