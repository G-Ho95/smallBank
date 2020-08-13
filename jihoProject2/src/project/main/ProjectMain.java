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

public class ProjectMain {
	static Scanner scan = new Scanner(System.in);
	static CinfoDao dao = new CinfoDao();
	static AnumberDao adao = new AnumberDao();
	static InoutDao inoutdao = new InoutDao();
	static TraninfoDao traninfodao = new TraninfoDao();

	public static void main(String[] args) {
		while (true) {
			System.out.println("-------------------------------------------------------------------");
			System.out.println("�����ϴ� ���� ��ư�� �����ּ���.");
			System.out.println(
					"1.��������� 2.���������� 3.����������ȸ 4.����� ���°��� " + "   5.�Ա�\t    \n6.���\t "
							+ "7.������ü    8.�ŷ����� ��ȸ    9.��ü ��������ȸ    10.����");
			System.out.println("-------------------------------------------------------------------");
			int bt = scan.nextInt();
			switch (bt) {
			case 1:
				mainM.insert(); // ���������
				break;
			case 2:
				mainM.update(); // ����������
				break;
			case 3:
				mainM.listAll(); // ����������ȸ(����ȣ �Է� �� ��ȸ)
				break;
			case 4:
				mainM.anumberInsert(); // ���°���
				break;
			case 5:
				mainM.inmoy(); // �Ա�
				break;
			case 6:
				mainM.outmoy();// ���
				break;
			case 7:
				mainM.sendmoney();//������ü
				break;
			case 8:
				mainM.search();
				break;
			case 9://������ ��ü��ȸ
				mainM.allCinfo();
				break;
			case 10:
				System.out.println("����������");
				System.exit(0); // ����
			}
		}
	}
}
