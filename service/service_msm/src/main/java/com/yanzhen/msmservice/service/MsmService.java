package com.yanzhen.msmservice.service;

import java.util.Map;

public interface MsmService {
    boolean send(Map map, String phone);
}
