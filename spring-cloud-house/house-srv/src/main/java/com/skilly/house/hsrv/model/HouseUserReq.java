package com.mooc.house.hsrv.model;

public class HouseUserReq {

  private Long houseId;
  
  private Long userId;
  
  private Integer bindType;
  
  private boolean unBind;

  public Long getHouseId() {
    return houseId;
  }

  public void setHouseId(Long houseId) {
    this.houseId = houseId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Integer getBindType() {
    return bindType;
  }

  public void setBindType(Integer bindType) {
    this.bindType = bindType;
  }

  public boolean isUnBind() {
    return unBind;
  }

  public void setUnBind(boolean unBind) {
    this.unBind = unBind;
  }

  @Override
  public String toString() {
    return "HouseUserReq [houseId=" + houseId + ", userId=" + userId + "]";
  }
  
  
  
}
