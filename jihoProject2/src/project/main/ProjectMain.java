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
			System.out.println("▶원하는 업무 버튼을 눌러주세요.");
			System.out.println(
					"1.고객정보등록 2.고객정보수정 3.보유계좌조회 4.입출금 계좌개설 " + "   5.입금\t    \n6.출금\t "
							+ "7.계좌이체    8.거래내역 조회    9.전체 고객정보조회    10.종료");
			System.out.println("-------------------------------------------------------------------");
			int bt = scan.nextInt();
			switch (bt) {
			case 1:
				mainM.insert(); // 고객정보등록
				break;
			case 2:
				mainM.update(); // 고객정보수정
				break;
			case 3:
				mainM.listAll(); // 보유계좌조회(고객번호 입력 후 조회)
				break;
			case 4:
				mainM.anumberInsert(); // 계좌개설
				break;
			case 5:
				mainM.inmoy(); // 입금
				break;
			case 6:
				mainM.outmoy();// 출금
				break;
			case 7:
				mainM.sendmoney();//계좌이체
				break;
			case 8:
				mainM.search();
				break;
			case 9://고객정보 전체조회
				mainM.allCinfo();
				break;
			case 10:
				System.out.println("▶업무종료");
				System.exit(0); // 종료
			}
		}
	}
}
