package us.kpmg.core6x.dataproviders.components;

import com.adobe.cq.sightly.WCMUsePojo;
import us.kpmg.core6x.dataproviders.utilities.ContentTypeUtility;

/**
 * Created by trasmussen on 8/23/17.
 */
public class DownloadComponentDataProvider extends WCMUsePojo {
    String contentType, contentTypeDisplayName, colorClass, colorHex, icon;
    @Override
    public void activate() {
        contentType =  get("contentType", String.class);
        contentTypeDisplayName = ContentTypeUtility.getContentTypeDisplayNameFromContentType(contentType);
        colorClass = ContentTypeUtility.getColorClassFromContentType(contentType);
        colorHex = ContentTypeUtility.getColorHexFromContentType(contentType);
        icon = ContentTypeUtility.getIconSVGFromContentType(contentType);
    }

    public String getContentTypeDisplayName() {
        return  contentTypeDisplayName;
    }

    public String getColorClass() {
        return  colorClass;
    }

    public String getContentType() {
        return  contentType;
    }

    public String getColorHex() {
        return  colorHex;
    }

    public String getIcon() {
        return icon;
    }
}
