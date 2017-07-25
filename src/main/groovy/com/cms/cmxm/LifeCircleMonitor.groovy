package com.cms.cmxm

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Author: jinghao fkinh26@gmail.com
 * Date: 2017/7/21
 */

class LifeCircleMonitor extends SimpleMonitor {

    String clz;

    List<MethodCell> list;

    public LifeCircleMonitor(String clz) {
        super();
        this.clz = clz
        MethodCell cell = new MethodCell("onCreate", null, 0, {
            ClassVisitor cv, int access, String name, String desc, String signature, String[] exceptions ->
                MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
                MethodVisitor adapter = new SimpleAdapter(mv) {
                    @Override
                    def void onStart() {
                        mv.visitVarInsn(Opcodes.ALOAD, 1);
                        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "bruce/com/testhibeaver/Agent", "onClick", "(Landroid/view/View;)V");
                    }

                    @Override
                    def void onEnd() {
                        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "bruce/com/testhibeaver/Agent", "onVisitEnd", "()V");
                    }

                }
                return adapter;
        })
        List<MethodCell> cellList = new ArrayList<>();
        cellList.add(cell);
        this.list = cellList;
        this.cells = cellList;
    }
}
