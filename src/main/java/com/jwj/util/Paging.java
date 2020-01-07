package com.jwj.util;

import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Component
public class Paging {
		private int maxNum; //전체 글 갯수
		private int pageNum; //현재 페이지 번호
		private int listCount; //페이지 당 글 갯수
		private int pageCount; //화면에 몇 개의 페이지를 보여줄 지 페이지 번호 갯수
		private String listName; //목록 페이지의 종류

		public Paging(int maxNum, int pageNum, int listCount, int pageCount, String listName) {
			this.maxNum = maxNum;
			this.pageNum = pageNum;
			this.listCount = listCount;
			this.pageCount = pageCount;
			this.listName = listName;
		}
		
		public String makeHtmlPaging() {
			//전체 페이지 갯수
			//9개 이하일때 tatalpage = 0
			//11개 이상일 때 totalPage = 1
			int totalPage = (maxNum % listCount > 0 )? maxNum/listCount+1 : maxNum/listCount;
			
			//화면상 보여줄 페이지 갯수(여기서는 5개로 잡자)
			int totalGroup = (totalPage%pageCount) > 0 ? totalPage/pageCount +1 : totalPage/pageCount ;
			
			//현재 페이지가 속해 있는 그룹 번호
			int currentGroup = (pageNum % pageCount) >0 ? pageNum/pageCount +1 : pageNum/pageCount;
			return makeHtml(totalPage,currentGroup);
		}

		private String makeHtml(int totalPage, int currentGroup) {
			//전체 페이지와 갯수와 현재 페이지가 속해있는 그룹번호
			//10개씩 1그룹 등 이 된다.																						
			StringBuffer sb = new StringBuffer();//HTML문구를 버퍼에 추가하여 문자열 그대로 jsp로 반환
			
			//현재 그룹의 시작 페이지 번호
			int start = (currentGroup * pageCount)- (pageCount-1);
							//현재 페이지가 속해있는 그룹 번호 * 한 화면에 몇개 페이지를 보여줄거니 - pageCount - 1
							//1 * 2 - (2-1) = 1  (한 화면에 2개씩 보여줄건데, 2페이지 보고 있으면 1페이지가 첫 페이지로 보여줄것임)
							//2 * 2 - (2-1) = 3
			//현재 그룹의 끝 페이지 번호
			int end = (currentGroup * pageCount >= totalPage)? totalPage : currentGroup *pageCount;
							//우리 글 갯수는 44개이고 10개씩 한 페이지에 들어가므로, totalPage는 5개.
							//currentGroup * pageCount >= totalPage 의 의미는
							//지금 보고 있는 페이지가 속한 그룹이, 꽉 차있니? -> 즉 끝 페이지 번호가 속해 있는 그룹이니?
							//맞다면, 끝 페이지 번호를 맨 끝 번호로 하고, 아니라면 뒤의 식을 넣어라.
			if(start != 1) {//그룹번호가 1이 아니다 -> 2페이지보다 뒷 쪽이다.
				sb.append("<a href='" + listName + "?pageNum=" +(start-1) + "'>");
				sb.append("[이전]");		
				sb.append("</a>");
				}
			//<a href = list?pageNum=5>[이전]</a>  ---- 6번 페이지에 있을 때, 이전을 눌러 5번페이지로 넘어가도록 만들기.
				for(int i = start; i<=end;i++) {
					if(pageNum != i) {//현재 보고 있는 페이지가 아니라면 페이지 번호에 링크걸자
						sb.append("<a href='" + listName + "?pageNum=" + i + "'>");
						sb.append("[" + i +"]</a>");
					}
					else {
						sb.append("<font style='color:red;'>");
						//이러한 HTML 코드에 class 혹은 id를 부여하여, css나 javascript를 적용할 수 있다.
						//그리고 띄어쓰기 조심하자.
						sb.append("[" + i +"]</font>");
					}
			//<a href = list?pageNum=6>[6]</a>  ---- 6번 페이지에 있을 때, 6번 표시
            //	<font style = 'color:red'>[7]</font>				
			//<a href = list?pageNum=8>[8]</a>  ---- 8번 페이지에 있을 때, 8번 표시
				}
				
				if(end != totalPage) {
					sb.append("<a href='" + listName + "?pageNum=" +(end+1) + "'>");
					sb.append("[다음]");			
					sb.append("</a>");
				}
				//<a href = list?pageNum=11>[다음]</a>
			return sb.toString();//버퍼에 문자열을 담아, 그 문자열을 반환함.
			
		}
}