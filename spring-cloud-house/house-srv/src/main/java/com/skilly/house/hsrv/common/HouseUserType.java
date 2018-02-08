package com.mooc.house.hsrv.common;

public enum HouseUserType {
  SALE(1),BOOKMARK(2);
  
  public  final Integer value;
  
  private HouseUserType(Integer value){
    this.value = value;
  }
  

}
