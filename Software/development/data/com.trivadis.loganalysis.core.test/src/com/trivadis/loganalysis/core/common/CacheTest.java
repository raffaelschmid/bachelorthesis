package com.trivadis.loganalysis.core.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class CacheTest {
	private int count = 0;
	private Cache<String, String> instance = new Cache<String, String>() {

		@Override
		protected String create(String k) {
			return "test";
		}

		@Override
		protected void dispose(String v) {
			count++;
		}
	};

	@Test
	public void test_get_same_instance_per_two_calls() {
		String a = instance.get("key");
		String b = instance.get("key");
		assertSame(a, b);
	}

	@Test
	public void test_invoking_dispose_per_entry() {
		instance.get("key");
		instance.dispose();
		assertEquals(1, count);
	}
	
}
