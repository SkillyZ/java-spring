package com.mooc.house.hsrv.model;

public class HouseQueryReq {
  
  private House query;
  
  private Integer limit;
  
  private Integer offset;


  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public House getQuery() {
    return query;
  }

  public void setQuery(House query) {
    this.query = query;
  }
  

}
