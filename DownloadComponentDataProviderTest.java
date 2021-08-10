package com.valicpublic.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
class DownloadComponentDataProviderTest {

	DownloadComponentDataProvider downloadComponentDataProvider;

	
	@Mock
	Bindings mockBindings;
	
	@BeforeEach
	public void setup() {
		downloadComponentDataProvider = new DownloadComponentDataProvider();
	}
	
	@Test
	public void testGetLocatioLink() throws Exception {
		lenient().when(mockBindings.get("contentType")).thenReturn("/contet/public");
		downloadComponentDataProvider.init(mockBindings);
		String link = downloadComponentDataProvider.getColorClass();
		assertEquals("colorClass", downloadComponentDataProvider.getColorClass());
		assertEquals("colorHex", downloadComponentDataProvider.getColorHex());
		assertEquals("contentTypeDisplayName", downloadComponentDataProvider.getContentTypeDisplayName());
		assertEquals("/contet/public", downloadComponentDataProvider.getContentType());
		assertEquals("icon", downloadComponentDataProvider.getIcon());

	}
	
}
