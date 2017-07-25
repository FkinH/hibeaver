package com.cms.cmxm.core

import com.cms.cmxm.MethodCell

public class XMConfig {
    boolean enable = true
    boolean keepQuiet = false
    boolean showHelp = false
    Map<String, Object> modifyMatchMaps = [:]
    Map<String, Map<String, Object>> modifyTasks = [:]
    List<String> lifeCircles = [];
    Map<String, List<MethodCell>> monitors = [:]
}