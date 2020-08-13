package project.dto;

import java.sql.Date;

public class InoutDto {
	private int cnum;
	private int anum;
	private int trannum;
	private int inmoy;
	private int outmoy;
	private Date ddate;
	public InoutDto() {}
	public InoutDto(int cnum,int anum,int trannum,int inmoy,int outmoy, Date ddate) { 
		this.cnum=cnum;
		this.anum=anum;
		this.trannum=trannum;
		this.inmoy=inmoy;
		this.outmoy=outmoy;
		this.ddate=ddate;
	}
	public InoutDto(int cnum,int anum,int trannum) { 
		this.cnum=cnum;
		this.anum=anum;
		this.trannum=trannum;
	}
	public int getCnum() {
		return cnum;
	}
	public void setCnum(int cnum) {
		this.cnum = cnum;
	}
	public int getAnum() {
		return anum;
	}
	public void setAnum(int anum) {
		this.anum = anum;
	}
	public int getTrannum() {
		return trannum;
	}
	public void setTrannum(int trannum) {
		this.trannum = trannum;
	}
	public int getInmoy() {
		return inmoy;
	}
	public void setInmoy(int inmoy) {
		this.inmoy = inmoy;
	}
	public int getOutmoy() {
		return outmoy;
	}
	public void setOutmoy(int outmoy) {
		this.outmoy = outmoy;
	}
	public Date getDdate() {
		return ddate;
	}
	public void setDdate(Date ddate) {
		this.ddate = ddate;
	}
	
}
