package com.cms.cmxm.core

import com.cms.cmxm.MethodCell
import com.cms.cmxm.ins.LifeCycleInstrumentation
import com.cms.cmxm.ins.MonitorInstrumentation
import com.cms.cmxm.ins.ReceiverInstrumentation

public class XMConfig {
    boolean enable = true
    boolean keepQuiet = false
    boolean showHelp = false
    Map<String, Object> modifyMatchMaps = [:]
    Map<String, Map<String, Object>> modifyTasks = [:]
    List<LifeCycleInstrumentation> lifecycle = [];
    List<MonitorInstrumentation> monitors = [];
    List<ReceiverInstrumentation> receivers = [];
    Map<String, List<MethodCell>> instrumentation = [:]
}