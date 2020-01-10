package com.jwj.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Target;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jwj.dao.BoardDao;
import com.jwj.dto.BfileDto;
import com.jwj.dto.BoardDto;
import com.jwj.dto.MemberDto;
import com.jwj.dto.ReplyDto;
import com.jwj.util.Paging;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Service //@Componet의 일종인데, Service bean임을 나타내주는 annotation이다.
@Slf4j //로그를 찍는데 사용하는 annotation
//경로 : src/main/java/com.jwj.Service/BoardService.java
public class BoardService {

	@Setter(onMethod_ = @Autowired)
	private HttpSession session;

	@Setter(onMethod_ = @Autowired)
	private BoardDao bDao;//게시판 정보가 담긴 List를 가져오기 위함.

	//ModelAndView 객체 (데이터를 view에 넘겨줌)
	//데이터는 Model에, 화면은 View에.
	//Model에 데이터를 집어넣고 View에 JSP파일을 지정해주고 return을 하면 그 JSP파일로 넘어간다.

	ModelAndView mav;//스프링에서 제공하는 객체

	public ModelAndView getBoardList(Integer pageNum) {
		//int가 아닌 이유는, Integer 객체로써 다루려 하기 때문
		log.info("getBoardList() - pageNum : " + pageNum);//@Slf4j를 사용하여 실행이 잘 되는지 확인하기 위한 로그
		mav = new ModelAndView();

		int num = (pageNum == null)? 1 : pageNum;//맨 처음에는 넘어오는 페이지 넘버가 없기 때문에 1페이지부터 시작함

		List<BoardDto> bList = bDao.getList(num);//페이지 번호를 가져오고, 그 번호에 해당하는 List를 가져온다.
		mav.addObject("bList", bList);//bList라는 이름으로 bList 데이터를 넣겠다.
		//------추가분-----------------------------------------------------------------------------------------
		mav.addObject("paging",getPaging(num));//여기서 num은 페이지 번호
		//-----------------------------------------------------------------------------------------------------
		mav.setViewName("boardList");//mav를 보낼 jsp파일의 이름
		session.setAttribute("pageNum", num);
		return mav;//데이터를 담은 mav가 controller쪽으로 return 된다.
	}


	//------추가분-----------------------------------------------------------------------------------------
	private Object getPaging(int num) {
		//전체 글 개수 구하기(from DB)
		int maxNum = bDao.getBoardCount();
		int listCount = 10;//페이지 당 글 갯수
		int pageCount = 2; //한 그룹당 페이지 갯수
		String listName = "list";//BoardController의 RequestMapping 과 똑같아야 함.
		Paging paging = new Paging(maxNum, num, listCount, pageCount, listName);
		String pagingHtml = paging.makeHtmlPaging();		

		return pagingHtml;
	}
	//-----------------------------------------------------------------------------------------------------



	public ModelAndView getBoardContents(Integer bnum) {
		mav = new ModelAndView();//지역 변수
		bDao.upView(bnum);
		BoardDto board = bDao.getContents(bnum);

		//파일 목록불러오는 처리 ...
		//해당 번호의 파일을 불러오기.
		List<BfileDto> bfList = bDao.getBfList(bnum);
		List<ReplyDto> rList = bDao.getReplyList(bnum);
		
		BoardDto getBoardContents = bDao.getContents(bnum);
		MemberDto sessionMember = (MemberDto) session.getAttribute("mb");
		String getSessionId = sessionMember.getM_id();
		String getId = getBoardContents.getBid();
		if(getId.equals(getSessionId)) {
			//아이디가 같으면
			mav.addObject("deleteCheck",1);
		}
		else {
			//아이디가 다르면
			mav.addObject("deleteCheck",0);
		}
		mav.addObject("board", board);
		mav.addObject("bfList", bfList);
		mav.addObject("rList",rList);
		mav.setViewName("boardContents");

		return mav;
	}


	//게시글 등록 서비스 메소드
	@Transactional
	public ModelAndView boardInsert(MultipartHttpServletRequest multi, RedirectAttributes rttr) {
		//HttpServletRequest는 데이터를 전달하는 녀석
		//거기에 Multipart가 붙어, 여러가지 타입의 데이터를 같이 담을 수 있다.
		//RedirectAttributes rttr를 사용하여, JSP에 메세지 보내기.
		mav = new ModelAndView();

		String title = multi.getParameter("btitle");
		String contents = multi.getParameter("bcontents");
		int check = Integer.parseInt(multi.getParameter("fileCheck"));
		String id = multi.getParameter("bid");
		log.info(title + ","+contents +","+ check +","+ id);

		BoardDto board = new BoardDto();
		board.setBtitle(title);
		board.setBid(id);
		board.setBcontents(contents);

		//수정부
		String view = null;
		try {
			bDao.boardInsert(board);
			view = "redirect:list";//redirect로, list에 가라
			rttr.addFlashAttribute("check",2);
		} catch (Exception e) {
			view = "redirect:writeFrm";//redirect로, writeFrm에 가라
			rttr.addFlashAttribute("check",1);
		}

		//----------------------------- 2020.01.06 파일처리 -----------------------------

		if(check == 1) {//파일이 들어왔을 때...
			//파일 처리 메소드 호출
			fileup(multi,board.getBnum());
		}



		//----------------------------------------------------------------------------
		mav.setViewName(view);
		//mav에 데이터를 담지 않아도 되나..?? - Insert를 하니까 상관 없음
		return mav;
	}


	//파일 처리 메소드
	public boolean fileup(MultipartHttpServletRequest multi, int bnum) {

		// 저장 공간의 물리적 경로 구하기
		//1) 절대 경로 구하기
		String path = multi.getSession().getServletContext().getRealPath("/");
		// /인 절대 경로를 구하자.
		path += "resources/upload/";//upload폴더에 저장하자.
		log.info(path);

		File dir = new File(path);//path 경로에 있는 파일에 관한 객체
		if(dir.isDirectory() == false) {//경로(저장할 upload폴더가 없으면 만들어주자.
			dir.mkdir();//directory를 만들자(upload폴더 생성), 위의 path에 경로를 저장했기때문에 없으면 upload를 만든다
			//servlet-context에서 resources경로를 가지고 있는 애들은 다 resources로 보내주는 태그가 있다.
		}

		//실제 파일명과 저장 파일명을 함께 관리.(MAP을 이용하자) - Mybatis에서 매개변수를 1개 밖에 못 받기 때문에 map을 이용
		Map<String, String> fmap = new HashMap<String, String>();
		//Mybatis에서 map을 인터페이스로 받기 때문에 이렇게 선언하자.
		//앞의 String이 key이며 뒤의 String이 value이다. (key는 중복되면 안됨)
		//key 값으로 value를 찾을 것임.
		boolean fResult = false;//각각의 분기에 따른 return값을 주기 위해 선언한 변수


		//저장할 때 3가지가 들어간다.
		//DB의 BOARDFILE 테이블에는 BF_NUM(순서 번호), BF_BNUM(파일이 속한 게시글 번호),
		//BF_ORINAME(파일의 원래 이름), BF_SYSNAME(파일의 시스템 내 이름) 이렇게 4가지가 있다.
		//BF_NUM(순서 번호)는 sequence로 증가 시킬 것이므로 안 넣어도 된다.


		fmap.put("bnum", String.valueOf(bnum));
		//첫번째로 파일이 속한 게시글의 번호를 저장
		//map이 문자만 저장하도록 만들었기 때문에 게시글 번호를 문자열로 변환하여 저장

		//   파일 업로드 2개 이상에 대한 처리
		Iterator<String> files = multi.getFileNames();
		//multi객체 안에 있는 파일의 이름을 꺼내와서 Iterator에 저장
		//파일은 순서대로 이름만 배열로써 Iterator에 저장된다
		//form에서 input file 태그 자체가 Iterator 한 칸에 들어간다.
		//즉 파일을 업로드하는 input태그 1개 당, Iterator 한 칸이다.


		/*while (files.hasNext()) {//다음에 파일이 있다면.. iterator의 속성
				String fileName = files.next();//파일 이름만 가져옴
				log.info(fileName);
				MultipartFile mf = multi.getFile(fileName);//첫번 째 파일 이름으로 그 파일을 가져옴
				String oriName = mf.getOriginalFilename();//그 파일의 실제 이름을 가져옴
				fmap.put("oriFileName", oriName);//map에 실제 파일명을 저장(확장자는 이미 있으므로 따로 확장자 처리는 안 해도 된다)
				String sysName = System.currentTimeMillis() + "." + oriName.substring(oriName.lastIndexOf(".")+1);
				//저장 파일명 생성은 등록 된 시간으로 겹치지 않게 처리, oriName.lastIndexOf(".")+1는 원래 확장자 +1은 .을 빼줌
				//oriName.lastIndexOf(".")가 .의 위치를 나타냄.
				//substring은 해당 번호부터 뒤의 문자열을 잘라 가져오겠다.
				fmap.put("sysFileName", sysName);

				//사용자는 originalName을 보고, 파일을 찾아 올 때는 originalName로 sysfileName을 찾아서 그 파일에 접근. 

				//여기까지가 DB에 저장할 3가지 정보를 처리함.
				//이제부터 3가지 정보를 저장한 fmap을 처리할 Mybatis의 Interface 메소드를 만들자.

				try {
				//1. 물리적 디스크에 파일 저장. 
					//DB에 먼저 업로드하고 물리적 디스크에 파일이 없으면 안되므로 먼저 물리적 디스크에 저장부터 한다
					mf.transferTo(new File(path+sysName));
					//new File(path+sysName) 로 새로운 경로의 파일을 만든다
					//기존의 mf를 transferTo 메소드를 이용하여 새로 만든 파일에 넣는다.

					//mf(꺼내온 파일에서) 새로운 이름을 붙여 저장
					//transferTo가 그 파일 안의 바이너리 내용을 전송시킴 
					//mf라는 파일을 File(path+sysName) 에 저장한다.
					//path는 upload파일까지 경로이고, sysName이름
				//2. DB에 파일 정보 저장.
					fResult = bDao.fileInsert(fmap);
				}catch (IOException e) {
					fResult = false;
				}
			}*/

		List<MultipartFile> fList = multi.getFiles("files");
		//파일의 이름들 만이 아닌, 파일들 자체를 저장하는 List를 선언

		for(int i = 0; i < fList.size(); i++) {
			//파일 메모리에 저장
			MultipartFile mf = fList.get(i);
			String oriName = mf.getOriginalFilename();
			fmap.put("oriFileName", oriName);
			String sysName = System.currentTimeMillis()	+ "."	+ oriName.substring(oriName.lastIndexOf(".")+1);
			fmap.put("sysFileName", sysName);

			try {
				mf.transferTo(new File(path+sysName));
				fResult = bDao.fileInsert(fmap);
			}catch (IOException e) {
				e.printStackTrace();
				fResult = false;
			}
		}
		return fResult;

		//파일이 들어갔는지 직접 확인.
		//workSpace > .metadata > .plugins > org.eclipse.wst.server.core > temp0 > wtpwebapps > 내 프로젝트 명 > resources > 생성된 파일
		//DB에서는 데이터 부분 화면에서 새로고침해야 한다.




	}

	//파일 다운로드 서비스
	public void fileDown(String sysFileName, HttpServletRequest req, HttpServletResponse resp) {
		//파일을 열어, 그 안의 바이너리 데이터를 다 가져와서 디스크에 새로운 파일을 만들어 그 곳에 담는 작업.
		// 다운로드도, 서버쪽의 파일을 다 열어 바이너리 데이터를 가져와서 사용자 쪽에 파일을 만들어 그 곳에 저장

		//서버의 파일 위치를 얻자.
		String path = req.getSession().getServletContext().getRealPath("/") + "resources/upload/";
		log.warn(path);

		String oriName = bDao.getOriName(sysFileName);
		path += sysFileName;
		log.warn(path);
		log.warn(oriName);
		InputStream is = null;// 서버 컴퓨터 안에 저장된 파일을 읽어오는 것
		OutputStream os = null;// 파일을 사용자 컴퓨터로 전송하기 위한 것

		try {
			// 파일명의 한글 깨짐 방지
			String dFileName = URLEncoder.encode(oriName, "UTF-8");
			log.warn(dFileName);
			// 파일 객체 생성
			File file = new File(path);
			is = new FileInputStream(file);

			//응답 객체 (resp)의 헤더 설정
			//파일 전송용 contentType 설정
			resp.setContentType("application/octet-stream");
			resp.setHeader("content-Disposition", "attachment; filename=\"" + dFileName +"\"");
			//attachment; filename=\파일명.txt"\가 됨

			//응답 객체(resp)를 통해서 파일 전송
			os = resp.getOutputStream();

			//전송하기
			byte[] buffer = new byte[1024];//파일의 데이터를 buffer에 넣음
			int length;
			while((length = is.read(buffer)) != -1) {//read를 했는데 데이터가 없으면 -1이 됨
				os.write(buffer,0,length);//버퍼 시작부터, 길이만큼 outputStream으로 읽어옴
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				os.flush();//OutStream에 데이터가 남아있으면 그 데이터를 다 보냄
				os.close();
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	//수정을 할 때 해당 session의 아이디와 게시글의 id가 같으면 수정 진행
	public ModelAndView getBCforUpdate(int bnum,RedirectAttributes rttr) {
		String view = null;
		mav = new ModelAndView();
		BoardDto getBoardContents = bDao.getContents(bnum);
		MemberDto sessionMember = (MemberDto) session.getAttribute("mb");
		String getSessionId = sessionMember.getM_id();
		String getId = getBoardContents.getBid();
		if(getId.equals(getSessionId)) {
			List<BfileDto> bfList = bDao.getBfList(bnum);

			mav.addObject("bfList", bfList);
			mav.addObject("board",getBoardContents);
			view = "updateFrm";
		}
		else {
			view = "redirect:list?pageNum="+session.getAttribute("pageNum");
			//리스트로 넘어가고 싶은데, 페이지 번호가 없어서 내용 못 불러옴
			rttr.addFlashAttribute("check","타인의 글은 수정할 수 없습니다.");
		}
		mav.setViewName(view);

		return mav;
	}


	public String updateBoard(MultipartHttpServletRequest multi, BoardDto board, RedirectAttributes rttr) {
		//받아온 데이터를 DB에 넣고 다시 controller로 쏴주자
		String view = null;
		boolean s = bDao.updateBoard(board);//board 컨텐츠 가져오는게 안됨
		fileup(multi, board.getBnum());
		BoardDto updatedBoard = bDao.getContents(board.getBnum());

		if(s) {
			view = "redirect:contents?bnum=" + updatedBoard.getBnum();
			rttr.addFlashAttribute("check","수정 완료!");
		}
		else {
			view ="redirect:list";
			rttr.addFlashAttribute("check","수정 실패..");
			//그냥 view에 String을 담아 보낼 때는, controller의 URI를 적어주고,
			//view객체에 담아서 보낼 때는 보내고자 하는 jsp파일 이름을 써 주자.
		}
		return view;
	}



	public Map<String,List<ReplyDto>> replyInsert(ReplyDto reply){
		//댓글들을 하나 받아서, map에 넣어서 DB에 넣자
		//그리고 새로운 댓글이 추가된 댓글 List를 map으로써 반환!

		//json object를 변환하는 jackson이 있는데 걔는 map을 주면 json형태로 바꿔준다.
		//자바객체를 json으로 변환하는 방법이 여러가지 있는데 jackson을 쓰기 위해 map으로 반환을 한다.
		//1. form에서 받은 데이터를 json형태로 바꿈.
		//2. 그게 컨트롤러로 갈 때, 자바객체로 바꿔주는 애가 지슨
		//3. 내부작업 후, 결과물인 Map인 자바객체를 jackson이 다시 json 형태로 바꿔준다

		Map<String,List<ReplyDto>> rmap= null;

		try {
			bDao.replyInsert(reply);
			//댓글 하나를 DB에 넣으면 댓글리스트를 다시 불러오자
			List<ReplyDto> rList = bDao.getReplyList(reply.getR_bnum());
			rmap = new HashMap<String, List<ReplyDto>>();
			rmap.put("rList", rList);
		} catch (Exception e) {
			rmap = null; 
			//먼저 try문이 실행 되므로 map은 생성 되어있고 에러를 발생시켜 
			//ajax의 error 부의 함수를 실행시키기 위함
		}



		return rmap;
	}

	@Transactional
	public String delBoard(int bnum, RedirectAttributes rttr) {
		//자신이 쓴 글인지 아닌지 확인하고 글 삭제 작업 진행
		//session의 아이디와, 글쓴이의 아이디가 같은지 비교
		BoardDto getBoardContents = bDao.getContents(bnum);
		MemberDto sessionMember = (MemberDto) session.getAttribute("mb");
		String getSessionId = sessionMember.getM_id();
		String getId = getBoardContents.getBid();
		if(getId.equals(getSessionId)) {
			//1. 파일 삭제 - 글 번호로 DB에서 파일 찾아서 삭제하자.
			bDao.delBoardFile(bnum);
			//2. 댓글 삭제
			bDao.delBoardReply(bnum);
			//3. 글 삭제
			bDao.delBoardContents(bnum);

			String view = "redirect:list?pageNum="+session.getAttribute("pageNum");//redirect로, writeFrm에 가라
			rttr.addFlashAttribute("check","삭제 완료!");
			return view;
		}
		else {
			String view = "redirect:contents?bnum="+bnum;
			rttr.addFlashAttribute("check","다른 사람의 글은 삭제 할 수 없습니다 ㅎㅎ ");
			return view;
		}


	}



	




}//class의 끝











