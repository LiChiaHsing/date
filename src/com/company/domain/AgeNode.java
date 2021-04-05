package com.company.domain;

/**
 * @author lijiaxing
 * 年龄节点
 * 年龄大于等于3*12个月的，用岁表示；
 * 小于3*12个月而又大于等于1*12个月的，用岁月表示；
 * 小于12个月而又大于等于6个月的，用月表示；
 * 小于6个月而又大于等于1个月的，用月天表示；
 * 小于1个月而又大于等于72小时的(3天)，用天表示
 * 小于72小时的，用小时表示。
 */
public class AgeNode {
    /**
     * 年龄大于等于3*12个月的，用岁表示；
     * 3
     */
    private int yearNodeEnd;

    /**
     * 小于3*12个月而又大于等于1*12个月的，用岁月表示；
     * 1
     */
    private int yearNodeBegin;

    /**
     * 小于12个月而又大于等于6个月的，用月表示；
     * 6
     */
    private int mouthNodeEnd;

    /**
     * 小于6个月而又大于等于1个月的，用月天表示；
     * 小于1个月而又大于等于72小时的(3天)，用天表示
     * 1
     */
    private int mouthNodeBegin;

    /**
     * 小于72小时的，用小时表示;
     * 3
     */
    private int dayNode;

    public AgeNode() {
        this.yearNodeEnd = 3;
        this.yearNodeBegin = 1;
        this.mouthNodeEnd = 6;
        this.mouthNodeBegin = 1;
        this.dayNode = 3;
    }

    public int getYearNodeEnd() {
        return yearNodeEnd;
    }

    public void setYearNodeEnd(int yearNodeEnd) {
        this.yearNodeEnd = yearNodeEnd;
    }

    public int getYearNodeBegin() {
        return yearNodeBegin;
    }

    public void setYearNodeBegin(int yearNodeBegin) {
        this.yearNodeBegin = yearNodeBegin;
    }

    public int getMouthNodeEnd() {
        return mouthNodeEnd;
    }

    public void setMouthNodeEnd(int mouthNodeEnd) {
        this.mouthNodeEnd = mouthNodeEnd;
    }

    public int getMouthNodeBegin() {
        return mouthNodeBegin;
    }

    public void setMouthNodeBegin(int mouthNodeBegin) {
        this.mouthNodeBegin = mouthNodeBegin;
    }

    public int getDayNode() {
        return dayNode;
    }

    public void setDayNode(int dayNode) {
        this.dayNode = dayNode;
    }
}
