package es.rotolearn.controlImagenes;

import java.io.IOException ;
import java.util.Enumeration ;
import java.util.HashMap ;
import java.util.Iterator ;
import java.util.Map ;

 import javax.servlet.ServletRequest ;
 import javax.servlet.jsp.PageContext ;
 import javax.servlet.http.Cookie ;
 import javax.servlet.http.HttpServletRequest ;
 import javax.servlet.http.HttpServletResponse ;
import javax.servlet.http.HttpSession ;

public class ServletUtil {

	// ---------------------------------------------------------------- multipart

    public static boolean isMultipartRequest(HttpServletRequest  request) {
        String  type = request.getHeader("Content-Type");
        return (type != null) && type.startsWith("multipart/form-data");
    }

    /**
     * Sets the Vary response header to User-Agent to indicate that the page content
     * varies depending on which user agent (browser) is being used.
     */
    public static void setBrowserVary(HttpServletResponse  response) {
        response.setHeader( "Vary", "User-Agent");
    }


    // ---------------------------------------------------------------- authorization
/**
     * Decodes the "Authorization" header and retrieves the
     * user's name from it. Returns <code>null</code> if the header is not present.
     */
    public static String  getAuthUsername(HttpServletRequest  request) {
        String  header = request.getHeader("Authorization");
        if (header == null) {
            return null;
        }
        String  encoded = header.substring(header.indexOf(' ') + 1);
        String  decoded = new String (Base64.decode(encoded));
        return decoded.substring(0, decoded.indexOf(':'));
    }

    /**
     * Decodes the "Authorization" header and retrieves the
     * password from it. Returns null if the header is not present.
     */
    public static String  getAuthPassword(HttpServletRequest  request) {
        String  header = request.getHeader("Authorization");
        if (header == null) {
            return null;
        }
        String  encoded = header.substring(header.indexOf(' ') + 1);
        String  decoded = new String (Base64.decode(encoded));
        return decoded.substring(decoded.indexOf(':') +1);
    }

    /**
     * Sends correct headers to require basic authentication for the given realm.
     */
    public static void requireAuthentication(HttpServletResponse  resp, String  realm) throws IOException  {
        resp.setHeader("WWW-Authenticate", "Basic realm=\"" + realm + '\"');
        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }


    // ---------------------------------------------------------------- cookie

    /**
     * Returns cookie value from client.
     *
     * @return cookie value or <code>null</code> if cookie with specified name doesn't exist.
     */
    public static Cookie  getCookie(HttpServletRequest  request, String  cookieName) {
        Cookie [] cookies = request.getCookies();
        if (cookies != null) {
            Cookie  cookie;
            for (int i = 0; i < cookies.length; i++) {
                cookie = cookies[i];
                if (cookie.getName().equals(cookieName)) {
                    return cookie;
                }
            }
         }
         return null;
     }


     // ---------------------------------------------------------------- request/session


     /**
      * Return HTTP request parameter as String or String[].
      */
     public static Object  getRequestParameter(ServletRequest  request, String  name) {
         String [] values = request.getParameterValues(name);
         if (values == null) {
             return null;
         }
         if (values.length == 1) {
             return values[0];
         }
         return values;
     }


     /**
      * Transfer attributes from a map to request.
      */
     public static void setRequestAttributes(ServletRequest  request, Map  attributes) {
         Iterator  iterator = attributes.keySet().iterator();
         while (iterator.hasNext()) {
             String  key = (String ) iterator.next();
             request.setAttribute(key, attributes.get(key));
         }
     }

     /**
      * Returns a new map containing request attributes.
      */
     public static Map  getRequestAttributes(ServletRequest  request) {
         Map  map = new HashMap ();
         loadRequestAttributes(map, request);
         return map;
     }

     public static void loadRequestAttributes(Map  map, ServletRequest  request) {
         Enumeration  names = request.getAttributeNames();
         while (names.hasMoreElements()) {
             String  name = (String ) names.nextElement();
             map.put(name, request.getAttribute(name));
         }
     }

     /**
      * Returns a new map containing request parameters. Request parameter may
      * be either String or String[].
      * @see #getRequestParameter(ServletRequest, String)
      */
     public static Map  getRequestParameters(ServletRequest  request) {
         Map  map = new HashMap ();
         loadRequestParameters(map, request);
         return map;
     }

     public static void loadRequestParameters(Map  map, ServletRequest  request) {
         Enumeration  names = request.getParameterNames();
         while (names.hasMoreElements()) {
             String  name = (String ) names.nextElement();
             map.put(name, getRequestParameter(request, name));
         }
     }

     /**
      * Loads session attributes into a map.
      */
     public static void loadSessionAttributes(Map  destination, HttpSession  session) {
         Enumeration  names = session.getAttributeNames();
         while (names.hasMoreElements()) {
             String  name = (String ) names.nextElement();
             destination.put(name, session.getAttribute(name));
         }
     }

     public static Map  getSessionAttributes(HttpSession  session) {
         Map  map = new HashMap ();
         loadSessionAttributes(map, session);
         return map;
     }

     public static void setSessionAttributes(HttpSession  session, Map  attributes) {
         Iterator  iterator = attributes.keySet().iterator();
         while (iterator.hasNext()) {
             String  key = (String ) iterator.next();
             session.setAttribute(key, attributes.get(key));
         }
     }


     // ---------------------------------------------------------------- get value

     /**
      * Returns non-null attribute value. All scopes are examined in the
      * following order: page (if exist), request, session, application.
      */
     public static Object  getAttributeValue(HttpServletRequest  request, String  name, PageContext  page) {
         Object  value;
         if (page != null) {
             value = page.getAttribute(name);
             if (value != null) {
                 return value;
             }
         }
         value = request.getAttribute(name);
         if (value != null) {
             return value;
         }
         value = request.getSession().getAttribute(name);
         if (value != null) {
             return value;
         }
         value = request.getSession().getServletContext().getAttribute(name);
         return value;
     }

     public static Object  getAttributeValue(HttpServletRequest  request, String  name) {
         return getAttributeValue(request, name, null);
     }

     /**
      * Returns non-null value of property/attribute. Scopes are examined in the following
      * order: page (if exist), request, request parameters, session, application.
      */
     public static Object  getValue(HttpServletRequest  request, String  name, PageContext  page) {
         Object  value;
         if (page != null) {
             value = page.getAttribute(name);
             if (value != null) {
                 return value;
             }
         }
         value = request.getAttribute(name);
         if (value != null) {
             return value;
         }
         value = request.getParameter(name);
         if (value != null) {
             return value;
         }
         value = request.getSession().getAttribute(name);
         if (value != null) {
             return value;
         }
         value = request.getSession().getServletContext().getAttribute(name);
         return value;
     }

     public static Object  getValue(HttpServletRequest  request, String  name) {
         return getValue(request, name, null);
     }


     // ---------------------------------------------------------------- resolve url


     /**
      * Valid characters in a scheme, as specified by RFC 1738.
      */
     public static final String  VALID_SCHEME_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+.-";

     /**
      * Returns <code>true</code> if current URL is absolute, <code>false</code> otherwise.
      */
     public static boolean isAbsoluteUrl(String  url) {
         if (url == null) { // a null URL is not absolute
 return false;
         }
         int colonPos; // fast simple check first
 if ((colonPos = url.indexOf(':')) == -1) {
             return false;
         }

         // if we DO have a colon, make sure that every character
 // leading up to it is a valid scheme character
 for (int i = 0; i < colonPos; i++) {
             if (VALID_SCHEME_CHARS.indexOf(url.charAt(i)) == -1) {
                 return false;
             }
         }
         return true;
     }

     /**
      * Strips a servlet session ID from <code>url</code>. The session ID
      * is encoded as a URL "path parameter" beginning with "jsessionid=".
      * We thus remove anything we find between ";jsessionid=" (inclusive)
      * and either EOS or a subsequent ';' (exclusive).
      */
     public static String  stripSession(String  url) {
         StringBuffer  u = new StringBuffer (url);
         int sessionStart;
         while ((sessionStart = u.toString().indexOf(";jsessionid=")) != -1) {
             int sessionEnd = u.toString().indexOf(';', sessionStart + 1);
             if (sessionEnd == -1) {
                 sessionEnd = u.toString().indexOf('?', sessionStart + 1);
             }
             if (sessionEnd == -1) {
                 sessionEnd = u.length();
             }
             u.delete(sessionStart, sessionEnd);
         }
         return u.toString();
     }

     public static String  resolveUrl(String  url, PageContext  pageContext) {
         return resolveUrl(url, (HttpServletRequest ) pageContext.getRequest());
     }

     public static String  resolveUrl(String  url, HttpServletRequest  request) {
         if (isAbsoluteUrl(url)) {
             return url;
         }
         if (url.startsWith("/")) {
             return (request.getContextPath() + url);
         } else {
             return url;
         }
     }

     public static String  resolveUrl(String  url, String  context) {
         if (isAbsoluteUrl(url)) {
             return url;
         }
         if (!context.startsWith("/") || !url.startsWith("/")) {
             throw new IllegalArgumentException ("Values of both 'context' and 'url' must start with '/'.");
         }
         if (context.equals("/")) {
             return url;
         } else {
             return (context + url);
         }
     }


     // ---------------------------------------------------------------- debug

     /**
      * Returns a string with debug info from all servlet objects.
      * @see #debug(javax.servlet.http.HttpServletRequest, javax.servlet.jsp.PageContext)
      */
     public static String  debug(HttpServletRequest  request) {
         return debug(request, null);
     }

     /**
      * Returns a string with debug info from all servlet objects, including pageScope.
      */
     public static String  debug(HttpServletRequest  request, PageContext  page) {
         StringBuffer  result = new StringBuffer ();
         result.append("\nPARAMETERS\n----------\n");
         Enumeration  enumeration = request.getParameterNames();
         while (enumeration.hasMoreElements()) {
             String  name = (String ) enumeration.nextElement();
             Object [] value = request.getParameterValues(name);
             result.append(name).append('=');
             if (value == null) {
                 result.append("<null>");
             } else if (value.length == 1) {
                 result.append('[').append(value[0]).append("]\n");
             } else {
                 result.append('[').append(value).append("]\n");
             }
         }

         loop:
         for (int i = 0; i < 4; i++) {
             switch (i) {
                 case 0: result.append("\nREQUEST\n--------\n");
                         enumeration = request.getAttributeNames();
                         break;
                 case 1: result.append("\nSESSION\n--------\n");
                         enumeration = request.getSession().getAttributeNames();
                         break;
                 case 2: result.append("\nAPPLICATION\n-----------\n");
                         enumeration = request.getSession().getServletContext().getAttributeNames();
                         break;
                 case 3: if (page == null) {
                             break loop;
                         }
                         result.append("\nPAGE\n-----------\n");
                         enumeration = page.getAttributeNamesInScope(PageContext.PAGE_SCOPE);
             }
             while (enumeration.hasMoreElements()) {
                 String  name = (String ) enumeration.nextElement();
                 Object  value = request.getAttribute(name);
                 result.append(name).append('=');
                 if (value == null) {
                     result.append("<null>\n");
                 } else {
                     result.append('[').append(value).append("]\n");
                 }
             }
         }
         return result.toString();
     }

	public static boolean isRequestMultipart(HttpServletRequest request) {
		String  type = request.getHeader("Content-Type");
        return (type != null) && type.startsWith("multipart/form-data");
	}

}
