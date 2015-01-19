package com.ssl.jv.gip.web.mb.util;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public enum EFleteExterno {
	PREPAID("P","Prepaid"), COLLECT("C","Collect");
	
	String code;
	String description;
	
	private EFleteExterno(String code, String description){
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	/**
	 * Metodo que traduce la enumeracion a un listado
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email seba.gamba02@gmail.com
	 * @phone 311 8376670
	 * @return
	 */ 
	public static List<SelectItem> getList(){
		List<SelectItem> items = new ArrayList<SelectItem>();
		for(EFleteExterno efe : EFleteExterno.values()){
			SelectItem item = new SelectItem();
			item.setValue(efe.getCode());
			item.setLabel(efe.getDescription());
			items.add(item);
		}
		return items;
	}
}
