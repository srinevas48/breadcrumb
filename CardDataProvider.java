ackage us.kpmg.core6x.dataproviders.components;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import us.kpmg.core6x.dataproviders.utilities.ContentTypeUtility;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CardDataProvider extends WCMUsePojo {

    Map<String, String> page = new HashMap<String, String>();
    Page pathPage;
    String path = "";

    String contentType;
    String colorClass;
    String contentTypeDisplayName;
    String imageBox;
    String featuredBox;
    String featuredClass = "";
    String hideCategoryIconClass = "";
    String icon;
    String bgcolorClass = "";
    String colorHex = "";
    String relAttr= "";
    String linkExtension= "";
    String readmoreTextType;
    String contentCompletion="";
    String debugOutput;
    private String orderBy = "";
    private String tagOptions;
    private String tagsQueryString = "";
    private TagPojo tags;
    private String eventSort;
    private static final String AUTOCOMPLETE = "auto-complete";
    private static final String DESCRIPTION = "description";
    private static final String TITLE = "title";
    private static final String MANUALLYCOMPLETE = "manually-complete";
    private static final String FALSEVAR = "false";

    @Override
    public void activate() throws NullPointerException {
        contentCompletion = getProperties().get("contentCompletion", "");
        orderBy = getProperties().get("dateFilterBy", String.class);
        tagOptions = getProperties().get("tags",String.class);
        // resolve path to page and resolve the content type and name and such
        if(contentCompletion.equalsIgnoreCase(AUTOCOMPLETE) || contentCompletion.equalsIgnoreCase("") ) {
            getPath();
            linkExtension =".html";
            if(contentCompletion.equalsIgnoreCase(AUTOCOMPLETE)) {
                imageBox = getProperties().get("imageBox", String.class) == null ? FALSEVAR : getProperties().get("imageBox", String.class);
            }else {
                if (path.equalsIgnoreCase(getCurrentPage().getPath())) {
                    imageBox = FALSEVAR;
                }else{
                    imageBox = "true";
                }
            }
            featuredBox = getProperties().get("featuredBox", FALSEVAR);
            hideCategoryIconClass = getProperties().get("hideCategoryIcon", null) != null ? "hide" : "show";
            Resource pageResource = getResourceResolver().resolve(path);
            pathPage = pageResource.adaptTo(Page.class);
            if(pathPage == null) {
                page.put("path", "checkpage");
            }else{
                page.put("path", getPath() +".img.png");
                page.put(DESCRIPTION, pathPage.getDescription());
                page.put(TITLE, pathPage.getTitle());
                resolveContentTypeFromPath();
            }
        } else if(contentCompletion.equalsIgnoreCase(MANUALLYCOMPLETE)) {
            getPath();
            imageBox = "true";
            featuredBox = FALSEVAR;
            hideCategoryIconClass = "show";
            page.put("path", getProperties().get("photoPath", String.class));   // path for image to show
            page.put(TITLE, getProperties().get(TITLE, String.class));
            page.put(DESCRIPTION, getProperties().get(DESCRIPTION, String.class));
            resolveContentTypeFromPath();
        } else if(contentCompletion.equalsIgnoreCase("latest")) {
            if (orderBy.equalsIgnoreCase("upcoming")) {
                eventSort = "upcoming";
            } else if (orderBy.equalsIgnoreCase("ondemand")) {
                eventSort = "past";
            } else {
                eventSort = "";
            }
            contentType = getProperties().get("contentType",String.class);
            createTagPojos();
            buildComponentTagsQuery();
        }

    }

    private void createTagPojos() {
        if(tagOptions != null) {
            TagManager tagManager = getResourceResolver().adaptTo(TagManager.class);
            Tag t = tagManager.resolve(tagOptions);
            String metaName = tagOptions.replace(':', '-');
            String encodedMetaName = metaName.replace("/","~2F");
            String tagName = t.getName().replace("-"," ");
            tags = new TagPojo();
            tags.setName(tagName);
            tags.setMetaName(metaName);
            tags.setEncodedMetaName(encodedMetaName);
        }
    }
    private void buildComponentTagsQuery() {

        if (tags != null) {
            TagPojo tag = tags;
            tagsQueryString +=tag.getEncodedMetaName() +  "|";
        }

        if(!tagsQueryString.equals("") && tagsQueryString.endsWith("|")) {
            tagsQueryString=tagsQueryString.substring(0,tagsQueryString.length()-1);
        }
    }

    private void resolveContentTypeFromPath() {

        contentType = contentCompletion.equalsIgnoreCase(AUTOCOMPLETE) || contentCompletion.equalsIgnoreCase("") ?  pathPage.getProperties().get("contentType", String.class) :
                getProperties().get("categories", "");
        contentTypeDisplayName = ContentTypeUtility.getContentTypeDisplayNameFromContentType(contentType);
        icon = ContentTypeUtility.getIconSVGFromContentType(contentType);
        featuredClass = ContentTypeUtility.getFeaturedColorClass(featuredBox, imageBox) ;

        colorClass = ContentTypeUtility.getColorClassFromContentType(contentType, featuredBox, imageBox);
        bgcolorClass = ContentTypeUtility.getBgColorClassFromContentType(contentType, featuredBox, imageBox);
        colorHex = ContentTypeUtility.getColorHexFromContentType(contentType);
        readmoreTextType = contentCompletion.equalsIgnoreCase(AUTOCOMPLETE) || contentCompletion.equalsIgnoreCase("") ?
                ContentTypeUtility.getReadMoreTextType(contentType) : getProperties().get("linklabel",String.class);
    }

    public Map<String, String> getPage() {
        return page;
    }

    public String getPath() {

        String defaultPath=get("path", String.class);

        if(contentCompletion.equalsIgnoreCase(AUTOCOMPLETE)) {
            path = getProperties().get("cardPath", String.class) == null ? "" : getProperties().get("cardPath", String.class);
            return path;
        }
        if (contentCompletion.equalsIgnoreCase(MANUALLYCOMPLETE)) {
            path = getProperties().get("card-absolutelink", String.class) == null ? "" : getProperties().get("card-absolutelink",String.class);
            return path;
        }

        if (path.equalsIgnoreCase("")) {
            path = defaultPath;
        }

        if(path == null) {
            path = "";
        }

        return path;
    }

    public String getContentTypeDisplayName() {
        return contentTypeDisplayName;
    }

    public String getContentType() {
        return contentType;
    }

    public String getColorClass() {
        return colorClass;
    }

    public String getImageBox() {
        return imageBox;
    }

    public String getFeaturedBox() {
        return featuredBox;
    }

    public String getFeaturedClass() {
        return featuredClass;
    }

    public String getIcon() {
        return icon;
    }

    public String getBgcolorClass() {
        return bgcolorClass;
    }

    public String getHideCategoryIconClass() {
        return hideCategoryIconClass;
    }

    public String getColorHex() {
        return colorHex;
    }

    public String getReadmoreTextType() {
        return readmoreTextType;
    }

    public String getContentCompletion() {
        return contentCompletion ;
    }

    public String getRelAttr() {
        if(contentCompletion.equalsIgnoreCase(MANUALLYCOMPLETE)) {
            relAttr= "noopener noreferrer";
        }
        return relAttr;
    }

    public String getLinkExtension() {
        return linkExtension;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDebugOutput() {

        return "Debug {bgcolorClass:"+ getBgcolorClass() + ", colorClass:" + getColorClass() + ", colorHex: " +
                getColorClass() + ", contentCompletion : " + getContentCompletion() + ", contentType : " + getContentType() +
                ", contentTypeDisplay :" + getContentTypeDisplayName() + ", featuredBox : " + getFeaturedBox() +
                ",featuredClass : " + getFeaturedClass() + ", hideCateogyIconClass:" + getHideCategoryIconClass() +
                ", icon:" + getIcon() + ", imageBox :" + getImageBox() + ", path: " + getPath() +
                ", readmoreTextType: " + getReadmoreTextType() + ",debugPath=" + "}";
    }
    public TagPojo getTags() {
        return tags;
    }
    public String getTagsQueryString(){return tagsQueryString;}
    public String getOrderBy() { return orderBy; }
    public String getEventSort() { return eventSort; }
}
