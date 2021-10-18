package com.gxmafeng.service.common.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class IdGenTest {

    @Test
    public void nextId() {
        System.out.println(IdGen.get().nextId());
    }

    @Test
    public void timeGen() {
        System.out.println(IdGen.get().timeGen());
    }
}