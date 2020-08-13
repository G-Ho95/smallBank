package project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import project.dto.AnumberDto;
import project.dto.InoutDto;
import test.db.JDBCUtil;

public class InoutDao {
	public int inoutSelect(AnumberDto dto) { // �������� �ִ��� Ȯ���ϴ� �޼ҵ�
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "select cnum,anum,pwd\r\n" + "from anumber\r\n" + "where cnum=? AND anum=? AND pwd=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getCnum());
			pstmt.setInt(2, dto.getAnum());
			pstmt.setString(3, dto.getPwd());
			int n = pstmt.executeUpdate();
			return n;
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			return -1;
		} finally {
			JDBCUtil.close(null, pstmt, con);
		}
	}

	public void inoutInsert(int cnum, int anum, int inmoy) { // �Ա�ó��
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		try {
			con = JDBCUtil.getConn();
			con.setAutoCommit(false);// �ڵ�Ŀ�� ����
			String sql1 = "insert into inouttable(innum,cnum,anum,trannum,inmoy,ddate) "
					+ "values(inout_seq.nextval,?,?,10,?,sysdate)";
			pstmt = con.prepareStatement(sql1);
			pstmt.setInt(1, cnum);
			pstmt.setInt(2, anum);
			pstmt.setInt(3, inmoy);
			int a = pstmt.executeUpdate();

			String sql2 = "update anumber set tot=(tot+?) where cnum=? and anum=?";
			pstmt2 = con.prepareStatement(sql2);
			pstmt2.setInt(1, inmoy);
			pstmt2.setInt(2, cnum);
			pstmt2.setInt(3, anum);
			int b = pstmt2.executeUpdate();
			con.commit();
			System.out.println("�ԱݿϷ�!");
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			System.out.println("�Աݿ���!");
			try {
				con.rollback();
			} catch (SQLException s) {
				System.out.println(s.getMessage());
			}
		} finally {
			JDBCUtil.close(pstmt2);
			JDBCUtil.close(pstmt);
			JDBCUtil.close(con);
		}
	}

	public void outUpdate(int cnum, int anum, int outmoy) {// ���ó��
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		try {
			con = JDBCUtil.getConn();
			con.setAutoCommit(false); // �ڵ�Ŀ�� ����
			String sql = "insert into inouttable(innum,cnum,anum,trannum,outmoy,ddate)"
					+ "values(inout_seq.nextval,?,?,20,?,sysdate)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cnum);
			pstmt.setInt(2, anum);
			pstmt.setInt(3, outmoy);
			pstmt.executeUpdate();

			String sql2 = "update anumber set tot=(tot-?) where cnum=? and anum=?";
			pstmt2 = con.prepareStatement(sql2);
			pstmt2.setInt(1, outmoy);
			pstmt2.setInt(2, cnum);
			pstmt2.setInt(3, anum);
			pstmt2.executeUpdate();
			con.commit();// Ŀ��
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		} finally {
			JDBCUtil.close(pstmt2);
			JDBCUtil.close(pstmt);
			JDBCUtil.close(con);
		}
	}

	public int selectTot(int cnum, int anum) {// ���� ����ݾ� �̱�
		Connection con = null;
		AnumberDto dto = new AnumberDto();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "select tot from anumber where cnum=? and anum=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cnum);
			pstmt.setInt(2, anum);
			rs = pstmt.executeQuery();
			rs.next();
			int moy = rs.getInt("tot");
			return moy;
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			return -1;
		} finally {
			JDBCUtil.close(rs, pstmt, con);
		}
	}

	public int innumSel() { // �ŷ���ȣ:������ ��ȣ �̾ƿ���
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			int num = 0;
			con = JDBCUtil.getConn();
			String sql = "select innum from inouttable order by innum desc";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				num = rs.getInt("innum");
			}
			return num;
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			return -1;
		} finally {
			JDBCUtil.close(rs, pstmt, con);
		}
	}

	public int traninfoInsert(int anum, int cnum, int inmoy, int tot) {
		Connection con = null;// �Ա� �ŷ����� �߰��ϴ� �޼ҵ�

		PreparedStatement pstmt = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "insert into traninfo(innum,trannum,anum,cnum,inmoy,balance,ddate) "
					+ "values(?,10,?,?,?,?,sysdate)";
			pstmt = con.prepareStatement(sql);
			InoutDao dao = new InoutDao();
			int num = dao.innumSel();
			pstmt.setInt(1, num);
			pstmt.setInt(2, anum);
			pstmt.setInt(3, cnum);
			pstmt.setInt(4, inmoy);
			pstmt.setInt(5, tot);
			int n = pstmt.executeUpdate();
			return n;
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			return -1;
		} finally {
			JDBCUtil.close(null, pstmt, con);
		}
	}

	public int traninfoInsertOut(int anum, int cnum, int outmoy, int tot) {// ��ݰŷ������߰�
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "insert into traninfo(innum,trannum,anum,cnum,outmoy,balance,ddate)"
					+ "values(?,20,?,?,?,?,sysdate)";
			pstmt = con.prepareStatement(sql);
			int num = new InoutDao().innumSel();
			pstmt.setInt(1, num);
			pstmt.setInt(2, anum);
			pstmt.setInt(3, cnum);
			pstmt.setInt(4, outmoy);
			pstmt.setInt(5, tot);
			int n = pstmt.executeUpdate();
			return n;
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			return -1;
		} finally {
			JDBCUtil.close(null, pstmt, con);
		}
	}

	public int sendmoy(InoutDto dto) { // ����ȣ, ���¹�ȣ �Է¹޾Ƽ� ��ݰ��� �ݾ� Ȯ��
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JDBCUtil.getConn();
			String sql = "select * from anumber where anum=? and cnum=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getAnum());
			pstmt.setInt(2, dto.getCnum());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int n = rs.getInt("tot");
				return n;
			} else {
				System.out.println("��ġ�ϴ� ������ �����ϴ�.");
				return 0;
			}
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			return -1;
		} finally {
			JDBCUtil.close(rs, pstmt, con);
		}
	}

	public void send(InoutDto dto) { //������ü �����»��--
		Connection con = null;
		PreparedStatement pstmt=null;
		PreparedStatement pstmt2=null;
		try {
			con = JDBCUtil.getConn();
			con.setAutoCommit(false); //�ڵ�Ŀ������
			String sql="insert into inouttable(innum,cnum,anum,trannum,outmoy,ddate) "
					+ "values(inout_seq.nextval,?,?,40,?,sysdate)";//�����(�����»��)
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, dto.getCnum());
			pstmt.setInt(2, dto.getAnum());
			pstmt.setInt(3, dto.getOutmoy());
			pstmt.executeUpdate();
			
			String sql2="update anumber set tot=(tot-?) "
					+ "where cnum=? and anum=?";//�������(�޴»��)
			pstmt2=con.prepareStatement(sql2);
			pstmt2.setInt(1, dto.getOutmoy());
			pstmt2.setInt(2, dto.getCnum());
			pstmt2.setInt(3, dto.getAnum());
			pstmt2.executeUpdate();
			con.commit();
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}finally {
			JDBCUtil.close(pstmt2);
			JDBCUtil.close(null, pstmt, con);
		}
	}
	public void receive(InoutDto dto) { //������ü �޴»��
		Connection con = null;
		PreparedStatement pstmt=null;
		PreparedStatement pstmt2=null;
		try {
			con=JDBCUtil.getConn();
			con.setAutoCommit(false);
			String sql="insert into inouttable(innum,cnum,anum,trannum,inmoy,ddate) "
					+ "values(inout_seq.nextval,?,?,10,?,sysdate)";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, dto.getCnum());
			pstmt.setInt(2, dto.getAnum());
			pstmt.setInt(3, dto.getInmoy());
			pstmt.executeUpdate();
			
			String sql2="update anumber set tot=(tot+?) "
					+ "where cnum=? and anum=?";
			pstmt2=con.prepareStatement(sql2);
			pstmt2.setInt(1, dto.getInmoy());
			pstmt2.setInt(2, dto.getCnum());
			pstmt2.setInt(3, dto.getAnum());
			pstmt2.executeUpdate();
			con.commit();
		
		}catch(SQLException se) {
			System.out.println(se.getMessage());
		}finally {
			JDBCUtil.close(pstmt2);
			JDBCUtil.close(null, pstmt, con);
		}
	}

}
