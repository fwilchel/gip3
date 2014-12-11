package com.ssl.jv.gip.web.mb;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
/**
 * <p>Title: GIP</p>
 *
 * <p>Description: GIP</p>
 *
 * <p>Copyright: Copyright (c) 2012</p>
 *
 * <p>Company: Soft Studio Ltda.</p>
 *
 * @author Fredy Giovanny Wilches L�pez
 * @email fredy.wilches@gmail.com
 * @phone 300 2146240
 * @version 1.0
 */
public class UtilMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1971839772790505202L;
	/**
	 * Variables especificas para el codigo estructurado y asignarle los valores del buscador
	 */
	//DetalleCodigoEstructurado detalleCodigoEstructurado = new DetalleCodigoEstructurado();
	
	public UtilMB(){
		
	}
	
	/*public IUsuario getUsuarioSession(){
		IUsuario user = null;
		try{
			Map<String, Object> sesion = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			user = (IUsuario) sesion.get("user");
		}catch(Exception e){
			
		}
		return user;
	}*/
	
	public void invalidarSesion(){
		try{
			//clearKeepAlive();
			//clearView();
			HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			if(sesion!=null){
				sesion.removeAttribute("user");
				sesion.invalidate();
			}
		}catch(Exception e){
			
		}
	}
	
	public void addMensajeError(Exception exception){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,exception.getMessage(),exception.toString()));
	}
	
	public void addMensajeError(String mensaje){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, null));
	}
	
	public void addMensajeError(String idComponente, String mensaje){
		FacesContext.getCurrentInstance().addMessage(idComponente, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, null));
	}
	
	public void addMensajeInfo(String mensaje){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje));
	}
	
	public void addMensajeInfo(String idComponente, String mensaje){
		FacesContext.getCurrentInstance().addMessage(idComponente, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, null));
	}
	
	public void addMensajeWarn(String mensaje){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, null));
	}
	
	public void addMensajeWarn(String idComponente, String mensaje){
		FacesContext.getCurrentInstance().addMessage(idComponente, new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, null));
	}
	
	public void resetearUIInput(String[] idsUiInput){
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		UIComponent comp = null;
		for (int i = 0; i < idsUiInput.length; i++) {
			comp = viewRoot.findComponent(idsUiInput[i]);
			if(comp!=null && comp instanceof UIInput)
				((UIInput)comp).resetValue();
		}
	}
	
	public void removerBeanKeepAlive(String bean){
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		Map<String,Object> atributos = viewRoot.getAttributes();
		atributos.remove("org.ajax4jsf.viewbean:"+bean);
	}
	
	public void removerAtributoDeUIViewRoot(String atributo){
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		Map<String,Object> atributos = viewRoot.getAttributes();
		atributos.remove(atributo);
	}
	
	public String getInitParameter(String parameterName){
		return FacesContext.getCurrentInstance().getExternalContext().getInitParameter(parameterName);
	}
	
	public Map<String, String> getRequestParameterMap(){
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	}
	
	public Map<String, Object> getRequestMap(){
		return FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
	}
	
	public Map<String, Object> getSessionMap(){
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	}
	
	public Map<String, Object> getApplicationMap(){
		return FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
	}
	
	public Object getRequestAttribute(String attributeName){
		Map<String, Object> map = getRequestMap();
		return map.get(attributeName);
	}
	
	public void setRequestAttribute(String attributeName, Object attributeValue){
		Map<String, Object> map = getRequestMap();;
		map.put(attributeName, attributeValue);
	}
	
	public Object getSessionAttribute(String attributeName){
		Map<String, Object> map = getSessionMap();
		return map.get(attributeName);
	}
	
	public void setSessionAttribute(String attributeName, Object attributeValue){
		Map<String, Object> map = getSessionMap();
		map.put(attributeName, attributeValue);
	}
	
	public String getRequestParameter(String parameterName){
		return getRequestParameterMap().get(parameterName);
	}
	
	public void setRequestParameter(String parameterName, String parameterValue){
		getRequestParameterMap().put(parameterName, parameterValue);
	}
	
	public SelectItem getSelectItemPorValue(Object value, List<SelectItem> items){
		SelectItem item = null;
		for (SelectItem selectItem : items) {
			if(value.equals(selectItem.getValue())){
				item = selectItem;
				break;
			}
		}
		return item;
	}
	
	public String getLabelPorValue(Object value, List<SelectItem> items){
		String label = null;
		for (SelectItem selectItem : items) {
			if(value.equals(selectItem.getValue())){
				label = selectItem.getLabel();
				break;
			}
		}
		return label;
	}

	public boolean getErrores(){
		FacesContext fc = FacesContext.getCurrentInstance();
		if (fc.getMaximumSeverity()==null)
			return false;
		else
			if (fc.getMaximumSeverity().equals(FacesMessage.SEVERITY_ERROR) || fc.getMaximumSeverity().equals(FacesMessage.SEVERITY_FATAL))
				return true;
			else
				return false;
		
	}

}
