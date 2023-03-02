package com.nanjing.lightingdection.utils;

import java.util.List;

public class DatameaningTree {
  private String id; // 菜单id
  private String text; // 菜单名称
  private Integer status; // 是否已删除
  private boolean checked; // 是否为选中
  private List<DatameaningTree> children; // 下级菜单

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public boolean isChecked() {
    return checked;
  }

  public void setChecked(boolean checked) {
    this.checked = checked;
  }

  public List<DatameaningTree> getChildren() {
    return children;
  }

  public void setChildren(List<DatameaningTree> children) {
    this.children = children;
  }
}
