package project.main;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

import project.dao.AnumberDao;
import project.dao.CinfoDao;
import project.dao.InoutDao;
import project.dao.TraninfoDao;
import project.dto.AnumberDto;
import project.dto.CinfoDto;
import project.dto.InoutDto;
import project.dto.TraninfoDto;

public class mainM {
	static Scanner scan = new Scanner(System.in);
	static CinfoDao dao = new CinfoDao();
	static AnumberDao adao = new AnumberDao();
	static InoutDao inoutdao = new InoutDao();
	static TraninfoDao traninfodao = new TraninfoDao();
	
	public static void insert() {// 1.������ ���
		System.out.println("=========================");
		System.out.println("[ 1. ������ ��� ]");
		System.out.println("�����̸� �Է�:");
		String cname = scan.next();
		System.out.println("���ֹε�Ϲ�ȣ �Է�:");
		String jumin = scan.next();
		String restr = jumin.replaceAll("[^0-9]", "");
		if (restr.length() < 13) {
			System.out.println("������: �ֹι�ȣ�� 13�ڸ��� �Է����ּ���.");
			insert();
		} else {
			System.out.println("���� �ڵ�����ȣ �Է�");
			String phone = scan.next();
			System.out.println("���ּ��Է�: ");
			String addr = scan.next();
			System.out.println("=================");
			CinfoDto dto = new CinfoDto(cname, jumin, phone, addr, null);
			int n = dao.insert(dto);
			if (n <= 0) {
				System.out.println("������� ����! �Ф�");
				System.out.println("=================");
			} else {
				System.out.println("��" + cname + "�� ����� ����!");
				System.out.println("=================");
			}
		}
	}
	
	public static void update() {// 2.������ ����
		System.out.println("=========================");
		System.out.println("[ 2. ������ ���� ]");
		System.out.println("�������� ����ȣ �Է�:");
		int cnum = scan.nextInt();
		System.out.println("����ȭ��ȣ �Է� : ");
		String phone = scan.next();
		System.out.println("���ּ� �Է� : ");
		String addr = scan.next();
		System.out.println("====================");
		CinfoDto dto = new CinfoDto(cnum, phone, addr);
		int n = dao.update(dto);
		if (n <= 0) {
			System.out.println("��ȸ������ ��������");
		} else {
			System.out.println("��" + n + "���� ȸ������ �����Ϸ�!");
		}
	}
	
	public static void listAll() {// 3.�������� ��ȸ
		System.out.println("=========================");
		System.out.println("[ 3. �������� ��ȸ ]");
		System.out.println("������ȣ �Է� : ");
		int num = scan.nextInt();
		ArrayList<AnumberDto> list = adao.selectAnumber(num);
		if (list.size() == 0) {
			System.out.println("��" + num + "�� ���� �������°� �������� �ʽ��ϴ�.");
		}
		for (int i = 0; i < list.size(); i++) {
			AnumberDto dto = list.get(i);
			String cname = dto.getCname();
			int cnum = dto.getCnum();
			int anum = dto.getAnum();
			int tot = dto.getTot();
			Date adate = dto.getAdate();
			System.out.println(cname + "\t" + cnum + "\t" + anum + "\t" + tot + "\t" + adate);
		}
	}
	
	public static void anumberInsert() { // 4.���°���
		CinfoDto dto = new CinfoDto();
		AnumberDto dto2=new AnumberDto();
		System.out.println("=====================");
		System.out.println("[ 4.���°��� ]");
		System.out.println("=====================");
		System.out.println("������ȣ �Է�: ");
		int cnum = scan.nextInt();
		dto.setCnum(cnum);
		System.out.println("����й�ȣ �Է�(4�ڸ� ����): ");
		String pwd = scan.next();
		String restr = pwd.replaceAll("[^0-9]", ""); // ���ڸ� �Է¹ޤ��°�
		if (restr.length() != 4) {
			System.out.println("����й�ȣ�� ���� 4�ڸ��� �Է����ּ���.");
			//anumberInsert();
		} else {
			dto2.setCnum(cnum);
			dto2.setPwd(pwd);
			int n = adao.anumberInsert(dto2);
			if (n <= 0) {
				System.out.println("������ ���� ����!");
			} else {
				System.out.println("====================");
				System.out.println("�����尳�� �Ϸ�!");
				System.out.println("�������� : " + dao.printCname(dto));
				System.out.println("�����¹�ȣ : "+adao.printAnum(dto2));
			}
		}
	}
	public static void inmoy() { // 5.�Ա�
		System.out.println("=========================");
		System.out.println("[ 5. �Ա� ]");
		System.out.println("������ȣ �Է� : ");
		int cnum = scan.nextInt();
		System.out.println("�����¹�ȣ �Է� : ");
		int anum = scan.nextInt();
		System.out.println("����й�ȣ �Է�(����4�ڸ�) : ");
		String pwd = scan.next();
		AnumberDto dto = new AnumberDto(null, cnum, anum, pwd, 0, null);
		int n = inoutdao.inoutSelect(dto);
		if (n == 1) {
			System.out.println("���Ա��� �ݾ� �Է� : ");
			int inmoy = scan.nextInt();
			inoutdao.inoutInsert(cnum, anum, inmoy);// �Ա�
			int tot = inoutdao.selectTot(cnum, anum); // ���� ����ݾ� �����ͼ� �̱�
			int a = inoutdao.traninfoInsert(anum, cnum, inmoy, tot);// ���� ����ݾ� �����ͼ� �ŷ����� �߰�
			if (a == 1) {
				System.out.println("���ŷ����� �߰��Ϸ�!");
			} else {
				System.out.println("���ŷ����� �߰� ����");
			}
		} else {
			System.out.println("������ : �Է��� ������ ��ġ���� �ʽ��ϴ�.");
			inmoy();
		}
	}
	
	public static void outmoy() {// 6.���
		System.out.println("=========================");
		System.out.println("[ 6. ��� ]");
		System.out.println("������ȣ �Է� : ");
		int cnum = scan.nextInt();
		System.out.println("������� ���¹�ȣ �Է� : ");
		int anum = scan.nextInt();
		System.out.println("������ ��й�ȣ �Է�(����4�ڸ�) : ");
		String pwd = scan.next();
		AnumberDto dto = new AnumberDto(null, cnum, anum, pwd, 0, null);
		int n = inoutdao.inoutSelect(dto); // �������� �ִ��� Ȯ��
		if (n == 1) {
			System.out.println("������� �ݾ� �Է� : ");
			int outmoy = scan.nextInt();
			int tongjang = inoutdao.selectTot(cnum, anum);// ���������ܾױ���
			if (outmoy < tongjang) { // �����ܾ׺��� ��ݱݾ��� ������ ����
				inoutdao.outUpdate(cnum, anum, outmoy);// ��ݽ���޼ҵ�
				int tot = inoutdao.selectTot(cnum, anum);// ���� �����ܾ� �̾ƿ��� �޼ҵ�
				int a = inoutdao.traninfoInsertOut(anum, cnum, outmoy, tot);
				if (a == 1) {
					System.out.println("���ŷ����� �߰��Ϸ�!");
				} else {
					System.out.println("���ŷ����� �߰� ����");
				}
			} else {// ����ܾ��� ũ�� ����� ����
				System.out.println("����ݽ��� : ��� �ݾ� �ʰ��Դϴ�.");
			}
		} else {
			System.out.println("������ : �Է��� ������ ��ġ���� �ʽ��ϴ�.");
		}
	}
	
	public static void sendmoney() { // 7.������ü
		System.out.println("=========================");
		System.out.println("[ 7. ������ü ]");
		System.out.println("������ȣ �Է� : ");
		int cnum = scan.nextInt();
		System.out.println("�����¹�ȣ �Է� : ");
		int anum = scan.nextInt();
		InoutDto dto = new InoutDto(cnum, anum, 0);
		int tot = inoutdao.sendmoy(dto);
		System.out.println("������ �ܾ�(��ü ���ɱݾ�) : " + tot + "��");
		System.out.println("����ü�� �ݾ� �Է� : ");
		int send = scan.nextInt();
		if (send <= tot) {
			InoutDto dto2 = new InoutDto();
			System.out.println("������ ���¹�ȣ �Է� : ");
			int sendAnum = scan.nextInt();
			dto2.setAnum(sendAnum);
			System.out.println("�������� : " + adao.viewName(dto2));
			System.out.println("����ü�� ������ �½��ϱ�?");
			System.out.println("�������� 1��, Ʋ���� 2���� �����ּ���.");
			int bt = scan.nextInt();
			if (bt == 1) {
				System.out.println("����й�ȣ �Է� : ");
				String pwd = scan.next();
				AnumberDto dto3 = new AnumberDto(null, cnum, anum, pwd, 0, null);
				int n = inoutdao.inoutSelect(dto3);
				if (n == 1) {
					InoutDto dto4 = new InoutDto(cnum, anum, 40, 0, send, null);
					inoutdao.send(dto4);// ������-->
					// ������ �ŷ����� �߰�-->
					int tot2 = inoutdao.selectTot(cnum, anum);// ���������ܾ�
					int innum = inoutdao.innumSel();// �����������ȣ
					TraninfoDto dto5 = new TraninfoDto(innum, 40, anum, cnum, 0, send, tot2, null);
					int i = traninfodao.traninfoSend(dto5);
					int acnum = adao.viewCnum(dto2);// �޴»�� ���¹�ȣ�־ ����ȣ ��������
					InoutDto dto6 = new InoutDto(acnum, sendAnum, 10, send, 0, null);
					inoutdao.receive(dto6); // ��ü�ޱ�-->ok
					// ��ü�ޱ� �ŷ����� �߰�-->
					int totR = inoutdao.selectTot(acnum, sendAnum);// ���������ܾ�
					int innumR = inoutdao.innumSel();// �����������ȣ
					TraninfoDto dto7 = new TraninfoDto(innumR, 10, sendAnum, acnum, send, 0, totR, null);
					int j = traninfodao.traninfoReceive(dto7);
					System.out.println("��������ü ó���� �Ϸ� �Ǿ����ϴ�!");
				} else {
					System.out.println("������ : ��й�ȣ�� Ʋ�Ƚ��ϴ�.");
				}
			} else if (bt == 2) {
				System.out.println("����ü�۾��� ��ҵǾ����ϴ�.");
			}
		} else {
			System.out.println("������ : ��ü�� �ݾ��� ���� �ܾ׺��� �����ϴ�.");
		}
	}
	
	public static void search() {// 8.�ŷ�������ȸ
		System.out.println("=========================");
		System.out.println("[ 8. �ŷ����� ��ȸ ]");
		System.out.println("�����ϴ� ��ư�� �Է����ּ���.");
		System.out.println("1. ��ü �ŷ����� ��ȸ\t2.���º� �ŷ����� ��ȸ");
		int bt = scan.nextInt();
		if (bt == 1) {
			System.out.println("����ü �� �ŷ�����");
			ArrayList<TraninfoDto> list = traninfodao.allSearch();
			if (list.size() == 0) {
				System.out.println("���ŷ������� �����ϴ�.");
			}
			for (int i = 0; i < list.size(); i++) {
				TraninfoDto dto = list.get(i);
				String cname = dto.getCname();
				int anum = dto.getAnum();
				String tranname = dto.getTranname();
				int inmoy = dto.getInmoy();
				int outmoy = dto.getOutmoy();
				int balance = dto.getBalance();
				Date ddate = dto.getDdate();
				System.out.println(anum + "\t\t" + cname + "\t\t" + tranname + "\t\t" + inmoy + "\t\t" + outmoy + "\t\t"
						+ balance + "\t\t\t" + ddate);
			}
		} else if (bt == 2) {
			InoutDto dto = new InoutDto();
			System.out.println("����ȸ�� ���¹�ȣ�� �Է� : ");
			int anum = scan.nextInt();
			System.out.println("�����¹�ȣ : " + anum);
			dto.setAnum(anum);
			System.out.println("�������� : " + adao.viewName(dto));
			ArrayList<TraninfoDto> list = traninfodao.anumSearch(dto);
			if (list.size() == 0) {
				System.out.println("���ŷ������� �����ϴ�.");
			}
			for (int i = 0; i < list.size(); i++) {
				TraninfoDto dto2 = list.get(i);
				Date ddate = dto2.getDdate();
				String tranname = dto2.getTranname();
				int inmoy = dto2.getInmoy();
				int outmoy = dto2.getOutmoy();
				int balance = dto2.getBalance();
				System.out.println(ddate + "\t" + tranname + "\t\t" + inmoy + "\t\t" + outmoy + "\t\t" + balance);
			}
		}
	}
	public static void allCinfo() {
		System.out.println("=========================");
		System.out.println("[ 9. ��ü������ ��ȸ ]");
		ArrayList<CinfoDto> list=dao.allView();
		for(int i=0;i<list.size();i++) {
			CinfoDto dto=list.get(i);
			Date regdate=dto.getRegdate();
			int cnum=dto.getCnum();
			String cname=dto.getCname();
			String phone=dto.getPhone();
			String addr=dto.getAddr();
			System.out.println(regdate+"\t"+cnum+"\t\t"+cname+"\t\t"+
					phone+"\t\t"+addr);
		}
	}
}
