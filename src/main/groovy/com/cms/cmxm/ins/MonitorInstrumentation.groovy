package com.cms.cmxm.ins

import com.cms.cmxm.MethodCell
import com.cms.cmxm.SimpleAdapter
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
/**
 * Author: jinghao fkinh26@gmail.com
 * Date: 2017/7/26
 */

class MonitorInstrumentation extends BaseXMInstrumentation{

    String clz;

    List<MethodCell> list;

    /// type 0:开始, 1:结束
    /// class，要插入监测代码的类名
    /// method，要插入监测代码的方法名
    /// methodDesc，要插入监测代码的方法参数描述
    /// addMthStartOrEnd，要插入到方法的开始还是结束：1表示开始，2表示结束 同type
    /// id，监测的唯一标识
    /// monitorType，监测类型，1表示时间监测，2表示内存监测
    /// endIdx，结束点序号，用于上报时顺序报到对应的字段，跟先后执行无关
    /// last，是否为最后一次统计，是则上报数据
    //  void onGradleDefineMonitorStart(String class, String method, String methodDesc, int addMthStartOrEnd, String id, int monitorType);
    //  void onGradleDefineMonitorEnd(String class, String method, String methodDesc, int addMthStartOrEnd, String id, int monitorType, int endIdx, boolean last);

    /**
     *
     * @param section id
     * @param type 0:start 1:end
     * @param clz
     * @param method
     * @param descriptor
     * @param monitor 1:time 2: mem
     * @param endIndex
     * @param last
     */
    public MonitorInstrumentation(String section, int type, String clz, String method, String descriptor, int monitor, int endIndex, boolean last){
        super();
        this.clz = clz;
        MethodCell cell = new MethodCell(method, descriptor, 0, {
            ClassVisitor cv, int access, String name, String desc, String signature, String[] exceptions ->
                MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
                MethodVisitor adapter = new SimpleAdapter(methodVisitor) {
                    @Override
                    def void onStart() {
                        if(type == 0){
//                            String class, String method, String methodDesc, int addMthStartOrEnd, String id, int monitorType
                            methodVisitor.visitLdcInsn(clz)
                            methodVisitor.visitLdcInsn(name)
                            methodVisitor.visitLdcInsn(desc)
                            methodVisitor.visitLdcInsn(type)
                            methodVisitor.visitLdcInsn(section)
                            methodVisitor.visitLdcInsn(monitor)
                            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "bruce/com/testhibeaver/Agent", "onGradleDefineMonitorStart", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;I)V");
                        }
                    }

                    @Override
                    def void onEnd() {
                        if(type == 1){
//                            String class, String method, String methodDesc, int addMthStartOrEnd, String id, int monitorType, int endIdx, boolean last
                            methodVisitor.visitLdcInsn(clz)
                            methodVisitor.visitLdcInsn(name)
                            methodVisitor.visitLdcInsn(desc)
                            methodVisitor.visitLdcInsn(type)
                            methodVisitor.visitLdcInsn(section)
                            methodVisitor.visitLdcInsn(monitor)
                            methodVisitor.visitLdcInsn(endIndex)
                            methodVisitor.visitLdcInsn(last)
                            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "bruce/com/testhibeaver/Agent", "onGradleDefineMonitorEnd", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;IIZ)V");
                        }
                    }
                }

                return adapter;
        });
        List<MethodCell> cellList = new ArrayList<>();
        cellList.add(cell);

        this.list = cellList;
        this.cells = cellList;
    }

}
