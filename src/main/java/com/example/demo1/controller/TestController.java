package com.example.demo1.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo1.entity.T_COFFEE;
import com.example.demo1.entity.T_COFFEE_ORDER;
import com.example.demo1.entity.T_CUSTOMER;
import com.example.demo1.repo.entityrepo.CoffeOrderRepo;
import com.example.demo1.repo.entityrepo.CoffeeRepo;
import com.example.demo1.repo.entityrepo.CustomRepo;
import com.example.demo1.repo.entityrepo.TestUniqueRepo;
import com.example.demo1.service.impl.RequestTestService;
import com.example.demo1.service.impl.UniqueService;
import com.example.demo1.utils.SpringContextHelper;
import com.example.demo1.utils.ThreadPoolHelper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

@RequestMapping("/test")
@RestController
@Slf4j
public class TestController {

    private CoffeeRepo coffeeRepo;

    private CoffeOrderRepo coffeeOrderRepo;

    private CustomRepo customRepo;

    private TestUniqueRepo testUniqueRepo;

    private RequestTestService requestTestService;


    @Autowired
    private UniqueService uniqueService;

    @Autowired
    public void setTestUniqueRepo(TestUniqueRepo testUniqueRepo) {
        this.testUniqueRepo = testUniqueRepo;
    }

    @Autowired
    public void setCustomRepo(CustomRepo customRepo) {
        this.customRepo = customRepo;
    }

    @Autowired
    public void setCoffeeRepo(CoffeeRepo coffeeRepo) {
        this.coffeeRepo = coffeeRepo;
    }

    @Autowired
    public void setCoffeOrderRepo(CoffeOrderRepo coffeeOrderRepo) {
        this.coffeeOrderRepo = coffeeOrderRepo;
    }

    @Autowired
    public void setRequestTestService(RequestTestService requestTestService) {
        this.requestTestService = requestTestService;
    }

    private Gson parser = new Gson();

    @PostMapping(value = "/t1")
    public String test1() {
        String sql = "SELECT tc.id, tc.nameen, tc.namezh, tc.price FROM t_coffee AS tc";
        List<Map<String, Object>> res = coffeeRepo.getListMapObj(sql);
//        List<Map<String, Object>> res = coffeeRepo.getlm2();
        return  parser.toJson(res);
    }

    @PostMapping(value = "/t2")
    public String test2() {
        Object notiationTest = SpringContextHelper.getBeanByName("notiationTest");
        log.info(notiationTest.toString());
        return parser.toJson(notiationTest);
    }

    /*
    *  测试jpa模糊查询
    * */
    @PostMapping(value = "/t3")
    public String test3(@RequestBody Map<String, String> params) {
        String fuzzy = MapUtils.getString(params, "fuzzy", "");
        if (StringUtils.isEmpty(fuzzy)) {
            return "empty fuzzy";
        }
        List<T_COFFEE> res = coffeeRepo.findAll((Specification<T_COFFEE>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate nameen = criteriaBuilder.like(root.get("nameen").as(String.class), fuzzy + "%");
            Predicate price = criteriaBuilder.and(criteriaBuilder.gt(root.get("price"), 36.0));
            predicates.add(nameen);
            predicates.add(price);
            query.distinct(true);
            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        });
        return parser.toJson(res);
    }

    @PostMapping("/t4")
    public String saveCoffeeOrder() {
        String init = "SELECT tc.id, tc.nameen, tc.namezh, tc.price FROM t_coffee AS tc";
        String where = "where tc.id in (1,2,3)";
        StringBuffer sql = new StringBuffer(init);
        List<Map<String, Object>> coffees = coffeeRepo.getListMapObj(sql.toString());


        List<T_CUSTOMER> customers = customRepo.findAll();

        Map<String, T_CUSTOMER> customerMap = new HashMap<>();
        customers.forEach(cus -> {
            customerMap.put(cus.getUsername(), cus);
        });
        Map<String, String> map = new HashMap<>();
        map.put("yangqd", "tangtch");
        map.put("liuyw", "yinsw");
        map.put("xud", "wangsy");
        map.put("jiangyc", "zhangj");
        map.put("caowy", "chenmiss");
        map.put("xuanzy", "yuanmy");

        customers.forEach(custom -> {
            custom.setCouplename(MapUtils.getString(map, custom.getUsername(), ""));
        });

        customRepo.saveAll(customers);
        System.out.println("save customers");

        List<T_COFFEE_ORDER> orderList = new ArrayList<>();

        StringBuffer coffeeids = new StringBuffer();
        StringBuffer coffee_en_names = new StringBuffer();
        StringBuffer coffee_zh_names = new StringBuffer();
        Map<String, Double> sumMap = new HashMap<>();
        sumMap.put("sum", 0.0);
        // 筛选出冰咖啡
        coffees.stream()
                .filter(x -> MapUtils.getString(x, "nameen","").contains("Iced")).forEach(target -> {
            if (coffeeids.length() == 0) {
                coffeeids.append(MapUtils.getString(target, "id"));
            } else {
                coffeeids.append(",").append(MapUtils.getString(target, "id"));
            }

            if (coffee_en_names.length() == 0) {
                coffee_en_names.append(MapUtils.getString(target, "nameen"));
            } else {
                coffee_en_names.append(",").append(MapUtils.getString(target, "nameen"));
            }

            if (coffee_zh_names.length() == 0) {
                coffee_zh_names.append(MapUtils.getString(target, "namezh"));
            } else {
                coffee_zh_names.append(",").append(MapUtils.getString(target, "namezh"));
            }
            double sum = MapUtils.getDoubleValue(sumMap,"sum", 0.0);
            sum += MapUtils.getDoubleValue(target, "price", 0.0);
            sumMap.put("sum", sum);
        });

        T_COFFEE_ORDER order = new T_COFFEE_ORDER();
        order.setCoffeeid(coffeeids.toString());
        order.setCoffeenameen(coffee_en_names.toString());
        order.setCoffeenamezh(coffee_zh_names.toString());
        order.setTotalsum(MapUtils.getDoubleValue(sumMap, "sum", 0.0));
        order.setCreatetime(Calendar.getInstance());
        order.setUpdatetime(Calendar.getInstance());
        order.setCustomname("xuanzy");
        order.setCustomid(customerMap.get("xuanzy").getId());

        T_COFFEE_ORDER res = coffeeOrderRepo.save(order);

        return parser.toJson(res);
    }

    @PostMapping("/t5")
    public String batchInsertOrder() {
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolHelper.getInstance();
//        threadPoolExecutor.submit();
        new Thread();
        return "";
    }

    @PostMapping("/t7")
    public String saveTest(@RequestParam Map<String, String> params) {
        requestTestService.showRequest();
        return "success";
    }

    @GetMapping("/t6")
    public String getRequestTest(@RequestParam String user, @RequestParam String pass) {
        requestTestService.showRequest();
        return "success";
    }

    @PostMapping("/t8")
    public String getRequestTest2(@RequestBody Map<String, String> params) {
        requestTestService.showRequest();
        return "success";
    }


    @PostMapping("/t10")
    public String saveCoffeeOrder1() {
        String init = "SELECT tc.id, tc.nameen, tc.namezh, tc.price FROM t_coffee AS tc";
        String where = "where tc.id in (1,2,3)";
        String where2 = " where tc.nameen in ('Colombian Coffee','Jamaican Coffee')";
        StringBuffer sql = new StringBuffer(init);
        sql.append(where2);
        List<Map<String, Object>> coffees = coffeeRepo.getListMapObj(sql.toString());
        return JSON.toJSONString(coffees);
    }

    @PostMapping("/t11/tPath/{name}")
    public String testPath(@PathVariable String name) {
        return name;
    }

    @PostMapping("/t11/tPath/{date}")
    public String testPathDate(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date date) {
        return new Gson().toJson(date);
    }

    @PostMapping(path = "/t13/tPath")
    public String testHeader(@RequestBody Map<String, Object> map) {
        return new Gson().toJson(map);
    }

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping(path = "/writeRedis")
    public Object writeRedis(@RequestBody Map<String, Object> map) {
        String key = MapUtils.getString(map, "username", "user1");

        redisTemplate.opsForHash().putAll(key, map);
        return redisTemplate.opsForHash().multiGet(key, Arrays.asList("username", "nickname"));
    }

    @GetMapping(path = "/getRedisData")
    public Object getRedisData(String key, String[] fields) {
        return redisTemplate.opsForHash().multiGet(key, Arrays.asList(fields));
    }

    @PostMapping(path = "/rmRedis")
    public Object rmRedis(@RequestParam String key) {
        redisTemplate.delete(key);
        return "remove success";
    }

//    @PostConstruct
//    public void initUser() {
//        List<UserModel> userModels = new ArrayList<UserModel>() {{
//            add(new UserModel("K0321294", "许頔", "xd@163.com", "123456", "开发商", "开发商"));
//            add(new UserModel("K0321240", "浮增力", "fzl@163.com", "123456", "开发商", "开发商"));
//            add(new UserModel("K0321259", "唐震", "tz@163.com", "123456", "开发商", "开发商"));
//            add(new UserModel("K0321297", "庞佳佳", "pjj@163.com", "123456", "开发商", "开发商"));
//            add(new UserModel("K0321276", "李秋菊", "lqj@163.com", "123456", "开发商", "开发商"));
//            add(new UserModel("014895", "徐鹏骏", "xpj@htsc.com.cn", "1111000", "信息应用业务安全团队", "信息安全中心"));
//        }};
//        userModels.forEach(userModel -> {
//            redisTemplate.opsForValue().set(userModel.getUsername(), userModel);
//        });
//    }

    private static class UserModel{
        private String username;
        private String nickname;
        private String email;
        private String phone;
        private String team;
        private String center;

        public UserModel() {
        }

        public UserModel(String username, String nickname, String email, String phone, String team, String center) {
            this.username = username;
            this.nickname = nickname;
            this.email = email;
            this.phone = phone;
            this.team = team;
            this.center = center;
        }

        public String getUsername() {
            return username;
        }

        public String getNickname() {
            return nickname;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public String getTeam() {
            return team;
        }

        public String getCenter() {
            return center;
        }
    }
}
