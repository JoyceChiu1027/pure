package com.bupt.chengde.entity;

/**
 * Created by wangxin on 2015/12/17.
 * 常见问题
 */
public class ResponseCommonProblem {

    private String commonProblem;
    private String commonSolution;

    public String getCommonProblem() {
        return commonProblem;
    }

    public void setCommonProblem(String commonProblem) {
        this.commonProblem = commonProblem;
    }

    public String getCommonSolution() {
        return commonSolution;
    }

    public void setCommonSolution(String commonSolution) {
        this.commonSolution = commonSolution;
    }

    @Override
    public String toString() {
        return "ResponseCommonProblem{" +
                "commonProblem='" + commonProblem + '\'' +
                ", commonSolution='" + commonSolution + '\'' +
                '}';
    }
}
