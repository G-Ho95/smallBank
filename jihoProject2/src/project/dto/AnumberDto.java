package project.dto;

import java.sql.Date;

public class AnumberDto {
	private int anum; //계좌번호
	private int cnum; //고객번호
	private String pwd; //비밀번호
	private Date adate; //개설일자
	private int tot; //통장잔액
	private String cname;//고객이름
	
	public AnumberDto() {}
	public AnumberDto(int anum,String pwd,int tot) {
		this.anum=anum;
		this.pwd=pwd;
		this.tot=tot;
	}
	public AnumberDto(String cname,int cnum,int anum,String pwd,int tot,Date adate) {
		this.cname=cname;
		this.cnum=cnum;
		this.anum=anum;
		this.pwd=pwd;
		this.tot=tot;
		this.adate=adate;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
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
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Date getAdate() {
		return adate;
	}
	public void setAdate(Date adate) {
		this.adate = adate;
	}
	public int getTot() {
		return tot;
	}
	public void setTot(int tot) {
		this.tot = tot;
	}
	
}









