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

class ReceiverInstrumentation extends BaseXMInstrumentation{

    String clz;

    List<MethodCell> list;

    public ReceiverInstrumentation(String clz){
        super();
        this.clz = clz;
        List<MethodCell> cellList = new ArrayList<>();
        MethodCell attachBaseContext = new MethodCell('onReceive', '(Landroid/content/Context;Landroid/content/Intent;)V', 0, {
            ClassVisitor cv, int access, String name, String desc, String signature, String[] exceptions ->
                MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
                MethodVisitor adapter = new SimpleAdapter(methodVisitor) {
                    @Override
                    def void onStart() {
                        methodVisitor.visitVarInsn(Opcodes.ALOAD, 1);
                        methodVisitor.visitVarInsn(Opcodes.ALOAD, 2);
                        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "bruce/com/testhibeaver/Agent", "hookXM", "(Ljava/lang/Object;Ljava/lang/Object;)V");
                    }

                    @Override
                    def void onEnd() {
                        methodVisitor.visitVarInsn(Opcodes.ALOAD, 1);
                        methodVisitor.visitVarInsn(Opcodes.ALOAD, 2);
                        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "bruce/com/testhibeaver/Agent", "hookXM", "(Ljava/lang/Object;Ljava/lang/Object;)V");
                    }
                }

                return adapter;
        });
        cellList.add(attachBaseContext);
        this.cells = cellList;
    }

}
