package com.upm.etsist.dto;

import java.util.Date;

public class HarvestRequestDTO {
	private String baseURL;
	private String metadataPrefix;
	private String set;
	private Date from=null;
	private Date until=null;
	private String outdir=null;
	private boolean splitBySet=true;
	private String zipName="";
	private String zDir="";
	private boolean writeHeaders=false;
	boolean harvestAll=false;
	boolean harvestAllIfNoDeletedRecord=true;
	public String getBaseURL() {
		return baseURL;
	}
	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}
	public String getMetadataPrefix() {
		return metadataPrefix;
	}
	public void setMetadataPrefix(String metadataPrefix) {
		this.metadataPrefix = metadataPrefix;
	}
	public String getSet() {
		return set;
	}
	public void setSet(String set) {
		this.set = set;
	}
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public Date getUntil() {
		return until;
	}
	public void setUntil(Date until) {
		this.until = until;
	}
	public String getOutdir() {
		return outdir;
	}
	public void setOutdir(String outdir) {
		this.outdir = outdir;
	}
	public boolean isSplitBySet() {
		return splitBySet;
	}
	public void setSplitBySet(boolean splitBySet) {
		this.splitBySet = splitBySet;
	}
	public String getZipName() {
		return zipName;
	}
	public void setZipName(String zipName) {
		this.zipName = zipName;
	}
	public String getzDir() {
		return zDir;
	}
	public void setzDir(String zDir) {
		this.zDir = zDir;
	}
	public boolean isWriteHeaders() {
		return writeHeaders;
	}
	public void setWriteHeaders(boolean writeHeaders) {
		this.writeHeaders = writeHeaders;
	}
	public boolean isHarvestAll() {
		return harvestAll;
	}
	public void setHarvestAll(boolean harvestAll) {
		this.harvestAll = harvestAll;
	}
	public boolean isHarvestAllIfNoDeletedRecord() {
		return harvestAllIfNoDeletedRecord;
	}
	public void setHarvestAllIfNoDeletedRecord(boolean harvestAllIfNoDeletedRecord) {
		this.harvestAllIfNoDeletedRecord = harvestAllIfNoDeletedRecord;
	}
	@Override
	public String toString() {
		return "UpdateRequestDTO [baseURL=" + baseURL + ", metadataPrefix=" + metadataPrefix + ", set=" + set
				+ ", from=" + from + ", until=" + until + ", outdir=" + outdir + ", splitBySet=" + splitBySet
				+ ", zipName=" + zipName + ", zDir=" + zDir + ", writeHeaders=" + writeHeaders + ", harvestAll="
				+ harvestAll + ", harvestAllIfNoDeletedRecord=" + harvestAllIfNoDeletedRecord + "]";
	}
	public HarvestRequestDTO(String baseURL, String metadataPrefix, String set, Date from, Date until, String outdir,
			boolean splitBySet, String zipName, String zDir, boolean writeHeaders, boolean harvestAll,
			boolean harvestAllIfNoDeletedRecord) {
		super();
		this.baseURL = baseURL;
		this.metadataPrefix = metadataPrefix;
		this.set = set;
		this.from = from;
		this.until = until;
		this.outdir = outdir;
		this.splitBySet = splitBySet;
		this.zipName = zipName;
		this.zDir = zDir;
		this.writeHeaders = writeHeaders;
		this.harvestAll = harvestAll;
		this.harvestAllIfNoDeletedRecord = harvestAllIfNoDeletedRecord;
	}
	public HarvestRequestDTO() {
		super();
	}

}
