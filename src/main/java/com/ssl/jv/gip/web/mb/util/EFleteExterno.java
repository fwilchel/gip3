package com.ssl.jv.gip.web.mb.util;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public enum EFleteExterno {

  PREPAID("P", "Prepaid"), COLLECT("C", "Collect");

  String code;
  String description;

  private EFleteExterno(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public String getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }
}
