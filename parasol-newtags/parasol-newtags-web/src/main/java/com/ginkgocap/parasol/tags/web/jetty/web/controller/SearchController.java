package com.ginkgocap.parasol.tags.web.jetty.web.controller;

import com.ginkgocap.parasol.tags.service.NewTagSourceService;
import com.ginkgocap.parasol.tags.service.SearchService;
import com.ginkgocap.parasol.tags.service.TagService;
import com.ginkgocap.parasol.tags.service.TagSourceService;
import com.ginkgocap.ywxt.user.model.User;
import com.gintong.frame.util.dto.CommonResultCode;
import com.gintong.frame.util.dto.InterfaceResult;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class SearchController extends BaseControl{

    private static Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private TagService tagService;

    @Autowired
    private NewTagSourceService newTagSourceService ;

    @Autowired
    private SearchService searchService ;


    /**
     * 搜索展示（全局） /搜索资源（局部搜索)
     * @param request
     * @param type
     * @param sourceType
     * @param keyWord
     * @param index
     * @param size
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/tags/tags/searchTags/{type}/{sourceType}/{keyWord}/{index}/{size}",method = RequestMethod.POST)
    @ResponseBody
    public InterfaceResult searchTags(HttpServletRequest request, @PathVariable int type,int sourceType,String keyWord,int index,int size) throws Exception {
        User user=getUser(request);
        if(user == null){
            return InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION,"用户不能为空");
        }
        //type:0,全局 1.局部 .sourceType:0,全部,1.人脉,2.客户,3.知识.4需求
        if(type == 0){
           List list= searchService.searchTagsAndSource(user.getId(),keyWord,index*size,size);
           return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS,list);
        }
        if(type == 1){
            List list= newTagSourceService.searchTagSources(user.getId(),keyWord,sourceType,index*size,size);
            return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SUCCESS,list);
        }
        return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
    }

}
