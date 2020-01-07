package com.jwj.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jwj.dao.BoardDao;
import com.jwj.dto.BfileDto;
import com.jwj.dto.BoardDto;
import com.jwj.util.Paging;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Service //@Componet의 일종인데, Service bean임을 나타내주는 annotation이다.
@Slf4j //로그를 찍는데 사용하는 annotation
//경로 : src/main/java/com.jwj.Service/BoardService.java
public class BoardService {
	
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
		BoardDto board = bDao.getContents(bnum);
		
		//파일 목록불러오는 처리 ...
		//해당 번호의 파일을 불러오기.
		List<BfileDto> bfList = bDao.getBfList(bnum);
		
		//댓글 불러오는 처리...
		mav.addObject("board", board);
		mav.addObject("bfList", bfList);
		mav.setViewName("boardContents");
		
		return mav;
	}
	
	
	//게시글 등록 서비스 메소드
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
	public boolean fileup(MultipartHttpServletRequest mulit, int bnum) {
		
			// 저장 공간의 물리적 경로 구하기
			//1) 절대 경로 구하기
			String path = mulit.getSession().getServletContext().getRealPath("/");
				// /인 절대 경로를 구하자.
			path += "resources/upload/";//upload폴더에 저장하자.
			log.info(path);
			
			File dir = new File(path);//path 경로에 있는 파일에 관한 객체
			if(dir.isDirectory() == false) {//경로(저장할 upload폴더가 없으면 만들어주자.
				dir.mkdir();//directory를 만들자(upload폴더 생성), 위의 path에 경로를 저장했기때문에 없으면 upload를 만든다
								 //servlet-context에서 resources경로를 가지고 있는 애들은 다 resources로 보내주는 태그가 있다.
			}
			//2)multi에서 파일 꺼내오기
			//   파일 업로드 2개 이상에 대한 처리
			Iterator<String> files = mulit.getFileNames();
			//multi객체 안에 있는 파일의 이름을 꺼내와서 Iterator에 저장
			//파일은 순서대로 이름만 배열로써 Iterator에 저장된다
		
			//실제 파일명과 저장 파일명을 함께 관리.(MAP을 이용하자) - Mybatis에서 매개변수를 1개 밖에 못 받기 때문에 map을 이용
			Map<String, String> fmap = new HashMap<String, String>();
			//Mybatis에서 map을 인터페이스로 받기 때문에 이렇게 선언하자.
			//앞의 String이 key이며 뒤의 String이 value이다. (key는 중복되면 안됨)
			//key 값으로 value를 찾을 것임.
			
			
			//저장할 때 3가지가 들어간다.
			//DB의 BOARDFILE 테이블에는 BF_NUM(순서 번호), BF_BNUM(파일이 속한 게시글 번호),
			//BF_ORINAME(파일의 원래 이름), BF_SYSNAME(파일의 시스템 내 이름) 이렇게 4가지가 있다.
			//BF_NUM(순서 번호)는 sequence로 증가 시킬 것이므로 안 넣어도 된다.
			
			
			fmap.put("bnum", String.valueOf(bnum));
			//첫번째로 파일이 속한 게시글의 번호를 저장
			//map이 문자만 저장하도록 만들었기 때문에 게시글 번호를 문자열로 변환하여 저장
			
			boolean fResult = false;//각각의 분기에 따른 return값을 주기 위해 선언한 변수
			
			while (files.hasNext()) {//다음에 파일이 있다면.. iterator의 속성
				String fileName = files.next();//파일 이름만 가져옴
				log.info(fileName);
				MultipartFile mf = mulit.getFile(fileName);//첫번 째 파일 이름으로 그 파일을 가져옴
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
			}
		
			return fResult;
		
			//파일이 들어갔는지 직접 확인.
			//workSpace > .metadata > .plugins > org.eclipse.wst.server.core > temp0 > wtpwebapps > 내 프로젝트 명 > resources > 생성된 파일
			//DB에서는 데이터 부분 화면에서 새로고침해야 한다.
			
	}
}//class의 끝











