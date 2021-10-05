package info.vladkolm.utils.reflection;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.Method;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import static org.objectweb.asm.tree.AbstractInsnNode.METHOD_INSN;


public class AsmUtils {

	private static void throwException(Class<?> clazz, String methodName) {
		throw new ReflectUtilsException("Class "+ clazz + " does not have "+ methodName + " method");
	}


	public static java.lang.reflect.Method getMethod(Class<?> clazz, String methodName) {
		MethodNode methNode = findMethodNode(clazz, methodName);
		if(methNode == null) throwException(clazz, methodName);
		return getMethodFromNode(clazz.getCanonicalName(), methNode).orElseThrow(ReflectUtilsException::new);
	}

	private static InputStream getClassStream(Class<?> clazz) {
        String className = clazz.getName();
        String classResource = "/"+className.replaceAll("\\.", "/")+".class";
        return clazz.getResourceAsStream(classResource);
    }

	private static ClassNode getClassNode(InputStream is) {
		try {
			ClassReader cr = new ClassReader(is);
			ClassNode cn = new ClassNode();
			cr.accept(cn, 0);
			return cn;
		} catch(IOException e) {
			throw new ReflectUtilsException(e);
		}

	}

	public static MethodNode findMethodNode(Class<?> clazz, String methodName) {
		ClassNode classNode = getClassNode(getClassStream(clazz));
		return classNode.methods.stream().filter(mn -> mn.name.equals(methodName)).findFirst().orElse(null);
	}

	public static Optional<java.lang.reflect.Method> getMethodFromNode(String implClass, MethodNode mn) {
		return getMethodFromMethodNode(findMethodInstructionNode(mn, true));
	}

	public static Optional<java.lang.reflect.Method> getMethodFromMethodNode(MethodInsnNode methNode) {
		Method method = new Method(methNode.name, methNode.desc);
		String className = methNode.owner.replaceAll("/", ".");
		Class<?> clazz = ClassUtils.getClass(className);
		return ClassUtils.getMethod(clazz, methNode.name, getClassList(method));
	}
	public static Optional<java.lang.reflect.Method> getMethodFromMethodNode(Class<?> clazz, MethodNode methNode) {
		Method method = new Method(methNode.name, methNode.desc);
		return ClassUtils.getMethod(clazz, methNode.name, getClassList(method));
	}


    public static MethodInsnNode findMethodInstructionNode(MethodNode mn, boolean reverse) {
		OptionalInt found = reverse? findNode(mn.instructions, METHOD_INSN, true):
				findNode(mn.instructions, METHOD_INSN, false);
        if(!found.isPresent()) {
            throw new ReflectUtilsException("Cannot find appropriate method");
        }
        return (MethodInsnNode)mn.instructions.get(found.getAsInt());
    }

	private static OptionalInt findNode(InsnList instructions, int instruction, boolean reverse) {
		return IntStream.range(0, instructions.size())
				.map(i-> reverse? instructions.size()-1 -i: i)
				.filter(i ->instruction==instructions.get(i).getType())
				.findFirst();
	}



	private static Class<?>[] getClassList(Method method) {
		return getClasses(method.getArgumentTypes());
	}

	public static Class<?>[] getClassList(String methodSignature) {
		return getClasses(Type.getArgumentTypes(methodSignature));
	}

	private static Class<?>[] getClasses(Type[] argumentTypes) {
		return Stream.of(argumentTypes).map(AsmUtils::getClass).toArray((IntFunction<Class<?>[]>) Class[]::new);
	}



	
	private static Class<?> getClass(Type type) {
		switch(type.getSort()) {
			case Type.BOOLEAN: return boolean.class;
			case Type.CHAR: return char.class;
			case Type.BYTE: return byte.class;
			case Type.SHORT: return short.class;
			case Type.INT: return int.class;
			case Type.FLOAT: return float.class;
			case Type.LONG: return long.class;
			case Type.DOUBLE: return double.class; 
			case Type.ARRAY: return getArray(type); 
			default: return ClassUtils.getClass(type.getClassName());
		}
	}

	private static Class<?> getArray(Type type) {
		String componentDescriptor = type.getDescriptor().substring(1); 
		Type componentType = Type.getType(componentDescriptor);
		return ClassUtils.getArrayType(getClass(componentType));
	}

}
