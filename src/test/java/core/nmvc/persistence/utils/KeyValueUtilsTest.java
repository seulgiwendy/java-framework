package core.nmvc.persistence.utils;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.nmvc.persistence.KeyValue;
import next.model.User;

public class KeyValueUtilsTest {
	private static final Logger log = LoggerFactory.getLogger(KeyValueUtilsTest.class);

	@Test
	public void test() {
		Set<KeyValue> values = KeyValueUtils.keyValueParser(new User("javajigi", "password", "박재성", "slipp@javajigi.net"));
		values.stream().forEach(v -> log.debug(v.toString()));
		System.out.println("\n");
		values.stream().sorted().forEach(v -> log.debug(v.toString()));
		assertNotNull(values);
	}

}
