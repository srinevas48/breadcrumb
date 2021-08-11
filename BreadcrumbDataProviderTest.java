package com.valicpublic.handlers;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
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
class BreadcrumbDataProviderTest {

	private final AemContext aemContext = new  AemContext(ResourceResolverType.RESOURCERESOLVER_MOCK);
	BreadcrumbDataProvider breadcrumbDataProvider;
	
	@Mock
	Bindings mockBindings;
	
	@Mock
	ValueMap props;
	
	@BeforeEach
	public void setup() {
		aemContext.load().json("/valicpublic.json", "/content/valic_public");
		breadcrumbDataProvider = new BreadcrumbDataProvider();
	}
	
	@Test
	public void testgetBreadCrumbModels1() throws Exception {
		Page page = aemContext.currentPage("/content/valic_public/america-canada/us_corporate/en/home");
		Resource compResource = aemContext.currentResource("/content/valic_public/america-canada/us_corporate/en/home");
		lenient().when(mockBindings.get(WCMBindingsConstants.NAME_CURRENT_PAGE)).thenReturn(aemContext.currentPage());
		lenient().when(mockBindings.get(WCMBindingsConstants.NAME_PROPERTIES)).thenReturn(compResource.getValueMap());
		lenient().when(mockBindings.containsValue("kpmg-core-6x/components/templates/home")).thenReturn(true);
		
		breadcrumbDataProvider.init(mockBindings);
		
		assertNotNull(breadcrumbDataProvider.getBreadCrumbModels());
	}
	
	@Test
	public void testgetBreadCrumbModels2() throws Exception {
		Page currentPage = aemContext.currentPage("/content/valic_public/america-canada/us_corporate/en/about-valic");
		Resource compResource = aemContext.currentResource("/content/valic_public/america-canada/us_corporate/en/about-valic");
		
		lenient().when(mockBindings.get(WCMBindingsConstants.NAME_CURRENT_PAGE)).thenReturn(aemContext.currentPage());
		lenient().when(mockBindings.get(WCMBindingsConstants.NAME_PROPERTIES)).thenReturn(compResource.getValueMap());
		lenient().when(mockBindings.containsValue("kpmg-core-6x/components/templates/home")).thenReturn(true);
		
		breadcrumbDataProvider.init(mockBindings);
		
		assertNotNull(breadcrumbDataProvider.getBreadCrumbModels());
		
	}
	
	@Test
	public void testgetBreadCrumbModels3() throws Exception {
		Page currentPage = aemContext.currentPage("/content/valic_public/america-canada/us_corporate/en/contact-us");
		Resource compResource = aemContext.currentResource("/content/valic_public/america-canada/us_corporate/en/contact-us");
		
		lenient().when(mockBindings.get(WCMBindingsConstants.NAME_CURRENT_PAGE)).thenReturn(aemContext.currentPage());
		lenient().when(mockBindings.get(WCMBindingsConstants.NAME_PROPERTIES)).thenReturn(compResource.getValueMap());
		lenient().when(mockBindings.containsValue("kpmg-core-6x/components/templates/home")).thenReturn(true);
		
		breadcrumbDataProvider.init(mockBindings);
		
		assertNotNull(breadcrumbDataProvider.getBreadCrumbModels());
		
		
		  
	}


}
