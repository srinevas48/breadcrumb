package com.valicpublic.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

import javax.script.Bindings;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.scripting.WCMBindingsConstants;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
@RunWith(JUnitPlatform.class)
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ConnectSocialDataProviderTest {
	
	private final AemContext aemContext = new  AemContext(ResourceResolverType.RESOURCERESOLVER_MOCK);
	ConnectSocialDataProvider connectSocialDataProvider;

	@Mock
	Bindings mockBindings;
	
	@BeforeEach
	public void setup() {
//		aemContext.load().json("/valicpublic.json", "/content/valic_public");
		aemContext.load().json("/connectsocialdataprovider.json", "/content");
		connectSocialDataProvider = new ConnectSocialDataProvider();
	}

	@Test
	public void testGetLocatioLink() throws Exception {
//		Page pagecom = aemContext.currentPage("/content/valic_public/america-canada/us_corporate/en/home");
//		Resource compResource = aemContext.currentResource("/content/valic_public/america-canada/us_corporate/en/home/jcr:content/heroimage");
		
		Page pagecom = aemContext.currentPage("/content");
		Resource compResource = aemContext.currentResource("/content/jcr:content/heroimage");
		ValueMap props = compResource.getValueMap();
		lenient().when(mockBindings.get(WCMBindingsConstants.NAME_PROPERTIES)).thenReturn(compResource.getValueMap());
		connectSocialDataProvider.init(mockBindings);
		String locationLink = connectSocialDataProvider.getLocationLink();
		assertEquals("https://home.kpmg.com/us/en/home/about/offices.html", locationLink);
	}
	
	
	@Test
	public void testGetPeopleLink() throws Exception {
//		Page pagecom = aemContext.currentPage("/content/valic_public/america-canada/us_corporate/en/home");
//		Resource compResource = aemContext.currentResource("/content/valic_public/america-canada/us_corporate/en/home/jcr:content/heroimage");
		
		Page pagecom = aemContext.currentPage("/content");
		Resource compResource = aemContext.currentResource("/content/jcr:content/heroimage");
		ValueMap props = compResource.getValueMap();
		lenient().when(mockBindings.get(WCMBindingsConstants.NAME_PROPERTIES)).thenReturn(compResource.getValueMap());
		connectSocialDataProvider.init(mockBindings);
		String peopleLink = connectSocialDataProvider.getPeopleLink();
		assertEquals("http://kpmg.com", peopleLink);
	}
	
	@Test
	public void testGetrssLink() throws Exception {
//		Page pagecom = aemContext.currentPage("/content/valic_public/america-canada/us_corporate/en/home");
//		Resource compResource = aemContext.currentResource("/content/valic_public/america-canada/us_corporate/en/home/jcr:content/heroimage");
		
		Page pagecom = aemContext.currentPage("/content");
		Resource compResource = aemContext.currentResource("/content/jcr:content/heroimage");
		ValueMap props = compResource.getValueMap();
		lenient().when(mockBindings.get(WCMBindingsConstants.NAME_PROPERTIES)).thenReturn(compResource.getValueMap());
		connectSocialDataProvider.init(mockBindings);
		String rssLink = connectSocialDataProvider.getRSSLink();
		assertEquals("/content/valicpublic.html", rssLink);
	}
}
