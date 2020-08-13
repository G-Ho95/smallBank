package project.dto;

import java.sql.Date;

public class CinfoDto {
	private int cnum;
	private String cname;
	private String jumin;
	private String phone;
	private String addr;
	private Date regdate;
	
	public CinfoDto(){}
	public CinfoDto(int cnum,String cname, String jumin, String phone,String addr,Date regdate) {
		this.cnum=cnum;
		this.cname=cname;
		this.jumin=jumin;
		this.phone=phone;
		this.addr=addr;
		this.regdate=regdate;
	}
	
	
	public CinfoDto(String cname, String jumin, String phone,String addr,Date regdate) {
		this.cname=cname;
		this.jumin=jumin;
		this.phone=phone;
		this.addr=addr;
		this.regdate=regdate;
	}
	public CinfoDto(int cnum,String phone,String addr) {
		this.cnum=cnum;
		this.phone=phone;
		this.addr=addr;
	}
	public int getCnum() {
		return cnum;
	}
	public void setCnum(int cnum) {
		this.cnum = cnum;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getJumin() {
		return jumin;
	}
	public void setJumin(String jumin) {
		this.jumin = jumin;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone=phone;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
	
}
