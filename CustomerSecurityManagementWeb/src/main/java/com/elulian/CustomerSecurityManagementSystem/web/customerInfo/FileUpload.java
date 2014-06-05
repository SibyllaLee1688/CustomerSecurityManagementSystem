package com.elulian.CustomerSecurityManagementSystem.web.customerInfo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

//import org.apache.struts2.json.annotations.JSON;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

@Controller("fileUploadAction") @Scope("prototype")
public class FileUpload extends com.opensymphony.xwork2.ActionSupport {

	private static Logger logger = LoggerFactory.getLogger(FileUpload.class);
	
	private static String savePath = "./";
	
	private static final int BUFFER_SIZE = 16 * 1024 ;

	private File upload;
    private String uploadContentType;
    private String uploadFileName;

	/*private boolean actionResult;
	private String actionMessage;
    
	@JSON(name = "success")
	public boolean getActionResult() {
		return actionResult;
	}

	@JSON(name = "msg")
	public String getActionMessage() {
		return actionMessage;
	}*/	
	
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	 
	/*
     * @see Ext.BasicForm.fileUpload 
     */
	
	public String upload(){
    	      
        logger.debug("upload file name: "+ this.getUploadFileName());
        //logger.debug("upload file length: "+ this.getFile().length());
        logger.debug("upload file type: "+ this.getUploadContentType());
        
       
        /*HttpServletResponse response = (HttpServletResponse)ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
        response.setContentType("text/html;charset=utf-8");
        */
        File dstFile = new File(savePath + this.getUploadFileName());
        /*
         * Directly remove the dstFile on the server.
         * May change in the future, depends on whether old file need to backup.
         */
        if(dstFile.exists())
        	dstFile.delete();
        if(copy(this.upload, dstFile)){
        	//actionResult = true;
			//actionMessage = this.getUploadFileName() + "File upload successful!";
        	return Action.SUCCESS;
        }
//        actionResult = false;
//		actionMessage = this.getUploadFileName() + "File upload failed!";
        return Action.ERROR;
    }

	private boolean copy(File src, File dst) {
		InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
            out = new BufferedOutputStream(new FileOutputStream(dst),
                    BUFFER_SIZE);
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                	logger.error(e.getMessage(), e);
                }
            }
        }
	}	
}
