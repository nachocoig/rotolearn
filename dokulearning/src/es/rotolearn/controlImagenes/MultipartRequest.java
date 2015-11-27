package es.rotolearn.controlImagenes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;



public class MultipartRequest {

	 private Hashtable requestParameters;

	    private Hashtable requestFiles;

	    /**
	     * Default constructor, for using MultipartRequest in steps.
	     */
	    public MultipartRequest() {
	    }

	    /**
	     * Constructor that loads request and extracts all uploaded files and
	     * parameters using specified encoding. If request is not multipart, all
	     * parameters will be read, anyway.
	     *
	     * @param request http request
	     * @param encoding encoding
	     *
	     * @exception IOException
	     */
	    public MultipartRequest(HttpServletRequest request, String encoding) throws IOException {
	        load(request, encoding);
	    }

	    /**
	     * Constructor that loads request and extracts all uploaded files and
	     * parameters without encoding.
	     *
	     * @param request http request
	     *
	     * @exception IOException
	     * @see #MultipartRequest(HttpServletRequest, String)
	     */

	    public MultipartRequest(HttpServletRequest request) throws IOException {
	        load(request);
	    }


	    /**
	     * Loads http request manually without encoding.
	     *
	     * @param request http request.
	     *
	     * @exception IOException
	     * @see #load(HttpServletRequest, String)
	     */
	    public void load(HttpServletRequest request) throws IOException {
	        load(request, null);
	    }

	    /**
	     * Loads http request manually, with susing of specific encoding. If request
	     * is not multipart, all parameters will be read, anyway.
	     *
	     * @param request http request.
	     * @param encoding encoding that will be used during extracting
	     *
	     * @exception IOException
	     */
	    public void load(HttpServletRequest request, String encoding) throws IOException {

	        if (ServletUtil.isRequestMultipart(request) == true) {
	            byte[] requestData = getRequestData(request);
	            Hashtable[] maps = extractParameters(requestData, encoding);
	            requestParameters = maps[0];
	            requestFiles = maps[1];
	        } else {
	            requestParameters = new Hashtable();
	            requestFiles = new Hashtable();
	             Enumeration names = request.getParameterNames();
	             while (names.hasMoreElements()) {
	                 String paramName = (String) names.nextElement();
	                 String[] values = request.getParameterValues(paramName);
	                 requestParameters.put(paramName, values);
	             }
	         }
	     }


	     /**
	      * Returns value of a parameter.
	      *
	      * @param paramName parameter name
	      *
	      * @return parameer value, or <code>null</code> if not found
	      */
	     public String getParameter(String paramName) {
	         String[] values = (String[]) requestParameters.get(paramName);
	         if ((values != null) && (values.length > 0)) {
	             return (String) values[0];
	         }
	         return null;
	     }
	     /**
	      * Returns names of all availiable parameters.
	      *
	      * @return enumeration of all parameters names
	      */
	     public Enumeration getParameterNames() {
	         return requestParameters.keys();
	     }

	     public String[] getParameterValues(String paramName) {
	         return (String[]) requestParameters.get(paramName);
	     }


	     /**
	      * Returns uploaded file.
	      *
	      * @param paramName parameter name of the uploaded file
	      *
	      * @return uploaded file or <code>null</code> if parameter name not found
	      */
	     public UploadedFile getUploadedFile(String paramName) {
	         return (UploadedFile) requestFiles.get(paramName);
	     }

	     /**
	      * Returns parameter names of all uploaded files.
	      *
	      * @return enumeration of all uploaded files parameter names
	      */
	     public Enumeration getUploadedFileNames() {
	         return requestFiles.keys();
	     }


	     // ---------------------------------------------------------------- load and extract

	     /**
	      * Loads request data into a byte array.
	      *
	      * @param request http request
	      *
	      * @return byte array containing request data
	      * @exception IOException
	      */
	     public static byte[] getRequestData(HttpServletRequest request) throws IOException {
	         int totalBytes = request.getContentLength();
	         byte[] binArray = new byte[totalBytes];
	         int readBytes = 0;
	         try {
	             for (int totalRead = 0; totalRead < totalBytes; totalRead += readBytes) {
	                 readBytes = request.getInputStream().read(binArray, totalRead, totalBytes - totalRead);
	             }
	         } catch (Exception e) {
	             throw new IOException("Upload error.");
	         }
	         return binArray;
	     }

	     /**
	      * Extracts uploaded files and parameters from the request data, not using
	      * any encoding.
	      *
	      * @param binArray binarry array of loaded request data
	      *
	      * @return array with two Hashtables that contains parameters and uploaded files
	      * @see #getRequestData
	      * @see #extractParameters(byte[], String)
	      */
	     public static Hashtable[] extractParameters(byte[] binArray) {
	         return extractParameters(binArray, null);
	     }

	     /**
	      * Extracts uploaded files and parameters from the request data. The result
	      * is returned as array of two Hashtables. The first one contains parameter
	      * data, and the second one contains uploaded file data.
	      *
	      * @param binArray binarry array of loaded request data
	      * @param encoding encoding for parameters, if <code>null</code> encoding will not be used
	      *
	      * @return array with two Hashtables that contains parameters and uploaded files
	      * @see #getRequestData
	      */
	     public static Hashtable[] extractParameters(byte[] binArray, String encoding) {
	         if (binArray == null) {
	             return null;
	         }
	         Hashtable parameters = new Hashtable();
	         Hashtable files = new Hashtable();
	         int totalBytes = binArray.length;
	         int currentIndex = 0;
	         StringBuffer boundary = new StringBuffer();
	         while (currentIndex < totalBytes) {
	             if (binArray[currentIndex] == 13) {
	                 break;
	             }
	             boundary.append((char)binArray[currentIndex]);
	             currentIndex++;
	         }
	         if (currentIndex == 1) {
	             return null;
	         }
	         currentIndex++;

	         while (currentIndex < totalBytes) {

	             /* data header */
	             int start = currentIndex;
	             String dataHeader = null;
	             while (currentIndex < totalBytes) {
	                 if ((binArray[currentIndex] == 13) && (binArray[currentIndex + 2] == 13)) {
	                     int end = currentIndex;
	                     if (encoding != null) {
	                         try {
	                             dataHeader = new String(binArray, start, currentIndex - start, encoding);
	                         } catch (UnsupportedEncodingException ueex) {
	                             dataHeader = null;
	                         }
	                     }
	                     if (dataHeader == null) {
	                         dataHeader = new String(binArray, start, currentIndex - start);
	                     }
	                     currentIndex += 2;
	                     break;
	                 }
	                 currentIndex++;
	             }
	             if (dataHeader == null) {
	                 break;
	             }
	             currentIndex += 2;
	             boolean isFile = dataHeader.indexOf("filename") > 0;

	             /* fields */
	             String fieldName = getDataFieldValue(dataHeader, "name");
	             String fileName = "";
	             String filePathName = "";
	             if (isFile) {
	                 filePathName = getDataFieldValue(dataHeader, "filename");
	                 fileName = getFileName(filePathName);
	             }

	             /* data section */
	             boolean found = false;
	             int searchPos = currentIndex;
	             int keyPos = 0;
	             int boundaryLen = boundary.length();
	             int startData = currentIndex;
	             int endData = 0;
	             while (true) {
	                 if (searchPos >= totalBytes) break;
	                 if (binArray[searchPos] == (byte) boundary.charAt(keyPos)) {
	                     if (keyPos == boundaryLen - 1) {
	                         endData = ((searchPos - boundaryLen) + 1) - 3;
	                         break;
	                     }
	                     searchPos++;
	                     keyPos++;
	                 } else {
	                     searchPos++;
	                     keyPos = 0;
	                 }
	             }
	             currentIndex = endData + boundaryLen + 3;

	             /* store file */
	             if (isFile && (fileName.length() > 0)) {
	                 int totalFileSize = (endData - startData) + 1;
	                 UploadedFile newFile = new UploadedFile();
	                 newFile.setFieldName(fieldName);
	                 newFile.setFilePathName(filePathName);
	                 String contentType = getContentType(dataHeader);
	                 newFile.setContentType(contentType);
	                 newFile.setContentDisp(getContentDisp(dataHeader));
	                 if (contentType.indexOf("application/x-macbinary") > 0) {
	                     startData += 128;
	                 }
	                 newFile.setSize((endData - startData) + 1);
	                 newFile.setDataStart(startData);
	                 //newFile.setDataEnd(endData);
	                 newFile.setRequestData(binArray);

	                 files.put(fieldName, newFile);
	             } else {
	                 String value = null;
	                 if (encoding != null) {
	                     try {
	                         value = new String(binArray, startData, (endData - startData) + 1, encoding);
	                     } catch (UnsupportedEncodingException ucex) {
	                         value = null;
	                     }
	                 }
	                 if (value == null) {
	                     value = new String(binArray, startData, (endData - startData) + 1);
	                 }
	                 if (isFile == false) {
	                     ArrayList valuesList = (ArrayList) parameters.get(fieldName);
	                     if (valuesList == null) {
	                         valuesList = new ArrayList();
	                     }
	                     valuesList.add(value);
	                     parameters.put(fieldName, valuesList);
	                 } else {
	                     UploadedFile newFile = new UploadedFile(false);
	                     newFile.setFieldName(fieldName);
	                     newFile.setFilePathName(value);
	                     files.put(fieldName, newFile);
	                 }
	             }
	             if ((char)binArray[currentIndex + 1] == '-') {
	                 break;
	             }
	             currentIndex += 2;
	         }

	         // convert ArrayLists for parameters into arrays
	 Enumeration names = parameters.keys();
	         while (names.hasMoreElements()) {
	             String paramName = (String) names.nextElement();
	             ArrayList valuesList = (ArrayList) parameters.get(paramName);
	             if (valuesList != null) {
	                 String[] result = new String[valuesList.size()];
	                 for (int i = 0; i < result.length; i++) {
	                     result[i] = (String) valuesList.get(i);
	                 }
	                 parameters.put(paramName, result);
	             }
	         }
	         return new Hashtable[] {parameters, files};
	     }



	     // ---------------------------------------------------------------- misc utils

	     /**
	      * Gets value of data field
	      *
	      * @param dataHeader header
	      * @param fieldName field's name
	      *
	      * @return value or empty string if not founded
	      */
	     public static String getDataFieldValue(String dataHeader, String fieldName) {
	         String value = "";
	         String token = String.valueOf((new StringBuffer(String.valueOf(fieldName))).append("=").append('"'));
	         int pos = dataHeader.indexOf(token);
	         if (pos > 0) {
	             int i = pos + token.length();
	             int start = i;
	             token = "\"";
	             int end = dataHeader.indexOf(token, i);
	             if ((start > 0) && (end > 0)) {
	                 value = dataHeader.substring(start, end);
	             }
	         }
	         return value;
	     }

	     /**
	      * Strips content type information from request's data header.
	      *
	      * @param dataHeader data header string
	      *
	      * @return content type or an empty string if no content type defined
	      */
	     private static String getContentType(String dataHeader) {
	         String token = "Content-Type:";
	         int start = dataHeader.indexOf(token);
	         if (start == -1) {
	             return "";
	         }
	         start += token.length();
	         return dataHeader.substring(start);
	     }

	     private static String getFileName(String filePathName) {
	         int pos = filePathName.lastIndexOf('/');
	         if (pos != -1) {
	             return filePathName.substring(pos + 1);
	         }
	         pos = filePathName.lastIndexOf('\\');
	         if (pos != -1) {
	             return filePathName.substring(pos + 1);
	         }
	         return filePathName;
	     }

	     private static String getContentDisp(String dataHeader) {
	         int start = dataHeader.indexOf(":") + 1;
	         int end = dataHeader.indexOf(";");
	         return dataHeader.substring(start, end);
	     }

}




