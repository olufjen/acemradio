package no.radio.web.model;

import java.util.Map;



public class LoginModel extends ParentModel {

	private String epostAdresse;
	private String passord;
//	private Saksbehandler saksbehandler;
	
	public LoginModel() {
		super();
//		saksbehandler = new SaksbehandlerImpl();
		
	}
	public String getEpostAdresse() {
		return epostAdresse;
	}
	public void setEpostAdresse(String epostAdresse) {
		this.epostAdresse = epostAdresse;
	}
	public String getPassord() {
		return passord;
	}
	public void setPassord(String passord) {
		this.passord = passord;
	}
	public void saveLogin(){
		String[] formFields = getFormNames();
		Map<String,String> userEntries = getFormMap(); // formMap inneholder verdier angitt av bruker
//		saksbehandler.setBehandlerFields(userEntries);
//		saksbehandler.savetoSaksbehandler();
	}

}
