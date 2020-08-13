package project.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import project.dto.CinfoDto;
import test.db.JDBCUtil;

public class CinfoDao {
	// 회원정보 추가
	public int insert(CinfoDto dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "insert into cinfo values(cinfo_seq.nextval,?,?,?,?,sysdate)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getCname());
			pstmt.setString(2, dto.getJumin());
			pstmt.setString(3, dto.getPhone());// 핸드폰번호
			pstmt.setString(4, dto.getAddr());
			int n = pstmt.executeUpdate();
			return n;
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			return -1;
		} finally {
			JDBCUtil.close(null, pstmt, con);
		}
	}

	// 회원정보 수정
	public int update(CinfoDto dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "update cinfo set phone=?,addr=? where cnum=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getPhone());
			pstmt.setString(2, dto.getAddr());
			pstmt.setInt(3, dto.getCnum());
			int n = pstmt.executeUpdate();
			return n;
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			return -1;
		} finally {
			JDBCUtil.close(null, pstmt, con);
		}
	}

	// 고객번호 입력 후 이름출력
	public String printCname(CinfoDto dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String cname = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "select cname from cinfo where cnum=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getCnum());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cname = rs.getString("cname");
			}
			return cname;
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			return null;
		} finally {
			JDBCUtil.close(rs, pstmt, con);
		}
	}

	public ArrayList<CinfoDto> allView() {
		Connection con = null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ResultSetMetaData rsmd=null;
		ArrayList<CinfoDto> list=new ArrayList<CinfoDto>();
		try {
			con = JDBCUtil.getConn();
			String sql="select regdate 등록일, cnum 고객번호, cname 이름, phone 전화번호, "
					+ "addr 주소 from cinfo";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			rsmd=rs.getMetaData();
			for(int i=1;i<=rsmd.getColumnCount();i++) {
				String colName=rsmd.getColumnName(i);
				System.out.print(colName+"\t\t");
			}
			System.out.println();
			System.out.println(
					"=====================================================================================================================");
			while(rs.next()) {
				Date regdate=rs.getDate("등록일");
				int cnum=rs.getInt("고객번호");
				String cname=rs.getString("이름");
				String phone=rs.getString("전화번호");
				String addr=rs.getString("주소");
				CinfoDto dto=new CinfoDto(cnum, cname, null, phone, addr, regdate);
				list.add(dto);
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
