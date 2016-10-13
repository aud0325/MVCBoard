package com.song.board.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.song.board.dao.BDao;
import com.song.board.dto.BDto;

public class BReplyCommand implements BCommand {

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String bId= request.getParameter("bId");
		String bName= request.getParameter("bName");
		String bTitle= request.getParameter("bTitle");
		String bContent= request.getParameter("bContent");
		String bGroup= request.getParameter("bTitle");
		String bStep= request.getParameter("bContent");
		String bIndent= request.getParameter("bIndent");
		
		
		BDao dao = new BDao();
		BDto dto = dao.reply(bId, bName, bTitle, bContent,bGroup ,bStep ,bIndent );

	}

}
