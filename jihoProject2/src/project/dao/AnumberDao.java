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
	// ���°���
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

	// ����ȣ �Է� �� �������� ��ȸ
	public ArrayList<AnumberDto> selectAnumber(int cnum) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		ArrayList<AnumberDto> list = new ArrayList<AnumberDto>();
		try {
			con = JDBCUtil.getConn();
			String sql = "select c.cname ������, a.cnum ����ȣ, a.anum ���¹�ȣ, a.tot �ܾ�, a.adate ������\r\n"
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
				String cname = rs.getString("������");
				int num = rs.getInt("����ȣ");
				int anum = rs.getInt("���¹�ȣ");
				int tot = rs.getInt("�ܾ�");
				Date adate = rs.getDate("������");
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

	public String viewName(InoutDto dto) {// ���¹�ȣ �Է� �� ������ ��ȸ
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

	public int viewCnum(InoutDto dto) {// ���¹�ȣ �Է��� ����ȣ ��ȸ
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
