package project.dto;

import java.sql.Date;

public class TraninfoDto {
	private int innum;
	private int trannum;
	private int anum;
	private int cnum;
	private int inmoy;
	private int outmoy;
	private int balance;
	private String cname;
	private String tranname;
	private Date ddate;
	public TraninfoDto() {}
	public TraninfoDto(int innum,int trannum,int anum,int cnum,int inmoy,
			int outmoy,int balance,Date ddate) {
		this.innum=innum;
		this.trannum=trannum;
		this.anum=anum;
		this.cnum=cnum;
		this.inmoy=inmoy;
		this.outmoy=outmoy;
		this.balance=balance;
		this.ddate=ddate;
	}
	public TraninfoDto(int anum, String cname,String tranname,int inmoy,
			int outmoy,int balance,Date ddate) {//ÂÉÀÎÁ¶È¸
		this.anum=anum;
		this.cname=cname;
		this.tranname=tranname;
		this.inmoy=inmoy;
		this.outmoy=outmoy;
		this.balance=balance;
		this.ddate=ddate;
	}

	public String getTranname() {
		return tranname;
	}
	public void setTranname() {
		this.tranname=tranname;
	}
	
	public String getCname() {
		return cname;
	}
	public void setCname() {
		this.cname=cname;
	}
	public int getInnum() {
		return innum;
	}
	public void setInnum(int innum) {
		this.innum = innum;
	}
	public int getTrannum() {
		return trannum;
	}
	public void setTrannum(int trannum) {
		this.trannum = trannum;
	}
	public int getAnum() {
		return anum;
	}
	public void setAnum(int anum) {
		this.anum = anum;
	}
	public int getCnum() {
		return cnum;
	}
	public void setCnum(int cnum) {
		this.cnum = cnum;
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
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public Date getDdate() {
		return ddate;
	}
	public void setDdate(Date ddate) {
		this.ddate = ddate;
	}
	
}
