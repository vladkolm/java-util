package info.vladkolm.utils.reflection;

import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Optional;

import static info.vladkolm.utils.reflection.AsmUtils.*;

// See http://stackoverflow.com/questions/23861619/how-to-read-lambda-expression-bytecode-using-asm
public class MethodInfo {
    final private Class<?> implClass;
    final private Method implMethod;

    public static SerializedLambda getSerializedLambda(Serializable lambda){
        Method method = ClassUtils.getMethod(lambda.getClass(), "writeReplace");
        return (SerializedLambda) ClassUtils.invokeMethod(lambda, method);
    }
    public static Method methodOf(SerializableRunnable lambda) {
        return internalMethodOf(lambda);
    }
    public static Method methodOf(SerializableConsumer<?> lambda) {
        return internalMethodOf(lambda);
    }
    public static Method methodOf(SerializableFun lambda) {
        return internalMethodOf(lambda);
    }


    MethodInfo(Serializable lambda) {
        SerializedLambda serializedLambda = getSerializedLambda(lambda);
        String implClassName = serializedLambda.getImplClass();
        implClass = ClassUtils.getClassFromStr(implClassName);
        String methodName = serializedLambda.getImplMethodName();
        String signature = serializedLambda.getImplMethodSignature();
        Class<?>[] argTypes = getClassList(signature);
        implMethod = ClassUtils.getMethod(implClass, methodName, argTypes).orElseThrow(ReflectUtilsException::new);
    }

    boolean isSynthetic() {
        return implMethod.isSynthetic();
    }
    String getMethodName() {
        return implMethod.getName();
    }
    Method getImplMethod() {
        return implMethod;
    }

    String extractNameFromLambda() {
        MethodNode methodNode = findMethodNode(implClass, getMethodName());
        MethodInsnNode methNode = findMethodInstructionNode(methodNode, false);
        return methNode.name;
    }
    Method extractMethodFromLambda() {
        MethodNode methodNode = findMethodNode(implClass, getMethodName());
        MethodInsnNode methNode = findMethodInstructionNode(methodNode, false);
        return getMethodFromMethodNode(methNode).orElseThrow(ReflectUtilsException::new);
    }

    String extractFieldNameFromLambda() {
        MethodNode methodNode = findMethodNode(implClass, getMethodName());
        FieldInsnNode instNode = (FieldInsnNode)AsmUtils.findFieldFromNode(methodNode);
        return instNode.name;
    }
    Optional<String> extractLocalNameFromLambda() {
        MethodNode methodNode = findMethodNode(implClass, getMethodName());
        return findVarableFromNode(methodNode).map(v->v.name);
    }

    static String internalNameOf(Serializable lambda) {
        MethodInfo methodInfo = new MethodInfo(lambda);
        return methodInfo.isSynthetic()? methodInfo.extractNameFromLambda(): methodInfo.getMethodName();
    }
    static Method internalMethodOf(Serializable lambda) {
        MethodInfo methodInfo = new MethodInfo(lambda);
        return methodInfo.isSynthetic()? methodInfo.extractMethodFromLambda(): methodInfo.getImplMethod();
    }




}
