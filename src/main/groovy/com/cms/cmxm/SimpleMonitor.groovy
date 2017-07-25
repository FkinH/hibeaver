package com.cms.cmxm;

/**
 * Author: jinghao fkinh26@gmail.com
 * Date: 2017/7/21
 */

class SimpleMonitor {
    String clz;
    List<MethodCell> cells;

    SimpleMonitor(){
    }

    SimpleMonitor(String clz, List<MethodCell> cells){
        this.clz = clz;
        this.cells = cells;
    }

}
