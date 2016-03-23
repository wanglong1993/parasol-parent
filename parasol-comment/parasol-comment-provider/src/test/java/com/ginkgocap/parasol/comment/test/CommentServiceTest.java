package com.ginkgocap.parasol.comment.test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.json.JSONObject;
import com.alibaba.dubbo.common.serialize.support.json.JsonObjectOutput;
import com.ginkgocap.parasol.comment.exception.CommentServiceException;
import com.ginkgocap.parasol.comment.exception.CommentTypeServiceException;
import com.ginkgocap.parasol.comment.model.Comment;
import com.ginkgocap.parasol.comment.model.CommentType;
import com.ginkgocap.parasol.comment.service.CommentService;
import com.ginkgocap.parasol.comment.service.CommentTypeService;

import junit.framework.Test;
import junit.framework.TestResult;

public class CommentServiceTest extends TestBase implements Test {
	private static long System_AppId = 7647448850l;
	private static Long userId = 111l;

	@Autowired
	private CommentService commentService;

	@Autowired
	private CommentTypeService commentTypeService;

	@Override
	public int countTestCases() {
		return 0;
	}

	@Override
	public void run(TestResult result) {

	}

	@org.junit.Test
	public void testSaveComment() throws CommentTypeServiceException {
		for (int i = 0; i < 100; i++) {
			List<CommentType> commentTypes = commentTypeService.getCommentTypessByAppId(System_AppId);
			Assert.assertTrue(CollectionUtils.isNotEmpty(commentTypes));
			for (CommentType commentType : commentTypes) {
				Comment comment = new Comment();
				comment.setAppId(System_AppId);
				comment.setUserId(userId);

				comment.setSourceId(1l);
				comment.setSourceTypeId(commentType.getId());
				comment.setContent(RandomStringUtils.random(15, "中华人民共和国中央人民政府"));
				try {
					commentService.createComment(comment.getAppId(), comment.getUserId(), comment);
				} catch (CommentServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @throws CommentTypeServiceException
	 * @throws CommentServiceException 
	 * @throws IOException 
	 */
	@org.junit.Test
	public void testGetComment() throws CommentTypeServiceException, CommentServiceException, IOException {
		List<CommentType> commentTypes = commentTypeService.getCommentTypessByAppId(System_AppId);
		Assert.assertTrue(CollectionUtils.isNotEmpty(commentTypes));
		for (CommentType commentType : commentTypes) {
			List<Comment> comments = commentService.getComments(System_AppId, commentType.getId(), 1l,0,10);
			Assert.assertTrue(CollectionUtils.isNotEmpty(comments));
			JsonObjectOutput joo = new JsonObjectOutput(System.out);
			joo.writeObject(comments);
		}
		// commentService.getCommentsBy(System_AppId, sourceType, sourceId)
	}
	
	@org.junit.Test
	public void testRemoveComment() throws CommentTypeServiceException, CommentServiceException, IOException {
		List<CommentType> commentTypes = commentTypeService.getCommentTypessByAppId(System_AppId);
		Assert.assertTrue(CollectionUtils.isNotEmpty(commentTypes));
		for (CommentType commentType : commentTypes) {
			int count = commentService.countComment(System_AppId, commentType.getId(), 1l);

			List<Comment> comments = commentService.getComments(System_AppId, commentType.getId(), 1l,0,count);
			Assert.assertTrue(CollectionUtils.isNotEmpty(comments));
			JsonObjectOutput joo = new JsonObjectOutput(System.out);
			joo.writeObject(comments);
			
			//delete

		}
		
	}
}
