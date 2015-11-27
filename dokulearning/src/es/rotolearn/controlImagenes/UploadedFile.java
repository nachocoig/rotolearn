package es.rotolearn.controlImagenes;

import java.io.IOException;

public class UploadedFile {

	private boolean isvalid = true;

    /**
     * Returns flag that indicates if file was loaded (valid).
     *
     * @return <code>true</code> for valid files, <code>false</code> for files that
     * didn't exist at the upload time.
     */
    public boolean isValid() {
        return isvalid;
    }

    /**
     * Contrcuts new valid uploaded file.
     */
    public UploadedFile() {
        isvalid = true;
    }

    /**
     * Contrcuts new uploaded file.
     *
     * @param valid <code>true</code> for valid files, <code>false</code> for files that
     * didn't exist at the upload time.s
     */
    public UploadedFile(boolean valid) {
        isvalid = valid;
    }

    // ---------------------------------------------------------------- names

    private String fieldName;
    /**
     * Sets form field name.
     *
     * @param v form field name
     */
    public void setFieldName(String v) {
        fieldName = v;
    }
    /**
     * Returns form field name.
     *
     * @return form field name
     * @see #getFileExt
     */
    public String getFieldName() {
        return fieldName;
    }


    private String fileName;
    /**
     * Sets file name and file extension. File name doesn't contain any path data
     * and do contains file extension.
     *
     * @param v file name
     *
     * @see #getFileExt
     */
    private void setFileName(String v) {
        fileName = v;
        if (fileName == null) {
            fileExt = null;
            return;
        }
        int start = fileName.lastIndexOf('.') + 1;
        if (start == -1) {
            fileExt = "";
            return;
        }
        fileExt = fileName.substring(start);
    }
    /**
     * Returns file name. File name doesn't contain any path data.
     *
     * @return file name
     */
    public String getFileName() {
        return fileName;
    }

    private String fileExt;
    /**
      * Returns file extension.
      *
      * @return file extension
      */
     public String getFileExt() {
         return fileExt;
     }


     private String filePathName;
     /**
      * Sets the complete path name.
      *
      * @param v full file path name
      */

	 public void setFilePathName(String v) {
	     filePathName = v;
	     if (filePathName == null) {
	         setFileName(null);
	         return;
	     }
	     int pos = filePathName.lastIndexOf('/');
	     if (pos != -1) {
	         setFileName(filePathName.substring(pos + 1));
	         return;
	     }
	     pos = filePathName.lastIndexOf('\\');
	     if (pos != -1) {
	         setFileName(filePathName.substring(pos + 1));
	         return;
	     }
	     setFileName(filePathName);
	 }

	/**
      * Return complete file name with path included.
      *
      * @return file path and name
      */
     public String getFilePathName() {
         return filePathName;
     }


     // ---------------------------------------------------------------- content

     private String contentType;
     /**
      * Sets file content type and reads MIME and subtype MIME.
      *
      * @param v content type
      */
     public void setContentType(String v) {
         contentType = v;
         typeMIME = getTypeMIME(contentType);
         subTypeMIME = getSubTypeMIME(contentType);
     }
     /**
      * Returns files content type.
      *
      * @return content type
      */
     public String getContentType() {
         return contentType;
     }
     private String typeMIME;
     /**
      * Returns file types MIME.
      *
      * @return file MIME
      */
     public String getTypeMIME() {
         return typeMIME;
     }
     private String subTypeMIME;
     /**
      * Returns file sub type MIME.
      *
      * @return sub type MIME
      */
     public String getSubTypeMIME() {
         return subTypeMIME;
     }


     private String contentDisp;
     /**
      * Sets content disp.
      *
      * @param v content disp
      */
     public void setContentDisp(String v) {
         contentDisp = v;
     }
     /**
      * Gets content disp.
      *
      * @return content disp
      */
     public String getContentDisp() {
         return contentDisp;
     }

     // ---------------------------------------------------------------- size and position

     private int size;
     /**
      * Sets the file size
      *
      * @param v file size
      */
     public void setSize(int v) {
         if (v < 0) {
             v = 0;
         }
         size = v;
     }
     /**
      * Returns the file size
      *
      * @return file size
      */
     public int getSize() {
         return size;
     }

     private int startData;
     /**
      * Sets starting index of a file in requet data.
      *
      * @param v starting index
      */
     public void setDataStart(int v) {
         if (v < 0) {
             v = 0;
         }
         startData = v;
     }
     /**
      * Returns starting index of a file in request data.
      *
      * @return starting index
      */
     public int getDataStart() {
         return startData;
     }

     /**
      * Returns ending index of a file in request data
      *
      * @return ending index
      */
     public int getDataEnd() {
         return startData + size - 1;
     }


     // ---------------------------------------------------------------- data content

     private byte[] requestData;

     /**
      * Sets request data.
      *
      * @param v request data
      *
      * @see #getRequestData
      */
     public void setRequestData(byte[] v) {
         requestData = v;
     }
     /**
      * Returns complete request. Should not be used much.
      *
      * @return array of request data
      */
     public byte[] getRequestData() {
         return requestData;
     }

     /**
      * Returns file data as a byte array.
      *
      * @return raw file data
      */
     public byte[] getData() {
         if (size < 0) {
             return null;
         }
         if (requestData == null) {
             return null;
         }
         byte[] result = new byte[size];
         System.arraycopy(requestData, startData, result, 0, size);
         return result;
     }

     /**
      * Writes data to a file.
      *
      * @param fileName name of the file where to store data
      *
      * @exception IOException
      */
     public void writeData(String fileName) throws IOException {
         FileUtil.writeBytes(fileName, requestData, startData, size);

     }


     // ---------------------------------------------------------------- misc


     private String getTypeMIME(String ContentType) {
         int pos = ContentType.indexOf("/");
         if (pos == -1) {
             return ContentType;
         }
         return ContentType.substring(1, pos);
     }

     private String getSubTypeMIME(String ContentType) {
         String value = new String();
         int start = ContentType.indexOf("/");
         if (start == -1) {
             return ContentType;
         }
         start++;
         return ContentType.substring(start);
     }

     // ---------------------------------------------------------------- toString


     public String toString() {
         return "UploadedFile: valid=" + isvalid + " fieldName=" + fieldName + " fileName=" + fileName;
     }
}
