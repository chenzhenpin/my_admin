package my.admin.code.sys.controller;


import my.admin.code.common.utils.ResData;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2019-05-31
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {

    @RequestMapping("/getMenuData")
    public ResData getMenuData() {
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map1= new HashMap<>();
        map1.put("path","/");
        map1.put("redirect", "/dashboard");
        List<Map<String,Object>> children1 = new ArrayList<>();
        Map<String,Object> children1Map= new HashMap<>();
        children1Map.put("path","dashboard");
        children1Map.put("name","Dashboard");
        Map<String,Object> children1MapMate= new HashMap<>();
        children1MapMate.put("title","Dashboard");
        children1MapMate.put("icon","Dashdashboardboard");
        children1Map.put("meta",children1MapMate);
        children1.add(children1Map);
        map1.put("children", children1);
        list.add(map1);

        Map<String,Object> map2= new HashMap<>();
        map2.put("path","/example");
        map2.put("redirect", "/example/table");
        map2.put("name", "Example");
        Map<String,Object> mapMate= new HashMap<>();
        mapMate.put("title","Example");
        mapMate.put("icon","example");
        map2.put("meta", "Example");

        List<Map<String,Object>> children2 = new ArrayList<>();
        Map<String,Object> children2Map= new HashMap<>();
        children2Map.put("path","table");
        children2Map.put("name","Table");
        Map<String,Object> children2MapMate= new HashMap<>();
        children2MapMate.put("title","Table");
        children2MapMate.put("icon","table");
        children2Map.put("meta",children1MapMate);
        children2.add(children2Map);
        map2.put("children", children2);
        list.add(map2);
        return ResData.ok().setData(list);
    }
    @RequestMapping("list")
    public ResData list() {
        List<Map<String,Object>> list= new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("id",1);
        map.put("title","title");
        map.put("author","author");
        map.put("status",1);
        map.put("display_time",new Date(System.currentTimeMillis()));
        map.put("pageviews",500);

        list.add(map);
        return ResData.ok().setData(list).setCode(20000);
    }
}
