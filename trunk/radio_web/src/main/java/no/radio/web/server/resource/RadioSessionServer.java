package no.radio.web.server.resource;

import java.time.LocalDate;
import java.util.Date;

import no.basis.felles.server.resource.SessionServerResource;

public class RadioSessionServer extends SessionServerResource {

	protected String startDatoId="startdato";
	protected LocalDate startDato = null;
	protected String startdatoDate="firstdato";
	protected String sluttdatoId="sluttdato";
	protected String fileModelId = "filemodel";
	
	public RadioSessionServer() {
		super();
		
		//startDato = LocalDate.now();
	}

	public String getFileModelId() {
		return fileModelId;
	}

	public void setFileModelId(String fileModelId) {
		this.fileModelId = fileModelId;
	}

	public String getStartDatoId() {
		return startDatoId;
	}

	public void setStartDatoId(String startDatoId) {
		this.startDatoId = startDatoId;
	}

	public LocalDate getStartDato() {
		return startDato;
	}

	public void setStartDato(LocalDate startDato) {
		this.startDato = startDato;
	}

	public String getStartdatoDate() {
		return startdatoDate;
	}

	public void setStartdatoDate(String startdatoDate) {
		this.startdatoDate = startdatoDate;
	}

	public String getSluttdatoId() {
		return sluttdatoId;
	}

	public void setSluttdatoId(String sluttdatoId) {
		this.sluttdatoId = sluttdatoId;
	}


	
	
}
