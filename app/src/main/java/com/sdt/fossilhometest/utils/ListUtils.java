package com.sdt.fossilhometest.utils;

import java.util.Collections;
import java.util.List;

public final class ListUtils {

    public static <T> List<T> safe(List<T> list) {
        return list == null ? Collections.emptyList() : list;
    }

    private ListUtils() {}
}
