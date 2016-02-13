package com.example.ol.venuelocator.http.dto;

/**
* Created by ol on 10.02.16.
*/
public class MetaDto {
  int code;
/// ToDo Check - what's correct -  RequestId or ErrCode ?
//  String requestId;

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }
/*
  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }
*/
}
