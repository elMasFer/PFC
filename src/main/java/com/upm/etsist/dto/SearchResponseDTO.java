package com.upm.etsist.dto;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class SearchResponseDTO {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name="ID")
    private long id;

    @Column(name="SPA", length = 10000, nullable = false)
    private String spanishText;

    @Column(name="ENG", length = 10000, nullable = false)
    private String englishText;

    @Column(name="URL", length = 100, nullable = true)
    private String url;

    @Column(name="CHK")
    private boolean validated;
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSpanishText() {
		return spanishText;
	}
	public void setSpanishText(String spanishText) {
		this.spanishText = spanishText;
	}
	public String getEnglishText() {
		return englishText;
	}
	public void setEnglishText(String englishText) {
		this.englishText = englishText;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isValidated() {
		return validated;
	}
	public void setValidated(boolean validated) {
		this.validated = validated;
	}
	public SearchResponseDTO(String spanishText, String englishText) {
		super();
		this.spanishText = spanishText;
		this.englishText = englishText;
	}
	public SearchResponseDTO() {
		super();
		this.spanishText = "";
		this.englishText = "";
		this.url = "";
		this.validated = false;
	}
	@Override
	public String toString() {
		return "SearchResponseDTO [id=" + id + ", spanishText=" + spanishText + ", englishText=" + englishText
				+ ", url=" + url + ", validated=" + validated + "]";
	}
	

}
