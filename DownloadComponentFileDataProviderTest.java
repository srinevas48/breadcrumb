package com.valicpublic.handlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

import javax.script.Bindings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@RunWith(JUnitPlatform.class)
@ExtendWith({ MockitoExtension.class })
class DownloadComponentFileDataProviderTest {

	DownloadComponentFileDataProvider downloadComponentFileDataProvider;

	@Mock
	Bindings mockBindings;
	
	@BeforeEach
	public void setup() {
		downloadComponentFileDataProvider = new DownloadComponentFileDataProvider();
	}
	
	@Test
	public void testGetFileName() throws Exception {
		lenient().when(mockBindings.get("file")).thenReturn("C:\\work\\constants.java");
		downloadComponentFileDataProvider.init(mockBindings);
		assertEquals("C:\\work\\constants.java", downloadComponentFileDataProvider.getFilepath());
		
	}
	
	@Test
	public void testGetFilePath() throws Exception {
		lenient().when(mockBindings.get("file")).thenReturn("DownloadComponentFileDataProvider.java");
		downloadComponentFileDataProvider.init(mockBindings);
		assertEquals("DownloadComponentFileDataProvider.java", downloadComponentFileDataProvider.getFileName());
		
	}
	
}
