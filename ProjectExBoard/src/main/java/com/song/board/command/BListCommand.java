package com.song.board.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.song.board.dao.BDao;
import com.song.board.dto.BDto;

public class BListCommand implements BCommand{

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		//jsp페이지에 dto객체를 뿌려주는 역할 해야함.
		BDao dao = new BDao();
		ArrayList<BDto> dtos = dao.list();
		
		model.addAttribute("list", dtos);
	}

}
