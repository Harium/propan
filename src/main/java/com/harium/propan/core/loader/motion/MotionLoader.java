package com.harium.propan.core.loader.motion;

import com.harium.propan.core.model.motion.Motion;

import java.io.IOException;
import java.net.URL;

public interface MotionLoader {

    Motion loadMotion(URL dir, String path) throws IOException;

}
