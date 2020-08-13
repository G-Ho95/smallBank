package project.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import project.dto.InoutDto;
import project.dto.TraninfoDto;
import test.db.JDBCUtil;

public class TraninfoDao {
	public int traninfoSend(TraninfoDto dto) {// 이체거래내역 추가
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "insert into traninfo(innum,trannum,anum,cnum,outmoy,balance,ddate)"
					+ "values(?,40,?,?,?,?,sysdate)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getInnum());
			pstmt.setInt(2, dto.getAnum());
			pstmt.setInt(3, dto.getCnum());
			pstmt.setInt(4, dto.getOutmoy());
			pstmt.setInt(5, dto.getBalance());
			int n = pstmt.executeUpdate();
			return n;
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			return -1;
		} finally {
			JDBCUtil.close(null, pstmt, con);
		}
	}

	public int traninfoReceive(TraninfoDto dto) {// 입금거래내역 추가
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "insert into traninfo(innum,trannum,anum,cnum,inmoy,balance,ddate)"
					+ "values(?,10,?,?,?,?,sysdate)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getInnum());
			pstmt.setInt(2, dto.getAnum());
			pstmt.setInt(3, dto.getCnum());
			pstmt.setInt(4, dto.getInmoy());
			pstmt.setInt(5, dto.getBalance());
			int n = pstmt.executeUpdate();
			return n;
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			return -1;
		} finally {
			JDBCUtil.close(null, pstmt, con);
		}
	}

	public ArrayList<TraninfoDto> allSearch() {// 전체거래내역 조회
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		ArrayList<TraninfoDto> list = new ArrayList<TraninfoDto>();
		try {
			con = JDBCUtil.getConn();
			String sql = "select t.anum 계좌번호, cname 예금주,  tranname 거래내용, t.inmoy 입금액, "
					+ "t.outmoy 출금액, balance 거래후잔액, t.ddate 거래일자 \r\n"
					+ "from traninfo t, cinfo c, trantypetable tt\r\n"
					+ "where t.cnum=c.cnum and t.trannum=tt.trannum\r\n" + "order by t.innum";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				String colName = rsmd.getColumnName(i);
				System.out.print(colName + "\t\t");
			}
			System.out.println();
			System.out.println(
					"=====================================================================================================================");
			while (rs.next()) {
				int anum = rs.getInt("계좌번호");
				String cname = rs.getString("예금주");
				String tranname = rs.getString("거래내용");
				int inmoy = rs.getInt("입금액");
				int outmoy = rs.getInt("출금액");
				int balance = rs.getInt("거래후잔액");
				Date ddate = rs.getDate("거래일자");
				TraninfoDto dto = new TraninfoDto(anum, cname, tranname, inmoy, outmoy, balance, ddate);
				list.add(dto);
			}
			return list;
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			return null;
		} finally {
			JDBCUtil.close(rs, pstmt, con);
		}
	}

	public ArrayList<TraninfoDto> anumSearch(InoutDto dto) {// 계좌번호 입력받아 거래내역 조회
		Connection con = null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ResultSetMetaData rsmd=null;
		ArrayList<TraninfoDto> list=new ArrayList<TraninfoDto>();
		try {
			con = JDBCUtil.getConn();
			String sql="select  t.ddate 거래일자, tranname 거래내용, t.inmoy 입금액, t.outmoy 출금액, balance 거래후잔액\r\n" + 
					"from traninfo t, trantypetable tt\r\n" + 
					"where t.trannum=tt.trannum and anum=?\r\n" + 
					"order by ddate";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, dto.getAnum());
			rs=pstmt.executeQuery();
			rsmd=rs.getMetaData();
			for(int i=1;i<=rsmd.getColumnCount();i++) {
				String colName=rsmd.getColumnName(i);
				System.out.print(colName+"\t\t");
			}
			System.out.println();
			System.out.println("============================================================================");
			while(rs.next()) {
				Date ddate=rs.getDate("거래일자");
				String tranname=rs.getString("거래내용");
				int inmoy=rs.getInt("입금액");
				int outmoy=rs.getInt("출금액");
				int balance=rs.getInt("거래후잔액");
				TraninfoDto dto2=new TraninfoDto(0,null,tranname,inmoy,outmoy,balance,ddate);
				list.add(dto2);
			}
			return list;
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			return null;
		}finally {
			JDBCUtil.close(rs, pstmt, con);
		}
	}

}
