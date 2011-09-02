package com.trivadis.loganalysis.core.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.trivadis.loganalysis.core.IContentReader;
import com.trivadis.loganalysis.core.domain.IFileDescriptor;
import com.trivadis.loganalysis.core.domain.FileDescriptor;

public class ContentReaderTest {

	private static final String TEST_FILE_NAME = "./resources/jrockit-testlog.log";

	private IContentReader contentReader;

	private IFileDescriptor descriptor;

	@Before
	public void before() {
		contentReader = new ContentReader();
		descriptor = FileDescriptor.fromFile(TEST_FILE_NAME);
	}

	@Test
	public void test_contentAsList() throws Exception {
		List<String> lines = contentReader.contentAsList(descriptor);
		assertNotNull(lines);
		assertEquals(499, lines.size());
	}

}
