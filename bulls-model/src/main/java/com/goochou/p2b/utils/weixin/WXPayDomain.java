 package com.goochou.p2b.utils.weixin;

 /**
 * @author ydp
 * @date 2019/07/13
 */
public class WXPayDomain implements IWXPayDomain {

    /* (non-Javadoc)
     * @see com.github.wxpay.sdk.IWXPayDomain#report(java.lang.String, long, java.lang.Exception)
     */
    @Override
    public void report(String domain, long elapsedTimeMillis, Exception ex) {
        // TODO Auto-generated method stub
         
    }

    /* (non-Javadoc)
     * @see com.github.wxpay.sdk.IWXPayDomain#getDomain(com.github.wxpay.sdk.WXPayConfig)
     */
    @Override
    public DomainInfo getDomain(WXPayConfig config) {
        // TODO Auto-generated method stub
         return null;
    }

}
