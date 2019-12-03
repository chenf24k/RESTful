package com.chenf24k.restful;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SampleClass {
    private static final Log log = LogFactory.getLog(SampleClass.class);

    public void print(String name) {
        if (log.isTraceEnabled()) {
            log.trace("传入参数是：" + name);
        }
        try {
            // do something
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("出错啦", e);
            }
        }
    }
}
