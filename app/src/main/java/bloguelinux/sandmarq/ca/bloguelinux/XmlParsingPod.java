package bloguelinux.sandmarq.ca.bloguelinux;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by SANDRINE.MARQUIS on 2014-12-21.
 */
public class XmlParsingPod {

    private static final String TAG = "BlogueLinux|XMLParsing";

    private String urlIn = null; // your URL here
    private String xmlContent = null;

    public String getUrl() {
        return urlIn;
    }

    public void setUrl(String url) {
        urlIn = url;
    }

    public String getXmlFromUrl(String url) {
        String xml = null;

        // return XML
        return xml;
    }

    public XmlParsingPod() {
    }
}