package com.wrthomps.github.pavilion.demo;

import org.junit.Before;
import org.junit.Test;

public class HelloTest {

    private Hello hello;

    @Before
    public void setup()
    {
        this.hello = new Hello();
    }

    @Test
    public void sayHello_saysHello()
    {
        this.hello.sayHello();
    }
}
