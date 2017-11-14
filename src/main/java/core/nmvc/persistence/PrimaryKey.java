package core.nmvc.persistence;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.di.exceptions.NoPrimaryKeyException;

public class PrimaryKey implements KeyValue {

	private final static Logger log = LoggerFactory.getLogger(PrimaryKey.class);
	private final static int PRIMARY_KEY_COLUMN_INDEX = 1;
	private Object object;

	public PrimaryKey(Object o) {
		Class<?> clazz = o.getClass();

		Field key = Arrays.asList(clazz.getDeclaredFields()).stream().peek(f -> f.setAccessible(true))
				.filter(f -> f.isAnnotationPresent(core.annotation.PrimaryKey.class)).findFirst()
				.orElseThrow(() -> new NoPrimaryKeyException(clazz));

		try {
			this.object = key.get(o);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public Object getKeyObject() {
		return this.object;
	}

	@Override
	public int getColumnIndex() {
		return PRIMARY_KEY_COLUMN_INDEX;
	}


	@Override
	public String toString() {
		return String.format("Primary Key object = %s order in column = 1", this.object instanceof String ? this.object.toString() : this.object.getClass().getName());

	}

	@Override
	public int compareTo(KeyValue o) {
		return 0;
	}

}
