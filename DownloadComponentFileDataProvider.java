package us.kpmg.core6x.dataproviders.components;

import com.adobe.cq.sightly.WCMUsePojo;
import us.kpmg.core6x.dataproviders.utilities.ContentTypeUtility;

/**
 * Created by trasmussen on 8/23/17.
 */
public class DownloadComponentFileDataProvider extends WCMUsePojo {
    String filepath, fileName;
    @Override
    public void activate() {
        filepath =  get("file", String.class);
        fileName = filepath.split("/")[filepath.split("/").length - 1];
    }

    public String getFilepath() {
        return  filepath;
    }

    public String getFileName() {
        return  fileName;
    }
}
