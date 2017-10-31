package com.ginkgocap.parasol.metadata.web.jetty.web.controller;


import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ginkgocap.parasol.metadata.exception.NewRegionServiceException;
import com.ginkgocap.parasol.metadata.model.CodeRegion;
import com.ginkgocap.parasol.metadata.model.NewRegion;
import com.ginkgocap.parasol.metadata.service.NewRegionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by xutlong on 2017/10/30.
 */
@RestController
public class MetadataNewRegionController extends BaseControl {

    private static final Logger logger = Logger.getLogger(MetadataRegionCtroller.class);
    private static final String parameterFields = "fields";
    private static final String parameterCId ="cid"; //区县ID
    private static final String parameterPid ="id"; //父Id
    private static final String parameterGrade = "grade"; // 接口调用级数

    @Autowired
    private NewRegionService newRegionService;

    @RequestMapping(path = "/metadata/newregion", method = {RequestMethod.GET})
    public MappingJacksonValue getRegion(@RequestParam(name = MetadataNewRegionController.parameterFields, defaultValue = "") String fileds,
                                         @RequestParam(name = MetadataNewRegionController.parameterPid) String id,
                                         @RequestParam(name = MetadataNewRegionController.parameterGrade) int grade) throws NewRegionServiceException {
        MappingJacksonValue mappingJacksonValue = null;
        String cid = null;
        if (grade == 1) {
            cid = "%000000";
        } else if (grade == 2) {
            // 取id得前三位后四位
            String left = id.substring(0,3);
            String right = id.substring(5);
            cid = left + "%%" + right;
        } else if (grade == 3) {
            String left = id.substring(0,5);
            String right = id.substring(7);
            cid = left + "%%" + right;
        } else if (grade == 4) {
            String left = id.substring(0,7);
            cid = left + "%%";
        }
        try {
            List<NewRegion> regionList = newRegionService.getNewRegionListById(cid,id);
            // 2.转成框架数据
            mappingJacksonValue = new MappingJacksonValue(regionList);
            // 3.创建页面显示数据项的过滤器
            SimpleFilterProvider filterProvider = builderSimpleFilterProvider(fileds);
            mappingJacksonValue.setFilters(filterProvider);
            // 4.返回结果
            return mappingJacksonValue;
        } catch (NewRegionServiceException e) {
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * 指定显示那些字段
     *
     * @param fileds
     * @return
     */
    private SimpleFilterProvider builderSimpleFilterProvider(String fileds) {
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        // 请求指定字段
        String[] filedNames = StringUtils.split(fileds, ",");
        Set<String> filter = new HashSet<String>();
        if (filedNames != null && filedNames.length > 0) {
            for (int i = 0; i < filedNames.length; i++) {
                String filedName = filedNames[i];
                if (!StringUtils.isEmpty(filedName)) {
                    filter.add(filedName);
                }
            }
        } else {
            filter.add("id"); // 主键',
            filter.add("name"); // '类型名称',
            filter.add("firstCode");
        }

        filterProvider.addFilter(NewRegion.class.getName(), SimpleBeanPropertyFilter.filterOutAllExcept(filter));
        return filterProvider;
    }

}
