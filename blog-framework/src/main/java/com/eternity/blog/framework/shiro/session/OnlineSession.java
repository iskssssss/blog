package com.eternity.blog.framework.shiro.session;

import com.eternity.blog.common.enums.OnlineStatus;
import com.eternity.blog.common.utils.StringUtils;
import org.apache.shiro.session.mgt.SimpleSession;

/**
 * @Description 在线用户会话属性(内存)
 * @Author eternity
 * @Date 2020/4/17 21:37
 */
public class OnlineSession extends SimpleSession {

    /**
     * 用户ID
     */
    private Long uoUid;
    /**
     * 浏览器类型
     */
    private String uoBrowser;
    /**
     * 操作系统
     */
    private String uoOs;
    /**
     * 帐号状态
     */
    private OnlineStatus uoStatus;

    private transient boolean attributeChanged = false;

    public void markAttributeChanged() {
        this.attributeChanged = true;
    }

    public void resetAttributeChanged() {
        this.attributeChanged = false;
    }

    public boolean isAttributeChanged() {
        return this.attributeChanged;
    }

    public Long getUoUid() {
        return uoUid;
    }

    public void setUoUid(Long uoUid) {
        this.uoUid = uoUid;
    }

    public String getUoBrowser() {
        return uoBrowser;
    }

    public void setUoBrowser(String uoBrowser) {
        this.uoBrowser = uoBrowser;
    }

    public String getUoOs() {
        return uoOs;
    }

    public void setUoOs(String uoOs) {
        this.uoOs = uoOs;
    }

    public OnlineStatus getUoStatus() {
        return uoStatus;
    }

    public void setUoStatus(OnlineStatus uoStatus) {
        this.uoStatus = uoStatus;
    }

/*    @Override
    public String getHost() {
        return host;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }*/

    /*@Override
    public void setAttribute(Object key, Object value) {
        super.setAttribute(key, value);
    }

    @Override
    public Object removeAttribute(Object key) {
        return super.removeAttribute(key);
    }*/

    @Override
    public String toString() {
        return "OnlineSession{" +
                "uoUid=" + uoUid +
                ", uoBrowser='" + uoBrowser + '\'' +
                ", uoOs='" + uoOs + '\'' +
                ", uoStatus='" + (StringUtils.isNotNull(uoStatus) ? uoStatus.strCode() : null) + '\'' +
                ", attributeChanged=" + attributeChanged +
                '}';
    }
}






























