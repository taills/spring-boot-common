package com.gxmafeng.service.common.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class NumericConvertUtilsTest {

    @Test
    public void test() {
        long originalNumber = new Date().getTime();
        int carryFlag = 60;
        String newNumber = NumericConvertUtils.toOtherNumberSystem(originalNumber, carryFlag);
        long newLong = NumericConvertUtils.toDecimalNumber(newNumber,carryFlag);
        Assert.assertEquals(originalNumber,newLong);
    }
}