package com.bryansharp.gradle.hibeaver

import com.cms.cmxm.MethodCell

public class HiBeaverParams {
    boolean enableModify = true
    boolean watchTimeConsume = false
    boolean keepQuiet = false
    boolean showHelp = true
    Map<String, Object> modifyMatchMaps = [:]
    Map<String, Map<String, Object>> modifyTasks = [:]
    List<String> lifeCircles = [];
    Map<String, List<MethodCell>> monitors = [:]
}