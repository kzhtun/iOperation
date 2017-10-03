/**
 *  Copyright (c) 2003,2004, Stefan Haustein, Oberhausen, Rhld., Germany
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The  above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE. 
 *
 * Contributor(s): John D. Beatty, Dave Dash, F. Hunter, Alexander Krebs, 
 *                 Lars Mehrmann, Sean McDaniel, Thomas Strang, Renaud Tognelli 
 * */
package org.ksoap2.transport;

import java.io.*;
import java.util.List;

import org.ksoap2.*;
import org.xmlpull.v1.*;

import android.util.Log;

/**
 * A J2SE based HttpTransport layer.
 */
public class HttpTransportSE extends Transport {

    /**
     * Creates instance of HttpTransportSE with set url
     * 
     * @param url
     *            the destination to POST SOAP data
     */
    public HttpTransportSE(String url) {
        super(url);
    }
    
    /**
    * Creates instance of HttpTransportSE with set url
    * 
    * @param url
    *            the destination to POST SOAP data
    * @param timeout
    * 			  timeout for connection and Read Timeouts (milliseconds)
    */
   public HttpTransportSE(String url, int timeout) {
       super(url, timeout);
   }

    

    /**
     * set the desired soapAction header field
     * 
     * @param soapAction
     *            the desired soapAction
     * @param envelope
     *            the envelope containing the information for the soap call.
     */
    public void call(String soapAction, SoapEnvelope envelope) throws IOException, XmlPullParserException {
        if (soapAction == null)
            soapAction = "\"\"";
        byte[] requestData = createRequestData(envelope);
        requestDump = debug ? new String(requestData) : null;
        responseDump = null;
        ServiceConnection connection = getServiceConnection();
        connection.setRequestProperty("User-Agent", "kSOAP/2.0");
        connection.setRequestProperty("SOAPAction", soapAction);
        connection.setRequestProperty("Content-Type", "text/xml");
        connection.setRequestProperty("Connection", "close");
        connection.setRequestProperty("Content-Length", "" + requestData.length);
        connection.setRequestMethod("POST");
        connection.connect();
        OutputStream os = connection.openOutputStream();
        os.write(requestData, 0, requestData.length);
        os.flush();
        os.close();
        requestData = null;
        InputStream is;
        try {
            connection.connect();
            is = connection.openInputStream();
        } catch (IOException e) {
            is = connection.getErrorStream();
            if (is == null) {
                connection.disconnect();
                throw (e);
            }
        }
        if (debug) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[256];
            while (true) {
                int rd = is.read(buf, 0, 256);
                if (rd == -1)
                    break;
                bos.write(buf, 0, rd);
            }
            bos.flush();
            buf = bos.toByteArray();
            responseDump = new String(buf);
            is.close();
            is = new ByteArrayInputStream(buf);
        }
        parseResponse(envelope, is);
    }
    
    /**
     * set the desired soapAction header field
     * 
     * @param soapAction
     *            the desired soapAction
     * @param envelope
     *            the envelope containing the information for the soap call.
     */
    public void callUploadFile(String soapAction, SoapEnvelope envelope,String pathImagePhoto, String pathImageSignature) throws IOException, XmlPullParserException {
        if (soapAction == null)
            soapAction = "\"\"";
        byte[] requestData = createRequestData(envelope);
        requestDump = debug ? new String(requestData) : null;
        responseDump = null;
        String boundary =  "*****";
        ServiceConnection connection = getServiceConnection();
        
        DataOutputStream outputStream = null;

        String lineEnd = "\r\n";
        String twoHyphens = "--";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1*1024*1024;
        
//        int additionalByte = 0;
//        
//        FileInputStream fileInputStream = new FileInputStream(new File(pathImagePhoto) );
//        
//        bytesAvailable = fileInputStream.available();
//        bufferSize = Math.min(bytesAvailable, maxBufferSize);
//        buffer = new byte[bufferSize];
//        
//        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//        
//        additionalByte = additionalByte + bytesRead;
//        
//        fileInputStream.close();
//        
//        
//        fileInputStream = new FileInputStream(new File(pathImageSignature) );
//        
//        bytesAvailable = fileInputStream.available();
//        bufferSize = Math.min(bytesAvailable, maxBufferSize);
//        buffer = new byte[bufferSize];
//        
//        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//        
//        additionalByte = additionalByte + bytesRead;
//        
//        fileInputStream.close();
        
        
        connection.setRequestProperty("User-Agent", "kSOAP/2.0");
        connection.setRequestProperty("SOAPAction", soapAction);
        connection.setRequestProperty("Content-Type", "text/xml");
        //connection.setRequestProperty("Connection", "close");
        //connection.setRequestProperty("Content-Length", "" + requestData.length+additionalByte);
        connection.setRequestProperty("Content-Length", "" + requestData.length);
        connection.setRequestMethod("POST");
        
        connection.setRequestProperty("Connection", "Keep-Alive");
        //connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

        
        connection.connect();
        
        
//        outputStream = new DataOutputStream( connection.openOutputStream() );
//        
//        outputStream.write(requestData, 0, requestData.length);
        

//        //upload photo
//        FileInputStream fileInputStream = new FileInputStream(new File(pathImagePhoto) );
//        
//
//        outputStream.writeBytes(twoHyphens + boundary + lineEnd);
//        outputStream.writeBytes("Content-Disposition: form-data; name=\"PhotoImgFileName\"" + lineEnd);
//        outputStream.writeBytes(lineEnd);
//
//        bytesAvailable = fileInputStream.available();
//        bufferSize = Math.min(bytesAvailable, maxBufferSize);
//        buffer = new byte[bufferSize];
//
//        // Read file
//        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//
//        while (bytesRead > 0)
//        {
//        outputStream.write(buffer, 0, bufferSize);
//        bytesAvailable = fileInputStream.available();
//        bufferSize = Math.min(bytesAvailable, maxBufferSize);
//        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//        }
//
//        outputStream.writeBytes(lineEnd);
//        outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
//        
//        fileInputStream.close();
//        
//      //upload signature
//        fileInputStream = new FileInputStream(new File(pathImageSignature) );
//        
//        //outputStream = new DataOutputStream( connection.openOutputStream() );
//        outputStream.writeBytes(twoHyphens + boundary + lineEnd);
//        outputStream.writeBytes("Content-Disposition: form-data; name=\"SignatureImgFileName\"" + lineEnd);
//        outputStream.writeBytes(lineEnd);
//
//        bytesAvailable = fileInputStream.available();
//        bufferSize = Math.min(bytesAvailable, maxBufferSize);
//        buffer = new byte[bufferSize];
//
//        // Read file
//        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//
//        while (bytesRead > 0)
//        {
//        outputStream.write(buffer, 0, bufferSize);
//        bytesAvailable = fileInputStream.available();
//        bufferSize = Math.min(bytesAvailable, maxBufferSize);
//        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//        }
//
//        outputStream.writeBytes(lineEnd);
//        outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
//
//
//        fileInputStream.close();

//        outputStream.flush();
//        outputStream.close();
        
        OutputStream os = connection.openOutputStream();
        os.write(requestData, 0, requestData.length);
        os.flush();
        os.close();
        
//        OutputStream os = connection.openOutputStream();
//        os.write(requestData, 0, requestData.length);
//        os.flush();
//        os.close();
        requestData = null;
        InputStream is;
        try {
            connection.connect();
            is = connection.openInputStream();
        } catch (IOException e) {
            is = connection.getErrorStream();
            if (is == null) {
                connection.disconnect();
                throw (e);
            }
        }
       // if (debug) {
        if (true) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[256];
            while (true) {
                int rd = is.read(buf, 0, 256);
                if (rd == -1)
                    break;
                bos.write(buf, 0, rd);
            }
            bos.flush();
            buf = bos.toByteArray();
            responseDump = new String(buf);
            is.close();
            is = new ByteArrayInputStream(buf);
        }
        parseResponse(envelope, is);
    }

    public ServiceConnection getServiceConnection() throws IOException {
        return new ServiceConnectionSE(url);
    }

	@Override
	public List call(String soapAction, SoapEnvelope envelope, List headers)
			throws IOException, XmlPullParserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List call(String soapAction, SoapEnvelope envelope, List headers,
			File outputFile) throws IOException, XmlPullParserException {
		// TODO Auto-generated method stub
		return null;
	}
}