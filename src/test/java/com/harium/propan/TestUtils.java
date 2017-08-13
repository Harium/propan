package com.harium.propan;

import com.harium.etyl.util.io.IOHelper;

import java.net.URL;

public class TestUtils {

    public static boolean isTestEnvironment(URL dir) {
        String prefix = IOHelper.FILE_PREFIX + "/home/ubuntu";
        return dir.toString().startsWith(prefix);
    }
}
