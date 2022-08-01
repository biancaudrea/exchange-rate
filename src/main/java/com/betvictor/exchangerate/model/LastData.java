package com.betvictor.exchangerate.model;

import com.betvictor.exchangerate.utils.RestCallType;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class LastData {
    private Timestamp timestamp;
    private RestCallType restCallType;
    private Map<String, String> parameters = new HashMap<>();
    private String result;
}
