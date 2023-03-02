package com.nanjing.lightingdection.utils;

import java.io.Serializable;

public class GlobalResult implements Serializable {

  /** */
  private static final long serialVersionUID = 1L;

  // 响应业务状态
  private Integer status;

  // 响应消息
  private String msg;

  // 响应中的数据
  private Object data;

  public GlobalResult() {}

  public GlobalResult(Integer status, String msg, Object data) {
    this.status = status;
    this.msg = msg;
    this.data = data;
  }

  public GlobalResult(Object data) {
    this.status = 200;
    this.msg = "OK";
    this.data = data;
  }

  public static GlobalResult build(Integer status, String msg, Object data) {
    return new GlobalResult(status, msg, data);
  }

  public static GlobalResult ok(Object data) {
    return new GlobalResult(data);
  }

  public static GlobalResult ok() {
    return new GlobalResult(null);
  }

  public static GlobalResult build(Integer status, String msg) {
    return new GlobalResult(status, msg, null);
  }

  public Boolean isOK() {
    return this.status == 200;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "ManagerResult [status=" + status + ", msg=" + msg + ", data=" + data + "]";
  }
}
