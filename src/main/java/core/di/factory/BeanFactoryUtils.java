package core.di.factory;

import static org.reflections.ReflectionUtils.getAllConstructors;
import static org.reflections.ReflectionUtils.withAnnotation;

import java.lang.reflect.Constructor;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

import core.annotation.Inject;

public class BeanFactoryUtils {

	private static final Logger log = LoggerFactory.getLogger(BeanFactoryUtils.class);

	/**
	 * 인자로 전달하는 클래스의 생성자 중 @Inject 애노테이션이 설정되어 있는 생성자를 반환
	 * 
	 * @Inject 애노테이션이 설정되어 있는 생성자는 클래스당 하나로 가정한다.
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Constructor<?> getInjectedConstructor(Class<?> clazz) {
		Set<Constructor> injectedConstructors = getAllConstructors(clazz, withAnnotation(Inject.class));
		if (injectedConstructors.isEmpty()) {
			return null;
		}
		return injectedConstructors.iterator().next();
	}

	/**
	 * 인자로 전달되는 클래스의 구현 클래스. 만약 인자로 전달되는 Class가 인터페이스가 아니면 전달되는 인자가 구현 클래스, 인터페이스인
	 * 경우 BeanFactory가 관리하는 모든 클래스 중에 인터페이스를 구현하는 클래스를 찾아 반환
	 * 
	 * @param injectedClazz
	 * @param preInstantiateBeans
	 * @return
	 * @return
	 */
	public static Class<?> findConcreteClass(Class<?> injectedClazz, Set<Class<?>> preInstantiateBeans) {
		if (!injectedClazz.isInterface()) {
			return injectedClazz;
		}

		for (Class<?> clazz : preInstantiateBeans) {
			Set<Class<?>> interfaces = Sets.newHashSet(clazz.getInterfaces());
			if (interfaces.contains(injectedClazz)) {
				return clazz;
			}
		}

		throw new IllegalStateException(injectedClazz + "인터페이스를 구현하는 Bean이 존재하지 않는다.");
	}

	public static Object instantiateClass(Constructor<?> constructor, Object[] args) {
		try {
			log.debug("something has invoked instantiateClass method.");
			return constructor.newInstance(args);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
}
