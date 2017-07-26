package com.cms.cmxm.core

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.bryansharp.gradle.hibeaver.utils.DataHelper
import com.bryansharp.gradle.hibeaver.utils.Log
import com.bryansharp.gradle.hibeaver.utils.ModifyFiles
import com.bryansharp.gradle.hibeaver.utils.Util
import org.gradle.api.Plugin
import org.gradle.api.Project

class CMXMPluginImpl implements Plugin<Project> {
    @Override
    void apply(Project project) {
        println ":applied CMXM"
        project.extensions.create('xmconfig', XMConfig)
        Util.setProject(project)
        try {
            if(Class.forName("com.android.build.gradle.BaseExtension")){
                BaseExtension android = project.extensions.getByType(BaseExtension)
                if (android instanceof LibraryExtension) {
                    DataHelper.ext.projectType = DataHelper.TYPE_LIB;
                } else if (android instanceof AppExtension) {
                    DataHelper.ext.projectType = DataHelper.TYPE_APP;
                } else {
                    DataHelper.ext.projectType = -1
                }
                if (DataHelper.ext.projectType != -1) {
                    registerTransform(android)
                }
            }
        } catch (Exception e) {
            DataHelper.ext.projectType = -1
        }
        initDir(project);
        project.afterEvaluate {
            Log.setQuiet(project.xmconfig.keepQuiet);
            Log.setShowHelp(project.xmconfig.showHelp);
            Log.logHelp();
            Map<String, Map<String, Object>> taskMap = project.xmconfig.modifyTasks;
            if (taskMap != null && taskMap.size() > 0) {
                generateTasks(project, taskMap);
            }
        }
    }

    def static registerTransform(BaseExtension android) {
        CMXMInjectTransform transform = new CMXMInjectTransform()
        android.registerTransform(transform)
    }

    static void initDir(Project project) {
        if (!project.buildDir.exists()) {
            project.buildDir.mkdirs()
        }
        File hiBeaverDir = new File(project.buildDir, "CMXM")
        if (!hiBeaverDir.exists()) {
            hiBeaverDir.mkdir()
        }
        File tempDir = new File(hiBeaverDir, "temp")
        if (!tempDir.exists()) {
            tempDir.mkdir()
        }
        DataHelper.ext.hiBeaverDir = hiBeaverDir
        DataHelper.ext.hiBeaverTempDir = tempDir
    }

    def static generateTasks(Project project, Map<String, Map<String, Object>> taskMap) {
        project.task("cmxmModifyFiles") << {
            ModifyFiles.modify(taskMap)
        }
    }
}