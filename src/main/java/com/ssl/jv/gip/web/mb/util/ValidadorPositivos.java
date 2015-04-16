package com.ssl.jv.gip.web.mb.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class ValidadorPositivos implements Validator {

  public void validate(FacesContext context, UIComponent component,
      Object value) {

    Double d = new Double(value.toString());

    if (d <= 0) {

      FacesMessage msg = new FacesMessage("Error de validación:",
          "El número debe ser mayor a cero");
      msg.setSeverity(FacesMessage.SEVERITY_ERROR);

      throw new ValidatorException(msg);
    }

  }

}
