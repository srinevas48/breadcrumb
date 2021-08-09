package us.kpmg.core6x.dataproviders.components;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;
import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import us.kpmg.core6x.dataproviders.utilities.UrlUtility;

/**
 * Data provider for the KPMG Breadcrumb component
 */
public class BreadcrumbDataProvider extends WCMUsePojo {
    private static final String BREADCRUMB_MODEL_LINK = "link";
    private static final String BREADCRUMB_MODEL_LABEL = "label";
    private static final String NAV_TITLE = "navTitle";
    private final List<Map<String, Object>> breadCrumbModels = new ArrayList<>();
    @Reference
    private ResourceResolverFactory factory;
    @Override
    public void activate() throws Exception {
        buildBreadCrumbs();
    }
    public void buildBreadCrumbs() {
        Page currentPage = getCurrentPage();
        if (currentPage.getProperties().containsValue("kpmg-core-6x/components/templates/home")) {
            return;
        }
        if (currentPage.getProperties().containsValue("kpmg-core-6x/components/templates/bio")) {
            getPageData(currentPage);
            return;
        }
        getPageData(currentPage);
        while (currentPage.getParent() != null && currentPage.getParent().getPath() != null && currentPage.getParent().getPath().length() > 1) {
            if(currentPage.getProperties() != null && currentPage.getProperties().get("navRoot") != null && currentPage.getProperties().get("navRoot").toString().equalsIgnoreCase("true")) {
                    break;
            }
            currentPage = getParent(currentPage);
            getPageData(currentPage);
        }
    }
    public List<Map<String, Object>> getBreadCrumbModels() {
        return new ArrayList<>(breadCrumbModels);
    }
    private static Map<String, Object> createBreadCrumbModel(String title, String link) {
        Map<String, Object> breadCrumbModel = new HashMap<>();
        breadCrumbModel.put(BREADCRUMB_MODEL_LINK, link);
        breadCrumbModel.put(BREADCRUMB_MODEL_LABEL, title);
        return breadCrumbModel;
    }
    private void getPageData(Page currentPage) {
        breadCrumbModels.add(0, createBreadCrumbModel(getPageTitle(currentPage), currentPage.getPath()));
    }
    private String getPageTitle(Page currentPage) {
        if(currentPage.getProperties() != null && currentPage.getProperties().get(NAV_TITLE) != null) {
            String checker = currentPage.getProperties().get(NAV_TITLE).toString();
            if (checker.length() > 1) {
                return checker;
            }
        }
        return currentPage.getTitle();
    }
    private static Page getParent(Page currentPage) {

        if(currentPage.getProperties() != null && currentPage.getProperties().get("sudoParent") != null) {
            String sudoParentPath = currentPage.getProperties().get("sudoParent").toString();
            if ((sudoParentPath.length() > 1) && !(sudoParentPath.startsWith(currentPage.getPath()))) {//beware of loops and empty paths
                Page page = currentPage.getPageManager().getPage(sudoParentPath);
                if (page.isHideInNav()) {
                    return getParent(page);
                }
                return page;
            }
        }
        Page page = currentPage;
        if(page.getParent() != null) {
            page = page.getParent();
            if (page.isHideInNav()) {
                return getParent(page);
            }
        }
        return page;
    }
}
