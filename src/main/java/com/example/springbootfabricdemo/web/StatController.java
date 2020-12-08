package com.example.springbootfabricdemo.web;

import com.example.springbootfabricdemo.dto.fabric.req.FinLocQuery;
import com.example.springbootfabricdemo.dto.fabric.req.FinQuery;
import com.example.springbootfabricdemo.dto.fabric.req.WalletQuery;
import com.example.springbootfabricdemo.dto.fabric.resp.FinInfo;
import com.example.springbootfabricdemo.dto.fabric.resp.FinLocInfo;
import com.example.springbootfabricdemo.dto.resp.Response;
import com.example.springbootfabricdemo.entity.User;
import com.example.springbootfabricdemo.service.StatService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stat")
@AllArgsConstructor
public class StatController {

    StatService statService;

    /**
     * 获取指定时间范围内用户的交易统计信息
     * @param finQuery
     * @return
     */
    @PostMapping("/fin-info")
    public Response<List<FinInfo>> getFinInfo(@RequestBody FinQuery finQuery) throws Exception {
        List<FinInfo> finInfoList = statService.getFinInfo(finQuery);
        return Response.newSuccInstance(finInfoList);
    }

    /**
     * 获取指定时间范围内用户的资金流向统计信息
     * @param finLocQuery
     * @return
     */
    @PostMapping("/fin-loc-info")
    public Response<List<FinLocInfo>> getFinInfo(@RequestBody FinLocQuery finLocQuery) throws Exception {
        List<FinLocInfo> finLocInfoList = statService.getFinLocInfo(finLocQuery);
        return Response.newSuccInstance(finLocInfoList);
    }

}
