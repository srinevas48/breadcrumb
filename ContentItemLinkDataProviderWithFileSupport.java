package us.kpmg.core6x.dataproviders.components;

import com.adobe.cq.sightly.WCMUsePojo;
import org.apache.commons.io.FilenameUtils;
import us.kpmg.core6x.dataproviders.utilities.UrlUtility;

/**
 * Created by zlott on 9/27/2016.
 */
public class ContentItemLinkDataProviderWithFileSupport extends WCMUsePojo  {

    private String _link = "";

    @Override
    public void activate() throws Exception {
        _link += get("expath", String.class);
        if (_link.equalsIgnoreCase("null")) {
            _link = "";
            _link += get("inpath", String.class);
        }
        if (_link.equalsIgnoreCase("null")) {
            _link = "";
            _link += get("file", String.class);
        }
        if (_link.equalsIgnoreCase("null")) {
            _link = "";
            _link += get("youtube", String.class);
            if (_link.length() >= 0 && !_link.equalsIgnoreCase("null")) {
                _link = "https://www.youtube.com/watch?v=" + _link;
                return;
            }
            else {
                _link = "";
            }
        }
        String extension = FilenameUtils.getExtension(_link);
        if (_link.startsWith("/") && extension.length() > 0) {//if relative path with file extension
            return;//do nothing
        } else if (extension.length() == 0) {//if no file extension.
            _link = UrlUtility.getURL(_link);//append .html (if relative path)
        }
        //else leave as is (fully qualified links, empty links, and some gibberish)
    }

    public String getLink() {
        return _link;
    }
}
