package com.mobile.map;

import com.mobile.map.entity.Device;
import com.mobile.map.entity.Line;

public interface DrawClickListener {
    void deviceClick(Device device);
    void lineClick(Line line);
}
