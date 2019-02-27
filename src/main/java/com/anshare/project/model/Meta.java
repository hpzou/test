package com.anshare.project.model;

import lombok.Data;

/**
* @ClassName Meta
* @Description 菜单标识实体类
* @Author wukunfan
* @Date 2018/11/12 16:57
* @UpdateUser:
* @UpdateDate:     2018/11/12 16:57
* @UpdateRemark:   修改内容
* @Version 1.0
**/
@Data
public class Meta {
    private String title;

    @Override
    public String toString() {
        return "Meta{" +
                "title='" + title + '\'' +
                '}';
    }
    public Meta(String title) {
        this.title = title;
    }
}
