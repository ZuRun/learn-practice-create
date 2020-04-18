package cn.zull.lpc.practice.spring.cache.controller;

import cn.zull.lpc.common.basis.model.Result;
import cn.zull.lpc.practice.spring.cache.dto.CacheRespDTO;
import cn.zull.lpc.practice.spring.cache.service.CacheTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zurun
 * @date 2020/3/25 14:26:49
 */
@Slf4j
@RequestMapping("cache/test")
@RestController
public class CacheTestController {
    final CacheTestService cacheTestService;

    public CacheTestController(CacheTestService cacheTestService) {
        this.cacheTestService = cacheTestService;
    }

    @RequestMapping("getByAgeAndName")
    public Result getByAgeAndName(@RequestParam Integer age, @RequestParam String name) {
        CacheRespDTO cacheRespDTO = cacheTestService.get(age, name);
        return Result.ok().addData(cacheRespDTO);
    }
}
