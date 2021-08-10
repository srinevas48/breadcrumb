package com.valicpublic.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
//import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.valicpublic.constants.Constants;
import com.valicpublic.mocks.util.AEMContextAndMockitoUtil;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@RunWith(JUnitPlatform.class)
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class HeroSignInHandlerTest2 {

	public final AemContext aemContext = new AemContext(ResourceResolverType.RESOURCERESOLVER_MOCK);
	private static final Logger LOGGER = LoggerFactory.getLogger(HeroSignInHandlerTest2.class);

	private static ConnectSocialDataProvider connectSocialDataProvider;
	
	@BeforeEach
	public void setUp() throws Exception {

		aemContext.load().json("/valicpublic.json", "/content/valic_public");
		MockitoAnnotations.initMocks(this);
		connectSocialDataProvider = Mockito.spy(new ConnectSocialDataProvider());
		aemContext.currentPage("/content/valic_public/america-canada/us_corporate/en/home");
		aemContext.currentResource("/content/valic_public/america-canada/us_corporate/en/home/jcr:content/heroimage");
		
		Page currentPage = aemContext.currentPage();
		Resource currentResource = Mockito.spy(aemContext.currentResource());
		
		ValueMap properties = currentResource.adaptTo(ValueMap.class);

		Mockito.lenient().doReturn(properties).when(connectSocialDataProvider).getProperties();
	}

	@Test
	public final void testgetLocationLink() throws Exception {
		connectSocialDataProvider.activate();
		
		assertEquals("https://home.kpmg.com/us/en/home/about/offices.html",
				connectSocialDataProvider.getLocationLink());
	}

	@Test
	public final void testgetPeopleLink() throws Exception {
		connectSocialDataProvider.activate();
		
		assertEquals("http://kpmg.com",
				connectSocialDataProvider.getPeopleLink());
	}
	@Test
	public final void testgetRSSLink() throws Exception {
		connectSocialDataProvider.activate();
		
		assertEquals("/content/valicpublic.html",
				connectSocialDataProvider.getRSSLink());
	}
}
