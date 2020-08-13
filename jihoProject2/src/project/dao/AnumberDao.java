package project.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import project.dto.AnumberDto;
import project.dto.CinfoDto;
import project.dto.InoutDto;
import test.db.JDBCUtil;

public class AnumberDao {
	// 계좌개설
	public int anumberInsert(AnumberDto dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "insert into anumber values(anum_seq.nextval,?,?,sysdate,0)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getCnum());
			pstmt.setString(2, dto.getPwd());
			int n = pstmt.executeUpdate();
			return n;

		} catch (SQLException se) {
			System.out.println(se.getMessage());
			return -1;
		} finally {
			JDBCUtil.close(null, pstmt, con);
		}
	}

	// 고객번호 입력 후 보유계좌 조회
	public ArrayList<AnumberDto> selectAnumber(int cnum) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		ArrayList<AnumberDto> list = new ArrayList<AnumberDto>();
		try {
			con = JDBCUtil.getConn();
			String sql = "select c.cname 예금주, a.cnum 고객번호, a.anum 계좌번호, a.tot 잔액, a.adate 개설일\r\n"
					+ "	from anumber a,cinfo c\r\n" + "	where a.cnum=c.cnum and c.cnum=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cnum);
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				String colName = rsmd.getColumnName(i);
				System.out.print(colName + "\t");

			}
			System.out.println();
			System.out.println("===================================================");
			while (rs.next()) {
				String cname = rs.getString("예금주");
				int num = rs.getInt("고객번호");
				int anum = rs.getInt("계좌번호");
				int tot = rs.getInt("잔액");
				Date adate = rs.getDate("개설일");
				AnumberDto dto = new AnumberDto(cname, cnum, anum, null, tot, adate);
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

	public String viewName(InoutDto dto) {// 계좌번호 입력 후 예금주 조회
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "select cname, anum from cinfo c,anumber a\r\n" + "where c.cnum=a.cnum and anum=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getAnum());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String name = rs.getString("cname");
				return name;
			} else {
				return null;
			}
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			return null;
		} finally {
			JDBCUtil.close(rs, pstmt, con);
		}
	}

	public int viewCnum(InoutDto dto) {// 계좌번호 입력후 고객번호 조회
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "select cnum from anumber where anum=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getAnum());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int cnum = rs.getInt("cnum");
				return cnum;
			} else {
				return -1;
			}
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			return -1;
		} finally {
			JDBCUtil.close(rs, pstmt, con);
		}
	}

	public int printAnum(AnumberDto dto) {
		Connection con = null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int anum=0;
		try {
			con = JDBCUtil.getConn();
			String sql="select anum from anumber where cnum=? order by anum desc";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, dto.getCnum());
			rs=pstmt.executeQuery();
			if(rs.next()) {
				anum=rs.getInt("anum");
			}
			return anum;
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			return -1;
		}finally {
			JDBCUtil.close(rs, pstmt, con);
		}
	}

}
