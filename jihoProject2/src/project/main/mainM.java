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
	
	public static void insert() {// 1.고객정보 등록
		System.out.println("=========================");
		System.out.println("[ 1. 고객정보 등록 ]");
		System.out.println("▶고객이름 입력:");
		String cname = scan.next();
		System.out.println("▶주민등록번호 입력:");
		String jumin = scan.next();
		String restr = jumin.replaceAll("[^0-9]", "");
		if (restr.length() < 13) {
			System.out.println("▶오류: 주민번호는 13자리를 입력해주세요.");
			insert();
		} else {
			System.out.println("▶고객 핸드폰번호 입력");
			String phone = scan.next();
			System.out.println("▶주소입력: ");
			String addr = scan.next();
			System.out.println("=================");
			CinfoDto dto = new CinfoDto(cname, jumin, phone, addr, null);
			int n = dao.insert(dto);
			if (n <= 0) {
				System.out.println("▶고객등록 실패! ㅠㅠ");
				System.out.println("=================");
			} else {
				System.out.println("▶" + cname + "님 고객등록 성공!");
				System.out.println("=================");
			}
		}
	}
	
	public static void update() {// 2.고객정보 수정
		System.out.println("=========================");
		System.out.println("[ 2. 고객정보 수정 ]");
		System.out.println("▶수정할 고객번호 입력:");
		int cnum = scan.nextInt();
		System.out.println("▶전화번호 입력 : ");
		String phone = scan.next();
		System.out.println("▶주소 입력 : ");
		String addr = scan.next();
		System.out.println("====================");
		CinfoDto dto = new CinfoDto(cnum, phone, addr);
		int n = dao.update(dto);
		if (n <= 0) {
			System.out.println("▶회원정보 수정실패");
		} else {
			System.out.println("▶" + n + "명의 회원정보 수정완료!");
		}
	}
	
	public static void listAll() {// 3.보유계좌 조회
		System.out.println("=========================");
		System.out.println("[ 3. 보유계좌 조회 ]");
		System.out.println("▶고객번호 입력 : ");
		int num = scan.nextInt();
		ArrayList<AnumberDto> list = adao.selectAnumber(num);
		if (list.size() == 0) {
			System.out.println("▶" + num + "번 고객의 보유계좌가 존재하지 않습니다.");
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
	
	public static void anumberInsert() { // 4.계좌개설
		CinfoDto dto = new CinfoDto();
		AnumberDto dto2=new AnumberDto();
		System.out.println("=====================");
		System.out.println("[ 4.계좌개설 ]");
		System.out.println("=====================");
		System.out.println("▶고객번호 입력: ");
		int cnum = scan.nextInt();
		dto.setCnum(cnum);
		System.out.println("▶비밀번호 입력(4자리 숫자): ");
		String pwd = scan.next();
		String restr = pwd.replaceAll("[^0-9]", ""); // 숫자만 입력받ㄴ는거
		if (restr.length() != 4) {
			System.out.println("▶비밀번호는 숫자 4자리로 입력해주세요.");
			//anumberInsert();
		} else {
			dto2.setCnum(cnum);
			dto2.setPwd(pwd);
			int n = adao.anumberInsert(dto2);
			if (n <= 0) {
				System.out.println("▶계좌 개설 실패!");
			} else {
				System.out.println("====================");
				System.out.println("▶통장개설 완료!");
				System.out.println("▶예금주 : " + dao.printCname(dto));
				System.out.println("▶계좌번호 : "+adao.printAnum(dto2));
			}
		}
	}
	public static void inmoy() { // 5.입금
		System.out.println("=========================");
		System.out.println("[ 5. 입금 ]");
		System.out.println("▶고객번호 입력 : ");
		int cnum = scan.nextInt();
		System.out.println("▶계좌번호 입력 : ");
		int anum = scan.nextInt();
		System.out.println("▶비밀번호 입력(숫자4자리) : ");
		String pwd = scan.next();
		AnumberDto dto = new AnumberDto(null, cnum, anum, pwd, 0, null);
		int n = inoutdao.inoutSelect(dto);
		if (n == 1) {
			System.out.println("▶입금할 금액 입력 : ");
			int inmoy = scan.nextInt();
			inoutdao.inoutInsert(cnum, anum, inmoy);// 입금
			int tot = inoutdao.selectTot(cnum, anum); // 현재 통장금액 가져와서 뽑기
			int a = inoutdao.traninfoInsert(anum, cnum, inmoy, tot);// 현재 통장금액 가져와서 거래내역 추가
			if (a == 1) {
				System.out.println("▶거래내역 추가완료!");
			} else {
				System.out.println("▶거래내역 추가 실패");
			}
		} else {
			System.out.println("▶오류 : 입력한 정보가 일치하지 않습니다.");
			inmoy();
		}
	}
	
	public static void outmoy() {// 6.출금
		System.out.println("=========================");
		System.out.println("[ 6. 출금 ]");
		System.out.println("▶고객번호 입력 : ");
		int cnum = scan.nextInt();
		System.out.println("▶출금할 계좌번호 입력 : ");
		int anum = scan.nextInt();
		System.out.println("▶계좌 비밀번호 입력(숫자4자리) : ");
		String pwd = scan.next();
		AnumberDto dto = new AnumberDto(null, cnum, anum, pwd, 0, null);
		int n = inoutdao.inoutSelect(dto); // 계좌정보 있는지 확인
		if (n == 1) {
			System.out.println("▶출금할 금액 입력 : ");
			int outmoy = scan.nextInt();
			int tongjang = inoutdao.selectTot(cnum, anum);// 통장현재잔액구함
			if (outmoy < tongjang) { // 현재잔액보다 출금금액이 작으면 실행
				inoutdao.outUpdate(cnum, anum, outmoy);// 출금실행메소드
				int tot = inoutdao.selectTot(cnum, anum);// 현재 통장잔액 뽑아오는 메소드
				int a = inoutdao.traninfoInsertOut(anum, cnum, outmoy, tot);
				if (a == 1) {
					System.out.println("▶거래내역 추가완료!");
				} else {
					System.out.println("▶거래내역 추가 실패");
				}
			} else {// 출금잔액이 크면 출력할 문자
				System.out.println("▶출금실패 : 출금 금액 초과입니다.");
			}
		} else {
			System.out.println("▶오류 : 입력한 정보가 일치하지 않습니다.");
		}
	}
	
	public static void sendmoney() { // 7.계좌이체
		System.out.println("=========================");
		System.out.println("[ 7. 계좌이체 ]");
		System.out.println("▶고객번호 입력 : ");
		int cnum = scan.nextInt();
		System.out.println("▶계좌번호 입력 : ");
		int anum = scan.nextInt();
		InoutDto dto = new InoutDto(cnum, anum, 0);
		int tot = inoutdao.sendmoy(dto);
		System.out.println("▶통장 잔액(이체 가능금액) : " + tot + "원");
		System.out.println("▶이체할 금액 입력 : ");
		int send = scan.nextInt();
		if (send <= tot) {
			InoutDto dto2 = new InoutDto();
			System.out.println("▶상대방 계좌번호 입력 : ");
			int sendAnum = scan.nextInt();
			dto2.setAnum(sendAnum);
			System.out.println("▶예금주 : " + adao.viewName(dto2));
			System.out.println("▶이체할 정보가 맞습니까?");
			System.out.println("▶맞으면 1번, 틀리면 2번을 눌러주세요.");
			int bt = scan.nextInt();
			if (bt == 1) {
				System.out.println("▶비밀번호 입력 : ");
				String pwd = scan.next();
				AnumberDto dto3 = new AnumberDto(null, cnum, anum, pwd, 0, null);
				int n = inoutdao.inoutSelect(dto3);
				if (n == 1) {
					InoutDto dto4 = new InoutDto(cnum, anum, 40, 0, send, null);
					inoutdao.send(dto4);// 보내기-->
					// 보내기 거래내역 추가-->
					int tot2 = inoutdao.selectTot(cnum, anum);// 통장현재잔액
					int innum = inoutdao.innumSel();// 현재시퀀스번호
					TraninfoDto dto5 = new TraninfoDto(innum, 40, anum, cnum, 0, send, tot2, null);
					int i = traninfodao.traninfoSend(dto5);
					int acnum = adao.viewCnum(dto2);// 받는사람 계좌번호넣어서 고객번호 가져오기
					InoutDto dto6 = new InoutDto(acnum, sendAnum, 10, send, 0, null);
					inoutdao.receive(dto6); // 이체받기-->ok
					// 이체받기 거래내역 추가-->
					int totR = inoutdao.selectTot(acnum, sendAnum);// 통장현재잔액
					int innumR = inoutdao.innumSel();// 현재시퀀스번호
					TraninfoDto dto7 = new TraninfoDto(innumR, 10, sendAnum, acnum, send, 0, totR, null);
					int j = traninfodao.traninfoReceive(dto7);
					System.out.println("▶계좌이체 처리가 완료 되었습니다!");
				} else {
					System.out.println("▶실패 : 비밀번호가 틀렸습니다.");
				}
			} else if (bt == 2) {
				System.out.println("▶이체작업이 취소되었습니다.");
			}
		} else {
			System.out.println("▶오류 : 이체할 금액이 통장 잔액보다 많습니다.");
		}
	}
	
	public static void search() {// 8.거래내역조회
		System.out.println("=========================");
		System.out.println("[ 8. 거래내역 조회 ]");
		System.out.println("▶원하는 버튼을 입력해주세요.");
		System.out.println("1. 전체 거래내역 조회\t2.계좌별 거래내역 조회");
		int bt = scan.nextInt();
		if (bt == 1) {
			System.out.println("▶전체 고객 거래내역");
			ArrayList<TraninfoDto> list = traninfodao.allSearch();
			if (list.size() == 0) {
				System.out.println("▶거래내역이 없습니다.");
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
			System.out.println("▶조회할 계좌번호를 입력 : ");
			int anum = scan.nextInt();
			System.out.println("▶계좌번호 : " + anum);
			dto.setAnum(anum);
			System.out.println("▶예금주 : " + adao.viewName(dto));
			ArrayList<TraninfoDto> list = traninfodao.anumSearch(dto);
			if (list.size() == 0) {
				System.out.println("▶거래내역이 없습니다.");
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
		System.out.println("[ 9. 전체고객정보 조회 ]");
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
