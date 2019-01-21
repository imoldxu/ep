package com.yyg.eprescription.entity;

import java.io.Serializable;
import java.util.List;

public class PrescriptionInfo implements Serializable{

	 /**
	 * 
	 */
	private static final long serialVersionUID = -1745404976653165750L;

	private Prescription prescription;
	 
	 private List<PrescriptionDrugs> drugList;

	public List<PrescriptionDrugs> getDrugList() {
		return drugList;
	}

	public void setDrugList(List<PrescriptionDrugs> drugList) {
		this.drugList = drugList;
	}

	public Prescription getPrescription() {
		return prescription;
	}

	public void setPrescription(Prescription prescription) {
		this.prescription = prescription;
	}
	 
	 
}
