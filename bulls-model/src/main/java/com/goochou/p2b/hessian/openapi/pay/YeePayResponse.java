package com.goochou.p2b.hessian.openapi.pay;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Response;


public class YeePayResponse extends Response {
    
    /**
     *
     */
    private static final long serialVersionUID = 7838928769573657945L;
    @FieldMeta(description = "URL", have=true)
	private String url;
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
	
}
