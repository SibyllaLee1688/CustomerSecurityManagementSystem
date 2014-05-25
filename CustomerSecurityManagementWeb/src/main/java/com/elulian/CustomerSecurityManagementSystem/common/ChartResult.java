package com.elulian.CustomerSecurityManagementSystem.common;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StrutsResultSupport;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import com.opensymphony.xwork2.ActionInvocation;

/**
 * @version 0.1
 * @author cloudlu
 *
 * @deprecated Not required, since png is the best image in network
 *
 */

public class ChartResult extends StrutsResultSupport {

    /**
     * 
     */
    private static final long serialVersionUID = 4199494785336139337L;
    
    //image width
    private int width;
    //image height
    private int height;
    //image type: just support: jpg, png
    private String imageType;
    
    
    @Override
    protected void doExecute(String arg0, ActionInvocation invocation) throws Exception {
        JFreeChart chart =(JFreeChart) invocation.getStack().findValue("chart");
        HttpServletResponse response = ServletActionContext.getResponse();
        OutputStream os = response.getOutputStream();
        
        if("jpeg".equalsIgnoreCase(imageType) || "jpg".equalsIgnoreCase(imageType))
            ChartUtilities.writeChartAsJPEG(os, chart, width, height);
        else if("png".equalsIgnoreCase(imageType))
            ChartUtilities.writeChartAsPNG(os, chart, width, height);
        else
            ChartUtilities.writeChartAsJPEG(os, chart, width, height);
        
        os.flush();

    }
    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    
    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

}