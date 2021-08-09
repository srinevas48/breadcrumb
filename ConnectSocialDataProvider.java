package us.kpmg.core6x.dataproviders.components;

3

4

import com.adobe.cq.sightly. WCMUsePojo;

import us.kpmg.core6x.dataproviders.utilities.UrlUtility;

/**

* Data provider for the "Connect with Us" component.

*/

public class ConnectSocialDataProvider extends WCMUsePojo

private static final String LOCATION_LINK_PARAM_NAME = "location Link";

private static final String PEOPLE_LINK_PARAM_NAME="peopleLink"; private static final String RSS_LINK_PARAM_NAME = "rssLink";

private static final String DEFAULT_LOCATION_LINK = | | "https://home.kpmg.com/us/en/home/about/offices.html";

private static final String DEFAULT_PEOPLE_LINK="http://kpmg.com";

private String locationLink;

private String peoplelink;

private String rssLink;

@Override

public void activate() throws Exception {

locationLink = UrlUtility.getURL ( getProperties ().get (LOCATION_LINK_PARAM_NAME, DEFAULT_LOCATION_LINK));

peopleLink = UrlUtility.getURL( getProperties ().get(PEOPLE_LINK_PARAM_NAME, DEFAULT_PEOPLE LINK));

rssLink = UrlUtility.getURL(

getProperties ().get (RSS_LINK_PARAM_NAME, ""));

I

}

public String getLocationLink() {

return location Link;

}

public String getPeopleLink() { return peoplelink;

}

public String getRSSLink() {

return rssLink;

}
}
