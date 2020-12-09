package com.example.springbootfabricdemo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.springbootfabricdemo.config.FabricConfig;
import com.example.springbootfabricdemo.dto.fabric.req.FinLocQuery;
import com.example.springbootfabricdemo.dto.fabric.req.FinQuery;
import com.example.springbootfabricdemo.dto.fabric.resp.FinInfo;
import com.example.springbootfabricdemo.dto.fabric.resp.FinLocInfo;
import com.example.springbootfabricdemo.dto.resp.Response;
import com.example.springbootfabricdemo.entity.User;
import com.example.springbootfabricdemo.fabric.FabricComponent;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Service
public class StatService {

    @Autowired
    FabricComponent fabricComponent;

    @Autowired
    FabricConfig fabricConfig;

    public List<FinInfo> getFinInfo(FinQuery finQuery) throws Exception {
        String resultStr = fabricComponent.invokeQuery(fabricConfig.getUserId(),
                "inAndOut",finQuery.getUserId(),finQuery.getStartTime(),finQuery.getEndTime());
        Map<String, JSONArray> resultMap = JSON.parseObject(resultStr,Map.class);
        JSONArray records = resultMap.get("records");
        List<FinInfo> finInfoList = records.toJavaList(FinInfo.class);
        return finInfoList;
    }

    public List<FinLocInfo> getFinLocInfo(String userId) throws Exception {
        String resultStr = fabricComponent.invokeQuery(fabricConfig.getUserId(),"flow",userId);
        Map<String, JSONArray> resultMap = JSON.parseObject(resultStr,Map.class);
        JSONArray records = resultMap.get("flows");
        List<FinLocInfo> FinLocInfoList = records.toJavaList(FinLocInfo.class);
        return FinLocInfoList;
    }

}
