package com.mooc.house.hsrv.exception;

public class IllegalParamsException extends RuntimeException implements WithTypeException{

  private static final long serialVersionUID = 936915964862903634L;

  private Type type;

  public IllegalParamsException(Type type, String message) {
    super(message);
    this.type = type;
  }
  
  public  Type type(){
    return type;
  }

  
  public enum Type{
    WRONG_PAGE_NUM;
  }
}
