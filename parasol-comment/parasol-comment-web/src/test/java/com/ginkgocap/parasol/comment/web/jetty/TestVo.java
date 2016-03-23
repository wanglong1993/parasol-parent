package com.ginkgocap.parasol.comment.web.jetty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ginkgocap.parasol.comment.model.Comment;
import com.ginkgocap.parasol.comment.web.jetty.web.vo.CommentUserVO;
import com.ginkgocap.parasol.user.model.UserBasic;

import junit.framework.Test;
import junit.framework.TestResult;

public class TestVo implements Test {

	@Override
	public int countTestCases() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void run(TestResult result) {
		// TODO Auto-generated method stub
	}

	@org.junit.Test
	public void testCommentUserVo() throws JsonGenerationException, JsonMappingException, IOException {
		List<CommentUserVO> commentUserVOs = new ArrayList<CommentUserVO>();
		Comment comment = new Comment();
		UserBasic userBasic = new UserBasic();
		UserBasic toUserBasic = new UserBasic();

		CommentUserVO commentUserVO = new CommentUserVO(comment, userBasic,toUserBasic);
		for (int i = 0; i < 15; i++) {
			commentUserVOs.add(commentUserVO);
		}
		SimpleFilterProvider sfp = new SimpleFilterProvider();
		sfp.addFilter(CommentUserVO.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept("user","userId","toUserId"));

		mapper.setFilterProvider(sfp);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		mapper.writeValue(System.out, commentUserVOs);
	}

	private static ObjectMapper mapper = new ObjectMapper();
}
