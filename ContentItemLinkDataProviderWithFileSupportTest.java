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
class ContentItemLinkDataProviderWithFileSupportTest {

	ContentItemLinkDataProviderWithFileSupport contentItemLinkDataProviderWithFileSupport;

	@Mock
	Bindings mockBindings;
	
	@BeforeEach
	public void setup() {
		contentItemLinkDataProviderWithFileSupport = new ContentItemLinkDataProviderWithFileSupport();
	}
	
	@Test
	public void testGetLocatioLink() throws Exception {
		lenient().when(mockBindings.get("expath")).thenReturn("/contet/public");
		contentItemLinkDataProviderWithFileSupport.init(mockBindings);
		String link = contentItemLinkDataProviderWithFileSupport.getLink();
		assertEquals("/contet/public.html", link);
	}
	
	@Test
	public void testGetLink2() throws Exception {
		lenient().when(mockBindings.get("expath")).thenReturn("null");
		lenient().when(mockBindings.get("inpath")).thenReturn("/contet/public");
		contentItemLinkDataProviderWithFileSupport.init(mockBindings);
		String link = contentItemLinkDataProviderWithFileSupport.getLink();
		assertEquals("/contet/public.html", link);
	}
	
	@Test
	public void testGetLink3() throws Exception {
		lenient().when(mockBindings.get("expath")).thenReturn("null");
		lenient().when(mockBindings.get("file")).thenReturn("/file");
		contentItemLinkDataProviderWithFileSupport.init(mockBindings);
		String link = contentItemLinkDataProviderWithFileSupport.getLink();
		assertEquals("/file.html", link);
	}
	
	@Test
	public void testGetLink4() throws Exception {
		lenient().when(mockBindings.get("expath")).thenReturn("null");
		lenient().when(mockBindings.get("youtube")).thenReturn("something");
		contentItemLinkDataProviderWithFileSupport.init(mockBindings);
		String link = contentItemLinkDataProviderWithFileSupport.getLink();
		assertEquals("https://www.youtube.com/watch?v=something", link);
	}
}
