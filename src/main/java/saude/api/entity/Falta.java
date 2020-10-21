package saude.api.entity;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Falta {

	private String local;
	private LocalDateTime data;
	
	
	
	public Falta(String local, LocalDateTime data) {
		this.local = local;
		this.data = data;
	}



	public Falta() {
		this.local = "";
		this.data = LocalDateTime.parse("1970-01-01T01:00:00.000");
	}



	public String getLocal() {
		return local;
	}



	public void setLocal(String local) {
		this.local = local;
	}



	public LocalDateTime getData() {
		return data;
	}



	public void setData(LocalDateTime data) {
		this.data = data;
	}



	



	
	
	
	
	
	
	
	
}
