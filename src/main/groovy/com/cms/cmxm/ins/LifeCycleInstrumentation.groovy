package com.cms.cmxm.ins

import com.cms.cmxm.MethodCell
import com.cms.cmxm.SimpleAdapter
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
/**
 * Author: jinghao fkinh26@gmail.com
 * Date: 2017/7/21
 */

class LifeCycleInstrumentation extends BaseXMInstrumentation {

    String clz;

    List<MethodCell> list;

    public LifeCycleInstrumentation(String clz) {
        super();
        this.clz = clz
        MethodCell cell = new MethodCell('onCreate', null, 0, {
            ClassVisitor cv, int access, String name, String desc, String signature, String[] exceptions ->
                MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
                MethodVisitor adapter = new SimpleAdapter(methodVisitor) {
                    @Override
                    def void onStart() {
                        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
                        methodVisitor.visitLdcInsn(name);
                        methodVisitor.visitLdcInsn(1);
                        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "bruce/com/testhibeaver/Agent", "onCreate", "(Ljava/lang/Object;Ljava/lang/String;I)V");
                    }

                    @Override
                    def void onEnd() {
//                        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "bruce/com/testhibeaver/Agent", "onVisitEnd", "()V");
                    }
                }
//                MethodVisitor adapter = new MethodLogAdapter(mv) {
//                    @Override
//                    void visitCode() {
//                        super.visitCode();
////                                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "bruce/com/testhibeaver/Agent", "onMonitors", "()V");
////                                methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
//                        mv.visitLdcInsn(name);
//                        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "bruce/com/testhibeaver/Agent", "test", "(Ljava/lang/Object;)V");
//                    }
//                }
                return adapter;
        });
        MethodCell resume = new MethodCell("test", '()V', 0, {
            ClassVisitor cv, int access, String name, String desc, String signature, String[] exceptions ->
                MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
                MethodVisitor adapter = new SimpleAdapter(methodVisitor) {
                    @Override
                    def void onStart() {
                        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
                        methodVisitor.visitLdcInsn(name);
                        methodVisitor.visitLdcInsn(1);
                        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "bruce/com/testhibeaver/Agent", "onResume", "(Ljava/lang/Object;Ljava/lang/String;I)V");
                    }

                    @Override
                    def void onEnd() {
                        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
                        methodVisitor.visitLdcInsn(name);
                        methodVisitor.visitLdcInsn(1);
                        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "bruce/com/testhibeaver/Agent", "onShowEnd", "(Ljava/lang/Object;Ljava/lang/String;I)V");
                    }

//                    @Override
//                    void visitCode() {
//                        super.visitCode();
////                                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "bruce/com/testhibeaver/Agent", "onMonitors", "()V");
////                                methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
//
//                    }
                }
                return adapter;
        })
        List<MethodCell> cellList = new ArrayList<>();
        cellList.add(cell);
        cellList.add(resume);

        this.list = cellList;
        this.cells = cellList;
    }
}
