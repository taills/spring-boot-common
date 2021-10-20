package com.gxmafeng.service.common.utils;

import org.junit.Test;

public class SnowFlakeTest {

    @Test
    public void nextId() {
        System.out.println(SnowFlake.get().nextId());
        System.out.println(SnowFlake.get().nextId());
        System.out.println(SnowFlake.get().nextId());
        System.out.println(SnowFlake.get().nextId());
    }
}