package no.radio.web.server.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;






import java.util.stream.Stream;

import no.basis.felles.model.FileModel;
import no.basis.felles.model.FileProcess.ReadFile;
import no.basis.felles.server.resource.SessionServerResource;

import org.restlet.Request;
import org.restlet.data.Form;
import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.data.Parameter;
import org.restlet.data.Reference;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import freemarker.template.SimpleScalar;

/**
 * @author olj
 *  Denne resursen er knyttet til startsiden for radio applikasjonen
 */
public class RapporterRadioStartServerResourceHTML extends RadioSessionServer {

	
	private String delMelding = "delmelding";
	private String meldeTxtId = "melding";
	private String passordCheck = "none";
	private String displayPassord = "passord";

	public String getDisplayPassord() {
		return displayPassord;
	}

	public void setDisplayPassord(String displayPassord) {
		this.displayPassord = displayPassord;
	}

	public String getPassordCheck() {
		return passordCheck;
	}

	public void setPassordCheck(String passordCheck) {
		this.passordCheck = passordCheck;
	}

	public String getDelMelding() {
		return delMelding;
	}

	public void setDelMelding(String delMelding) {
		this.delMelding = delMelding;
	}


	public String getMeldeTxtId() {
		return meldeTxtId;
	}

	public void setMeldeTxtId(String meldeTxtId) {
		this.meldeTxtId = meldeTxtId;
	}


	/**
	 * getStartpage
	 * Denne rutinen starter med startside.html
	 * Denne rutinen henter inn nødvendige session objekter og  setter opp nettsiden for Acem radio
	 * @return
	 */
	@Get
	public Representation getStartpage() {


	     Reference reference = new Reference(getReference(),"..").getTargetRef();
	     Request request = getRequest();
	     FileModel translist = new FileModel();
	     translist.setFilePath("c:\\ullern\\acem\\radio\\translist.txt");
	     translist.createLines();
	     Stream lines = translist.getLines();
	     String firstLine = "tom";
	     DateTimeFormatter translistformatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
	     List sendeListe = translist.sendeListe();
	     int len = sendeListe.size(); 
	     String sisteLinje = (String)sendeListe.get(len-1);
	     char sep = '-';
	     String sisteDato = "20" + translist.extractString(sisteLinje,sep,0);
	     
	     Map<String, Object> dataModel = new HashMap<String, Object>();
	     startDato = LocalDate.parse(sisteDato, translistformatter);
	     String meldingsText = " ";
	     SimpleScalar simple = new SimpleScalar(meldingsText);
	     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MMM.yyyy");
	     LocalDate sluttDato = LocalDate.now();
	     String formEndDate = sluttDato.format(formatter);
	     String formDate = startDato.format(formatter);
//	     LocalDate formStartdate = LocalDate.parse(formDate);
		 dataModel.put(meldeTxtId,simple );
		 dataModel.put(startDatoId, formDate);
		 dataModel.put(sluttdatoId, formEndDate);
//		 SimpleScalar pwd = new SimpleScalar(passordCheck);
//		 dataModel.put(displayPassord,pwd);
		 sessionAdmin.setSessionObject(request, formDate, startDatoId);
		 sessionAdmin.setSessionObject(request, startDato, startdatoDate);
		 sessionAdmin.setSessionObject(request, translist, fileModelId);
	     LocalReference pakke = LocalReference.createClapReference(LocalReference.CLAP_CLASS,
                 "/radio");
	    
	     LocalReference localUri = new LocalReference(reference);
	     
// Denne client resource forholder seg til src/main/resource katalogen !!!	
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/radio/startside.html"));

	        Representation pasientkomplikasjonFtl = clres2.get();

	        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
	                MediaType.TEXT_HTML);
		 return templatemapRep;
	
	}
	
    /**
     * storeHemovigilans
     * Denne rutinen rutinen kjøres dersom epost og passord er gitt fra bruker.
     * Den tar imot epost og passord og henter frem riktig meldingsinformasjon fra
     * databasen basert på melders id
     * @param form
     * @return
     */
    /**
     * @param form
     * @return
     
     */
    @Post
    public Representation storestartPage(Form form)  {
    
 	    Map<String, Object> dataModel = new HashMap<String, Object>();

	    Request request = getRequest();
	    
	    FileModel translist = (FileModel)sessionAdmin.getSessionObject(request, fileModelId);
	    translist.setFilePath("c:\\ullern\\acem\\radio\\translist.txt");
	    translist.createLines();
	    Stream lines = translist.getLines();
	    String firstLine = "tom";
/*	    try {
			firstLine = translist.readlines((BufferedReader r) -> r.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println(firstLine);*/
/*	    Map<String,List> alleMeldinger = new HashMap<String,List>();
 	    List<Vigilansmelding> meldinger = null;
 //	    List delMeldinger = null;
 	    List<Vigilansmelding> andreMeldinger = null;
 	    List<Vigilansmelding> pasientMeldinger = null;
 	    List<Vigilansmelding> giverMeldinger = null;*/
 	    
    	if(form == null){
    		invalidateSessionobjects();
    	}
/*
 * Verdier angitt av bruker    	
 */
    	String melderEpost = null;
    	String melderPassord = null;
    	String meldingsNokkel = null;
/*
 * Verdier fra database
 */
		String name ="";
		String passord = "";

    	Long melderid = null; 
    	Parameter nyttPassord = form.getFirst("nyttpassord");
        String page = "../hemovigilans/melder_rapport.html"; 
        LocalDate sluttDato = null;
        DateTimeFormatter endformatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");
        for (Parameter entry : form) {
			if (entry.getValue() != null && !(entry.getValue().equals(""))){
					System.out.println(entry.getName() + "=" + entry.getValue());
					if (entry.getName().equals("slutt-date")){
//						sluttDato.parse(entry.getValue());	
						sluttDato = LocalDate.parse(entry.getValue(), endformatter);
					}

			}
			
    	}
		Parameter formValue = form.getFirst("datohendt"); // Bruker oppgir sluttdato
	     Reference reference = new Reference(getReference(),"..").getTargetRef();
	     String meldingsText = " ";
	     SimpleScalar simple = new SimpleScalar(meldingsText);
	     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MMM.yyyy");
	     String formDate = startDato.format(formatter);
	     String formEndDate = sluttDato.format(formatter);
//	     LocalDate formStartdate = LocalDate.parse(formDate);
		 dataModel.put(meldeTxtId,simple );
		 dataModel.put(startDatoId, formDate);
		 dataModel.put(sluttdatoId, formEndDate);
//		 SimpleScalar pwd = new SimpleScalar(passordCheck);
//		 dataModel.put(displayPassord,pwd);
	     LocalReference pakke = LocalReference.createClapReference(LocalReference.CLAP_CLASS,
                "/radio");
	    
	     LocalReference localUri = new LocalReference(reference);
	
//Denne client resource forholder seg til src/main/resource katalogen !!!	
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/radio/startside.html"));

	        Representation pasientkomplikasjonFtl = clres2.get();

	        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
	                MediaType.TEXT_HTML);

    	return templatemapRep;
    }
}
