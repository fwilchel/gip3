package com.ssl.jv.gip.web.mb;

import java.util.ArrayList;
import java.util.List;

import javax.el.MethodExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIParameter;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.MethodExpressionActionListener;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;
import org.primefaces.component.submenu.UISubmenu;
import org.primefaces.component.menuitem.UIMenuItem;

import com.ssl.jv.gip.jpa.pojo.Funcionalidad;
import com.ssl.jv.gip.jpa.pojo.Usuario;



@ManagedBean
public class MenuMB extends UtilMB{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7266340433347174042L;
	private Usuario usuario;
	private MenuModel modelo;
	private String opcionActual;
	private List<Funcionalidad> opciones;
	

	public MenuModel getModelo() {
		return modelo;
	}

	public void setModelo(MenuModel modelo) {
		this.modelo = modelo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getOpcionActual() {
		return opcionActual;
	}

	public void setOpcionActual(String opcionActual) {
		this.opcionActual = opcionActual;
	}

	/**
	 * Baja de memoria los MB
	 */
	public String cerrar(){
		return "introduccion";
	}
	
	public void setMenu(List<Funcionalidad> opciones){
		this.opciones=opciones;
		modelo = new DefaultMenuModel();
		
		for (Funcionalidad o:opciones){
			if (o.getFuncionalidade()==null){
				
				UISubmenu sm = new UISubmenu();
				List<Funcionalidad> hijos=getHijos(o, opciones, sm);
				if (hijos.size()==0){
					/*MenuItem mi=new MenuItem();
					mi.setLabel(o.getOpcion_1());
					mi.setActionCommand(o.getUrl());*/
				}else{
					
					sm.setLabel(o.getNombre());
					modelo.addElement(sm);
					o.setFuncionalidades(hijos);
				}
				
			}
		}
	}
	
	private List<Funcionalidad> getHijos(Funcionalidad padre, List<Funcionalidad> opciones, UISubmenu sm){
		List<Funcionalidad> hijos=new ArrayList<Funcionalidad>();
		for (Funcionalidad o:opciones){
			if (o.getFuncionalidade()!=null && padre.getId().equals(o.getFuncionalidade().getId())){
				hijos.add(o);

				UISubmenu sm2 = new UISubmenu();
				List<Funcionalidad> hijos2=getHijos(o, opciones, sm2);
				if (hijos2.size()==0){
					UIMenuItem mi=new UIMenuItem();
					mi.setValue(o.getNombre());
					//mi.setIcon(o.getIcono());
					
					mi.setActionExpression(createAction("#{menuMB.ingresoOpcion}", String.class));
					UIParameter para2=new UIParameter();
	   				para2.setName("opcion");
	   				para2.setValue(o.getRuta());
	   				
	   				mi.getChildren().add(para2);
	   				mi.setAjax(false);
					
					sm.getChildren().add(mi);
				}else{
					
					sm.setLabel(o.getNombre());
					modelo.addElement(sm);
					o.setFuncionalidades(hijos2);
				}
			}
		}
		
		
		return hijos;
	}
	

	public static MethodExpression createAction(String actionExpression, Class<?> returnType) { 
        FacesContext context = FacesContext.getCurrentInstance(); 
        return context.getApplication().getExpressionFactory() 
            .createMethodExpression(context.getELContext(), actionExpression, returnType, new Class[0]); 
    } 
     
    public static MethodExpressionActionListener createActionListener(String actionListenerExpression) { 
        FacesContext context = FacesContext.getCurrentInstance(); 
        return new MethodExpressionActionListener(context.getApplication().getExpressionFactory() 
            .createMethodExpression(context.getELContext(), actionListenerExpression, null, new Class[] {ActionEvent.class})); 
    }
    
    public String ingresoOpcion(){
    	
    	FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest req = (HttpServletRequest) ec.getRequest();
        for (Funcionalidad o:opciones){
        	if (o.getRuta()!=null && o.getRuta().equals(req.getParameter("opcion"))){
        		this.opcionActual = o.getNombre();
        		break;
        	}
        }
    	return req.getParameter("opcion");
    }


}
