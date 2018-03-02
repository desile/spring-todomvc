package ru.hh.school;


import org.junit.Assert;
import org.junit.Test;

public class SimpleTest {

    @Test
    public void twoPlusTwoTest(){
        int a = 2 + 2;
        Assert.assertEquals(a, 4);
    }

}
