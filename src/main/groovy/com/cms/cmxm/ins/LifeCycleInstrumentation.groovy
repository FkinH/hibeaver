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

    int type; // 0:application, 1:activity

    public LifeCycleInstrumentation(String clz){
        this(null, clz, 0)
    }

    public LifeCycleInstrumentation(String section, String clz){
        this(section, clz, 1)
    }

    public LifeCycleInstrumentation(String section, String clz, int type) {
        super();
        this.clz = clz
        this.type = type
        List<MethodCell> cellList = new ArrayList<>();
        switch (type){
            case 0: // application
                MethodCell attachBaseContext = new MethodCell('attachBaseContext', '(Landroid/content/Context;)V', 0, {
                    ClassVisitor cv, int access, String name, String desc, String signature, String[] exceptions ->
                        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
                        MethodVisitor adapter = new SimpleAdapter(methodVisitor) {
                            @Override
                            def void onStart() {
                                methodVisitor.visitLdcInsn(1);
                                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "bruce/com/testhibeaver/Agent", "onAttachBaseContext", "(I)V");
                            }

                            @Override
                            def void onEnd() {
                                methodVisitor.visitLdcInsn(2);
                                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "bruce/com/testhibeaver/Agent", "onAttachBaseContext", "(I)V");
                            }
                        }

                        return adapter;
                });

                MethodCell create = new MethodCell('onCreate', '()V', 0, {
                    ClassVisitor cv, int access, String name, String desc, String signature, String[] exceptions ->
                        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
                        MethodVisitor adapter = new SimpleAdapter(methodVisitor) {
                            @Override
                            def void onStart() {
                                methodVisitor.visitLdcInsn(1);
                                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "bruce/com/testhibeaver/Agent", "onAppOnCreate", "(I)V");
                            }

                            @Override
                            def void onEnd() {
                                methodVisitor.visitLdcInsn(2);
                                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "bruce/com/testhibeaver/Agent", "onAppOnCreate", "(I)V");
                            }
                        }

                        return adapter;
                });
                cellList.add(attachBaseContext);
                cellList.add(create);
                break
            case 1: // activity
                MethodCell create = new MethodCell('onCreate', null, 0, {
                    ClassVisitor cv, int access, String name, String desc, String signature, String[] exceptions ->
                        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
                        MethodVisitor adapter = new SimpleAdapter(methodVisitor) {
                            @Override
                            def void onStart() {
                                methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
                                methodVisitor.visitLdcInsn(section);
                                methodVisitor.visitLdcInsn(1);
                                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "bruce/com/testhibeaver/Agent", "onCreate", "(Ljava/lang/Object;Ljava/lang/String;I)V");
                            }

                            @Override
                            def void onEnd() {
                                methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
                                methodVisitor.visitLdcInsn(section);
                                methodVisitor.visitLdcInsn(2);
                                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "bruce/com/testhibeaver/Agent", "onCreate", "(Ljava/lang/Object;Ljava/lang/String;I)V");
                            }
                        }

                        return adapter;
                });
                MethodCell resume = new MethodCell("onResume", '()V', 0, {
                    ClassVisitor cv, int access, String name, String desc, String signature, String[] exceptions ->
                        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
                        MethodVisitor adapter = new SimpleAdapter(methodVisitor) {
                            @Override
                            def void onStart() {
                                methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
                                methodVisitor.visitLdcInsn(section);
                                methodVisitor.visitLdcInsn(1);
                                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "bruce/com/testhibeaver/Agent", "onResume", "(Ljava/lang/Object;Ljava/lang/String;I)V");
                            }

                            @Override
                            def void onEnd() {
                                methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
                                methodVisitor.visitLdcInsn(section);
                                methodVisitor.visitLdcInsn(2);
                                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "bruce/com/testhibeaver/Agent", "onShowEnd", "(Ljava/lang/Object;Ljava/lang/String;I)V");
                            }
                        }
                        return adapter;
                })

                cellList.add(create);
                cellList.add(resume);
                break
        }

        this.list = cellList;
        this.cells = cellList;
    }
}
