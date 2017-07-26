package com.cms.cmxm.core

import com.cms.cmxm.MethodCell
import com.cms.cmxm.ins.MonitorInstrumentation

public class XMConfig {
    boolean enable = true
    boolean keepQuiet = false
    boolean showHelp = false
    Map<String, Object> modifyMatchMaps = [:]
    Map<String, Map<String, Object>> modifyTasks = [:]
    List<String> lifecycle = [];
    List<MonitorInstrumentation> monitors = [];
    Map<String, List<MethodCell>> instrumentation = [:]
}