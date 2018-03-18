package com.yk.platform.business.constant.entity;

/**
 * @项目名称：watchback
 * @类名称：Constant
 * @类描述：常量类
 * @创建人：阳凯
 * @创建时间：2017年2月8日 上午10:18:32
 * @company:步步高教育电子有限公司
 */
public class Constant
{

    /**
     * 常量名称(全局唯一)
     */
    private String k;
    /**
     * 常量值
     */
    private String v;
    /**
     * 外部是否可见
     */
    private Integer isVisible;
    /**
     * 该常量的解释 
     */
    private String description;

    public String getK()
    {
        return k;
    }

    public void setK(String k)
    {
        this.k = k;
    }

    public String getV()
    {
        return v;
    }

    public void setV(String v)
    {
        this.v = v;
    }

    public Integer getIsVisible()
    {
        return isVisible;
    }

    public void setIsVisible(Integer isVisible)
    {
        this.isVisible = isVisible;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public String toString()
    {
        return "Constant [k=" + k + ", v=" + v + ", isVisible=" + isVisible + ", description=" + description + "]";
    }

}
