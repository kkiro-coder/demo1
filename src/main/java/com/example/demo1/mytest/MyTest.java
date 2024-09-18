package com.example.demo1.mytest;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.example.demo1.entity.UploadData;
import com.example.demo1.jpatest.entity.TestData;
import com.example.demo1.mytest.funci.FuncEx;
import com.example.demo1.mytest.funci.ThrowingFunction;
import com.example.demo1.mytest.ps.Parent;
import com.example.demo1.mytest.ps.SonOne;
import com.example.demo1.mytest.ps.SonTwo;
import com.example.demo1.tenum.TestEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.*;


@Slf4j
public class MyTest {

    @Test
    public void filesExample1() {
        String str = "\\\"";
        String str1 = "\\\\";
        String str2 = "\"";
        System.out.println(str);
        System.out.println(str1);
        System.out.println(str2);
    }

    @Test
    public void testStream() {
        List list = null;
        String[] a = {"a", "b", "c"};
        Optional.ofNullable(list).orElseGet(ArrayList::new).addAll(Arrays.asList(a));
    }

    //Predicate接口是输入一个参数，返回布尔值。我们通过and方法组合两个Predicate条件，判断是否值大于0并且是偶数
    @Test
    public void testPredicate() {
        Predicate<Integer> pre = i -> i > 0;
        Predicate<Integer> post = i -> i % 2 == 0;
        System.out.println(pre.and(post).test(4));
    }

    @Test
    public void testConsumer() {
        Consumer<Integer> consumer = param -> System.out.println(Math.sqrt(param));
        Supplier<String> consumer1 = () -> "ok";
        IntStream.range(1, 9).forEach(consumer::accept);
    }

    //Function接口是输入一个数据，计算后输出一个数据。我们先把字符串转换为大写，然后通过andThen组合另一个Function实现字符串拼接
    @Test
    public void testFunction() {
        Function<String, String> pre = String::toUpperCase;
        Function<String, String> post = s -> s.concat(s);
        Assert.assertTrue(pre.andThen(post).apply("boom").equals("BOOMBOOM"));
    }

    //Supplier是提供一个数据的接口。这里我们实现获取一个随机数
    @Test
    public void testSupplier() {
        Supplier<Integer> random = () -> ThreadLocalRandom.current().nextInt();
        System.out.println(random.get());

    }

    @Test
    public void filesExample() throws IOException {
        //无限深度，递归遍历文件夹
        try (Stream<Path> pathStream = Files.walk(Paths.get("."))) {
            pathStream.filter(Files::isRegularFile) //只查普通文件
                    .filter(FileSystems.getDefault().getPathMatcher("glob:**/*.java")::matches) //搜索java源码文件
                    .flatMap(ThrowingFunction.unchecked(path ->
                            Files.readAllLines(path).stream() //读取文件内容，转换为Stream<List>
                                    .filter(line -> Pattern.compile("public class").matcher(line).find()) //使用正则过滤带有public class的行
                                    .map(line -> path.getFileName() + " >> " + line))) //把这行文件内容转换为文件名+行
                    .forEach(System.out::println); //打印所有的行
        }
    }

    @Test
    public void testTheadJoin1() {
        Thread threadOne = new Thread(() -> {
            try {
                System.out.println("threadOne do something");
                Thread.sleep(13000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread threadTwo = new Thread(() -> {
            try {
                System.out.println("threadTwo do something");
                Thread.sleep(13000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadOne.start();
        threadTwo.start();


        try {

            threadOne.join();
            System.out.println("thread one stop");
            threadTwo.join();
            System.out.println("thread two stop");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("all stop");
    }


    @Test
    public void testThreadJoin2() {
        Thread threadOne = new Thread(() -> {
            System.out.println("threadOne start");
            for (; ; ) {
            }
        });

        Thread mainThread = Thread.currentThread();


        Thread threadTwo = new Thread(() -> {
            try {
                System.out.println("threadTwo start");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mainThread.interrupt();
        });


        threadOne.start();

        threadTwo.start();

        try {
            threadOne.join();
        } catch (InterruptedException e) {
            System.out.println("main thread => " + e);
        }

    }


    @Test
    public void tests() {
        String str = "中国人";
        System.out.println(str.getBytes().length);
    }

    @Test
    public void testRequestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>("");
        ResponseEntity<String> entity = restTemplate.exchange("http://127.0.0.1:8081/test/t1", HttpMethod.POST, request, String.class);
        System.out.println(entity.getBody());
    }

    @Test
    public void testIntStream() {
        IntStream.rangeClosed(0, 5).forEach(System.out::println);
    }

    @Test
    public void testEnum() {
        TestEnum[] values = TestEnum.values();
        Arrays.stream(values).forEach(System.out::println);

        System.out.println(TestEnum.valueOf("A4"));
    }

    @Test
    public void testxor() {
        int a = 23;
        int b = 14;
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.println(a);
        System.out.println(b);
    }

    @Test
    public void testDi() {
        double w = 60;
        double h = 1.78d;
        double di = w / (h * h);
        System.out.println(di);
    }

    @Test
    public void test2() {
        String a = "";
        String[] split = "".split(";");
        System.out.println(split.length);
        Map<String, String> map = new HashMap<>();
        map.put("aa,bb,cc", "aaa");
        System.out.println(map.containsKey("aa"));

    }

    @Test
    public void test3() throws UnsupportedEncodingException {

        String str = "04";

        System.out.println(Integer.valueOf(str));
    }

    @Test
    public void test4() {
        UploadData uploadData = new UploadData();
        uploadData.setFields1("a");
        uploadData.setFields2("b");
//            Map describe = BeanUtils.describe(1);
//            System.out.println(describe);
        System.out.println(JSON.toJSONString(uploadData));
    }

    @Test
    public void test5() {
        System.out.println(Thread.currentThread().getName());

        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
        });
        t1.setName("custom-t");
        t1.start();
    }

    @Test
    public void testSystemProperty() {
//        System.out.println("java版本号：" + System.getProperty("java.version")); // java版本号
//
//
//        System.out.println("Java提供商名称：" + System.getProperty("java.vendor")); // Java提供商名称
//
//
//        System.out.println("Java提供商网站：" + System.getProperty("java.vendor.url")); // Java提供商网站
//
//
//        System.out.println("jre目录：" + System.getProperty("java.home")); // Java，哦，应该是jre目录
//
//
//        System.out.println("Java虚拟机规范版本号：" + System.getProperty("java.vm.specification.version")); // Java虚拟机规范版本号
//
//
//        System.out.println("Java虚拟机规范提供商：" + System.getProperty("java.vm.specification.vendor")); // Java虚拟机规范提供商
//
//
//        System.out.println("Java虚拟机规范名称：" + System.getProperty("java.vm.specification.name")); // Java虚拟机规范名称
//
//
//        System.out.println("Java虚拟机版本号：" + System.getProperty("java.vm.version")); // Java虚拟机版本号
//
//
//        System.out.println("Java虚拟机提供商：" + System.getProperty("java.vm.vendor")); // Java虚拟机提供商
//
//
//        System.out.println("Java虚拟机名称：" + System.getProperty("java.vm.name")); // Java虚拟机名称
//
//
//        System.out.println("Java规范版本号：" + System.getProperty("java.specification.version")); // Java规范版本号
//
//
//        System.out.println("Java规范提供商：" + System.getProperty("java.specification.vendor")); // Java规范提供商
//
//
//        System.out.println("Java规范名称：" + System.getProperty("java.specification.name")); // Java规范名称
//
//
//        System.out.println("Java类版本号：" + System.getProperty("java.class.version")); // Java类版本号
//
//
//        System.out.println("Java类路径：" + System.getProperty("java.class.path")); // Java类路径
//
//
//        System.out.println("Java lib路径：" + System.getProperty("java.library.path")); // Java lib路径
//
//
//        System.out.println("Java输入输出临时路径：" + System.getProperty("java.io.tmpdir")); // Java输入输出临时路径
//
//
//        System.out.println("Java编译器：" + System.getProperty("java.compiler")); // Java编译器
//
//
//        System.out.println("Java执行路径：" + System.getProperty("java.ext.dirs")); // Java执行路径
//
//
        System.out.println("操作系统名称：" + System.getProperty("os.name")); // 操作系统名称
//
//
//        System.out.println("操作系统的架构：" + System.getProperty("os.arch")); // 操作系统的架构
//
//
//        System.out.println("操作系统版本号：" + System.getProperty("os.version")); // 操作系统版本号
//
//
        System.out.println("文件分隔符：" + System.getProperty("file.separator")); // 文件分隔符
//
//
//        System.out.println("路径分隔符：" + System.getProperty("path.separator")); // 路径分隔符
//
//
//        System.out.println("直线分隔符：" + System.getProperty("line.separator")); // 直线分隔符
//

        System.out.println("操作系统用户名：" + System.getProperty("user.name")); // 用户名


        System.out.println("操作系统用户的主目录：" + System.getProperty("user.home")); // 用户的主目录


        System.out.println("当前程序所在目录：" + System.getProperty("user.dir")); // 当前程序所在目录
    }

    @Test
    public void test11() {
        Map<Integer, String> map = new ConcurrentHashMap<>();
        IntStream.rangeClosed(1, 10).forEach(x -> map.putIfAbsent(x, String.valueOf(x)));
        Arrays.asList(1, 4, 7, 3).forEach(x -> {
            if (map.containsKey(x)) {
                map.remove(x);
            }
        });
        System.out.println(map);
    }

    private String dealContent(String content) {
        if (content.getBytes().length - 10 > 0) {
            content = dealContent(content.substring(0, content.length() - 1));
        }
        return content;
    }

    @Test
    public void test12() {
        ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        System.out.println(concurrentHashMap.remove(10));
    }


    private static volatile CopyOnWriteArrayList<String> copyList = new CopyOnWriteArrayList<>();

    @Test
    public void test13() {
        copyList.add("hello");
        copyList.add("huawei");
        copyList.add("wellcome");
        copyList.add("to");
        copyList.add("nanjing");

        Thread threadOne = new Thread(() -> {
            copyList.set(1, "xiaomi");
            copyList.remove(2);
            copyList.remove(3);
        });

        Iterator<String> iterator = copyList.iterator();
        threadOne.start();
        try {
            threadOne.join();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(copyList);
    }

    @Test
    public void test14() {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        lock.lock();
        try {
            System.out.println("begin wait");
            condition.await();
            System.out.println("end wait");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        lock.lock();
        System.out.println("begin signal");
        condition.signal();
        System.out.println("end signal");
        lock.unlock();
    }

    @Test
    public void test15() {
        int count = 0;
        for (int i = 0; ; ) {
            count++;
            if (count > 5) {
                System.out.println(i);
                break;
            }
        }
        System.out.println(count);
    }

    @Test
    public void test16() throws UnsupportedEncodingException {
        String ss = "192.1.10.6,192.1.10.10,192.1.10.6,192.1.10.11,192.1.10.13,18,192.1.10.13,13";
        int length = ss.getBytes(StandardCharsets.UTF_8).length;
        System.out.println(length);
        while (length - 25 > 0) {
            ss = ss.substring(0, ss.lastIndexOf(","));
            length = ss.getBytes(StandardCharsets.UTF_8).length;
        }
        System.out.println(ss);
    }

    @Test
    public void test17() {
        AtomicInteger atomicInteger = new AtomicInteger(6);
        for (int i = 0; i < 10; i++) {
            System.out.println(atomicInteger.incrementAndGet());
        }
    }

    @Test
    public void test18() {
        System.out.println(UUID.nameUUIDFromBytes(new byte[16]).toString());
    }

    @Test
    public void test19() {
        long ll = 7L;
        System.out.println(7 / 2);
    }

    public static String makeRandomPassword(int len) {
        char[] charArr = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
        StringBuffer sb = new StringBuffer();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int x = 0; x < len; ++x) {
            sb.append(charArr[random.nextInt(charArr.length)]);
        }
        return sb.toString();
    }

    public static String getRandomPassword(int len) {
        String result = makeRandomPassword(len);
        if (result.matches(".*[a-z]+.*") && result.matches(".*[A-Z]+.*") && result.matches(".*[0-9]+.*")) {
            return result;
        }
        return getRandomPassword(len);
    }

    public static String getRandomPassword() {
        return getRandomPassword(16);
    }

    @Test
    public void testStr() {
        Map<String, Integer> map = new HashMap<>();
        map.put("page", 1);
        map.put("size", 10);
        map.put("adc", 11);
        System.out.println(map);

    }

    @Test
    public void testTimeStamp() throws UnsupportedEncodingException {
        System.out.println(Calendar.getInstance().getTimeInMillis());
        long val = Calendar.getInstance().getTimeInMillis() - 3600L * 24;
        System.out.println(val);
        Date date = new Date(val);
        System.out.println(date.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(date.getTime()));
    }

    @Test
    public void testAtomic() {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        System.out.println(atomicInteger.get());
        System.out.println(atomicInteger.incrementAndGet());
//        System.out.println(atomicInteger.getAndIncrement());
    }

    @Test
    public void testPs() {
        SonOne sonOne = new SonOne("make money");
        sonOne.work();
        SonTwo sonTwo = new SonTwo(10);
        sonTwo.cal();

        System.out.println(Parent.class.isAssignableFrom(sonOne.getClass()));
    }

    @Test
    public void testPath() {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        System.out.println(antPathMatcher.match("/**/*", "/test.css"));
        System.out.println(antPathMatcher.match("/*", "/test/child1/child2/test.css"));
        System.out.println(antPathMatcher.match("/**", "/test/child/aa.css"));
        System.out.println(antPathMatcher.match("/**/*", "/test/aa.css"));
        System.out.println(antPathMatcher.match("/*.js", "/a.js"));
        System.out.println(antPathMatcher.match("static/*", "static/a.js"));
    }

    @Test
    public void testPeriod() {
        LocalDate localDate = LocalDate.now();
        LocalDate localDate2 = LocalDate.of(2021, 5, 18);
        Period period = Period.between(localDate2, localDate);

        System.out.println(period.getDays());

        Period period2 = Period.ofWeeks(7);
        System.out.println(period2.getDays());
    }

    @Test
    public void testListSub() {
        String s = "sst";
        System.out.println(Optional.ofNullable(s).orElse("null str"));
    }

    @Test
    public void testSkipLimit() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n"));
        int current = 2;
        int pageSize = 3;
        System.out.println(list.stream().skip((current - 1) * pageSize).limit(pageSize).collect(toList()));
    }

    @Test
    public void testCharset() {
        byte[] b = {-26, -75, -73};
        ByteBuffer bb = ByteBuffer.allocate(3);
        bb.put(b, 0, 3);
        bb.flip();
        CharBuffer cb = UTF_8.decode(bb);
        char c = cb.charAt(0);
        System.out.println(c);
    }

    @Test
    public void testAllMatch() {
        List<String> a1 = new ArrayList<>(Arrays.asList("a", "b", "c"));
        List<String> a2 = new ArrayList<>(Arrays.asList("a", "b", "c", "d"));
        System.out.println(a2.containsAll(a1));
    }

    @Test
    public void test2Num() {
        String val = "BA";
        char[] chars = val.toCharArray();

        int total = 0;
        for (int i = 0; i < chars.length; i++) {
            total += chars[i] - 'A';
        }

        StringBuilder build = new StringBuilder();
        while (total == 0) {
            int cur = total % 26;
            build.append(cur);
            total /= 10;
        }
        System.out.println(build.toString());
    }

    @Test
    public void test99() {
        boolean present = Optional.ofNullable(null).isPresent();
        System.out.println(present);
        System.out.println(Optional.empty().isPresent());
    }

    @Test
    public void test100() {
        AtomicInteger integer = new AtomicInteger(0);

        List<Character> vals = new ArrayList<>();
        IntStream.rangeClosed(0, 25).forEach(value -> vals.add((char) ('A' + value)));
        Character remove = vals.remove(vals.size() - 1);
        vals.add(0, remove);
        System.out.println(vals);
        Character[] letters = vals.toArray(new Character[0]);
        List<Character> list = new ArrayList<>();
        int num = 26;
        while (num != 0) {
            int offset = num % 26;
            int tryDivide = num / 26;
            if (tryDivide != 1) {
                list.add(letters[offset]);
            }
            num /= 26;
        }
        System.out.println(list);
    }

    @Test
    public void testBitShift() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        long create = 1626324312377L;
        long end = 1626408060190L;
        long start = 1626324448324L;
        long period = (end - start) / 3600;
//        System.out.println(period);
        String createTime = fmt.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(create), ZoneId.of("Asia/Shanghai")));
        String timeNow = fmt.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(start), ZoneId.of("Asia/Shanghai")));
        String timeEnd = fmt.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(end), ZoneId.of("Asia/Shanghai")));
        long now = System.currentTimeMillis();
        System.out.println(now);
        String exp = fmt.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(now), ZoneId.of("Asia/Shanghai")));
        System.out.println(exp);

//        System.out.println(createTime);
//        System.out.println("开始：" + timeNow);
//        System.out.println("结束：" + timeEnd);
    }

    @Test
    public void timeLearn() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "a");
        map.put("b", "b");
        map.computeIfPresent("b", (k, v) -> {
            return "c" + 1;
        });
        System.out.println(map);
        map.computeIfPresent("c", (k, v) -> "c");
        System.out.println(map);

    }

    @Test
    public void testCodeBlock() {
        try {
            if (check(2))
                throw new RuntimeException();
        } catch (Exception e) {
            System.out.println("error");
            return;
        }

        try {
            System.out.println("next try");
        } finally {
            System.out.println("finally");
        }
    }

    private boolean check(int i) {
        return i == 1;
    }

    @Test
    public void testCron() {
        ConcurrentHashMap<Integer, Boolean> cur = new ConcurrentHashMap<>();
        System.out.println(cur.computeIfAbsent(1, k -> true));
    }


    private CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

    @Test
    public void testCyclicBarrier1() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        IntStream.rangeClosed(1, 2).forEach(x -> {
            executorService.submit(() -> {
                try {
                    System.out.println(Thread.currentThread() + " do step1");
                    cyclicBarrier.await();
                    System.out.println(Thread.currentThread() + " do step2");
                    cyclicBarrier.await();
                    System.out.println(Thread.currentThread() + " do remain step");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        });

        executorService.shutdown();
    }


    private volatile String shareVal = "";
    private volatile Integer shareIndex = 0;
    private List<String> shareList = Collections.synchronizedList(new ArrayList<>());
    private CyclicBarrier cyclic2 = new CyclicBarrier(2, new Task(shareVal, shareIndex, shareList));

    @Test
    public void testCyclicBarrier() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        IntStream.rangeClosed(1, 2).forEach(x -> {
            executorService.execute(() -> {
                try {
                    String task = "task" + x;
                    System.out.println(Thread.currentThread() + " do " + task);
                    String elem = task + "-elem";
                    shareIndex = x;
                    shareVal = task;
                    shareList.add(elem);
                    cyclic2.await();
                    System.out.println(Thread.currentThread() + " after do " + task);
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        });

        executorService.shutdown();
    }

    class Task implements Runnable {

        private String val;
        private Integer index;
        private List<String> list;

        public Task(String val, Integer index, List<String> list) {
            this.val = val;
            this.index = index;
            this.list = list;
        }

        @Override
        public void run() {
            System.out.println("val: " + val);
            System.out.println("index: " + index);
            System.out.println("list: " + list);
        }
    }

    @Test
    public void testFinal() {
        TestData testData = new TestData();
        testData.setStr("aaa");
        testData.getMap().put("A", "a");
        System.out.println(testData);
    }

    @Test
    public void nonDupSubStr() {
        System.out.println(change("ewppckwekew"));
    }

    private int change(String str) {
        char[] chars = str.toCharArray();
        if (chars.length == 0) {
            return 0;
        }
        Stack<Character> stack = new Stack<>();
        int len = 0;
        for (int i = 0; i < chars.length; i++) {
            if (stack.empty()) {
                stack.push(chars[i]);
                continue;
            }
            if (stack.peek() == chars[i] || stack.contains(chars[i])) {
                len = Math.max(len, stack.size());
                while (!stack.empty()) {
                    stack.pop();
                }
            }
            stack.push(chars[i]);
        }
        return len;
    }

    @Test
    public void testCl() {
//        String s = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "T00:00:00";
//        String s1 = "2022-02-21 00:00:00";
//        String s2 = "2022-02-23 00:00:00";
//        String s3 = "2022-02-23T00:00:00";
//        String s4 = "2022-02-25T00:00:00";
//        System.out.println(LocalDateTime.parse(s).toInstant(ZoneOffset.of("+8")).toEpochMilli());
//        System.out.println(LocalDateTime.parse(s).toInstant(ZoneOffset.of("+0")).toEpochMilli());
//        System.out.println(LocalDateTime.parse(s1, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toInstant(ZoneOffset.of("+0")).toEpochMilli());
//        System.out.println(LocalDateTime.parse(s2, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toInstant(ZoneOffset.of("+0")).toEpochMilli());
//        System.out.println(LocalDateTime.parse(s3).toInstant(ZoneOffset.of("+8")).toEpochMilli());
//        System.out.println(LocalDateTime.parse(s3).toInstant(ZoneOffset.of("+8")).toEpochMilli());
//        System.out.println(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
//        System.out.println(LocalDateTime.now().toInstant(ZoneOffset.of("+0")).toEpochMilli());
        long c1 = new Timestamp(new Date().getTime()).getTime();
        System.out.println(c1);
        Date date1 = new Date(c1);
        System.out.println(date1);
        long c2 = c1 + 8 * 60 * 60 * 1000;
        Date date = new Date(c2);
        System.out.println(date);
    }

    @Test
    public void testC() {
        List<Integer> list = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
            add(5);
            add(6);
        }};
        Optional<Integer> reduce = list.stream().reduce((x, y) -> x + 1);
        Optional<Integer> max = list.stream().max(Comparator.naturalOrder());
        System.out.println(reduce.get());
        System.out.println(max.get());
    }

    @Test
    public void testClassLoad() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        map.putIfAbsent(1, "1");
        map.putIfAbsent(2, "2");
        map.putIfAbsent(3, "3");
        map.computeIfPresent(3, (k, v) -> null);
        System.out.println(map);
    }

    private static final class TestLong {
        private String key = "KEY";
        private Long val = 10L;
        private List<String> list = new ArrayList<>();

        public TestLong() {
            System.out.println(key);
            System.out.println(val);
            list.add("a");
            list.add("c");
            list.add("e");
            System.out.println(list);
        }

        public TestLong(String key, Long val) {
            this.key = key;
            this.val = val;
        }

        public String getKey() {
            return key;
        }

        public Long getVal() {
            return val;
        }
    }

    @Test
    public void testCache() {
        ThreadLocalRandom current = ThreadLocalRandom.current();
        System.out.println(current.nextInt(1, 5));
    }

    @Test
    public void testPermute() {
//        String[] ips = {"128.0.0.10", "192.10.11.13", "168.66.52.1"};
        String[] ips = {"128.0.0.10", "192.10.11.13"};
        int length = ips.length;
        boolean[] used = new boolean[length];
        List<List<String>> res = new ArrayList<>();
        dfs(ips, 0, used, new LinkedList<>(), res);
        System.out.println(res);
    }

    private void dfs(String[] ips, int level, boolean[] used, Deque<String> tmp, List<List<String>> res) {
        if (level == ips.length) {
            res.add(new ArrayList<>(tmp));
            return;
        }

        for (int i = 0; i < used.length; i++) {
            if (!used[i]) {
                tmp.addLast(ips[i]);
                used[i] = true;
                dfs(ips, level + 1, used, tmp, res);
                used[i] = false;
                tmp.removeLast();
            }
        }
    }

    @Test
    public void testSet() {
        List<TestSet> list = new ArrayList<TestSet>() {{
            add(new TestSet("k1", "vcc", "vccc"));
            add(new TestSet("k2", "vdd", "vddd"));
            add(new TestSet("k2", "vee", "veee"));
        }};

        Map<String, TestSet> collect = list.stream().collect(toMap(TestSet::getK, Function.identity(), (pre, post) -> pre));
        System.out.println(collect);
    }

    private static class TestSet {
        private String k;
        private String v1;
        private String v2;

        @Override
        public String toString() {
            return "TestSet{" +
                    "k='" + k + '\'' +
                    ", v1='" + v1 + '\'' +
                    ", v2='" + v2 + '\'' +
                    '}';
        }

        public TestSet(String k, String v1, String v2) {
            this.k = k;
            this.v1 = v1;
            this.v2 = v2;
        }

        public String getK() {
            return k;
        }

        public String getV1() {
            return v1;
        }

        public String getV2() {
            return v2;
        }
    }

    @Test
    public void testPage() {
        int pageSize = 20;
        int total = 61;
        total -= 1;
        int curPage = 2;
        int ceil = (int) Math.ceil((double) total / pageSize);
        System.out.println(Math.min(curPage, ceil));
    }


    static class TestCloneZZ implements Serializable {
        private String name;
        private TestInner inner;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public TestInner getInner() {
            return inner;
        }

        public void setInner(TestInner inner) {
            this.inner = inner;
        }

//        @Override
//        protected Object clone() throws CloneNotSupportedException {
//            TestCloneZZ zz = (TestCloneZZ) super.clone();
//            zz.inner = (TestInner)inner.clone();
//            return zz;
//        }

        @Override
        public String toString() {
            return "TestCloneZZ{" +
                    "name='" + name + '\'' +
                    ", inner=" + inner +
                    '}';
        }
    }

    static class TestInner implements Serializable {
        private String name;

        public TestInner(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "TestInner{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    @Test
    public void testGotoBreak() {
        int[] arr1 = new int[]{1, 2, 3, 4, 5};
        int[] arr2 = new int[5];
        int[] arr3 = new int[5];
        System.arraycopy(arr1, 0, arr2, 0, arr1.length);
        System.arraycopy(arr1, 0, arr3, 0, arr1.length);
        for (int i : arr1) {
            System.out.println("1 level = " + i);
            Flag:
            for (int j : arr2) {
                System.out.println("2 level = " + j);
                for (int k = 0; k < arr3[k]; k++) {
                    if (arr3[k] == 3) {
                        break Flag;
                    }
                    System.out.println("3 level = " + arr3[k]);
                    System.out.println("3 level = " + arr3[k]);
                }
            }
        }
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        TestCloneZZ cl1 = new TestCloneZZ();
        cl1.setName("my test clone class1");
        cl1.setInner(new TestInner("my test inner class 1"));

//        TestCloneZZ cl2 = new TestCloneZZ();
//        BeanUtils.copyProperties(cl1, cl2);
//        System.out.println(cl2);
        TestCloneZZ cl2 = DeepCloneUtil.deepClone(cl1);
//        cl2.setInner((TestInner) cl1.getInner().clone());
        cl2.getInner().setName("my test inner class 222");
        cl2.setName("my test clone class2");
        System.out.println(cl2);
        System.out.println(cl1);
    }

    @Test
    public void testcal() {
        int v = (int) Math.ceil((double) 0 * 100);
        System.out.println(v);
    }

    @Test
    public void testDup() {
        List<TestDup> list = new ArrayList<>();
        list.add(new TestDup("123", 1));
        list.add(new TestDup("124", 4));
        list.add(new TestDup(null, 2));
        list.add(new TestDup(null, 2));
        Map<String, Integer> collect = list.stream().collect(toMap(TestDup::getKey, TestDup::getStatus));
        System.out.println(collect);
    }

    static class TestDup {
        private String key;
        private Integer status;

        public TestDup(String key, Integer status) {
            this.key = key;
            this.status = status;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

    private static Map<Integer, String> map = new HashMap<Integer, String>(){{
        put(1, "星期一");
        put(2, "星期二");
        put(3, "星期三");
        put(4, "星期四");
        put(5, "星期五");
        put(6, "星期六");
        put(7, "星期日");
    }};

    @Test
    public void testDay() {
        LocalDateTime now = LocalDateTime.now();
        int value = now.plusDays(1L).getDayOfWeek().getValue();
        System.out.println(value);
        List<String> days = new ArrayList<>();
        for (int i = 0; i < 100; i ++ ) {
            LocalDateTime localDateTime = now.plusDays(i * 500);
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            days.add(String.format("%s天=> %s %s", (i+1) * 500, fmt.format(localDateTime), map.get(localDateTime.getDayOfWeek().getValue())));
        }
        days.forEach(System.out::println);
    }

    @Test
    public void testChars() {
        String arrStr = "12.16.11.20\r\n12.16.11.20";
        String arrStr2 = "\t\r\n13.13.13.20";
        String collect = Arrays.stream(arrStr2.split("\t?\r\n")).filter(StringUtils::isNotEmpty).distinct().collect(joining(""));
        System.out.println(collect);
//        System.out.println(arrStr.replaceAll("\t?\r\n", ""));
//        System.out.println(arrStr2.replaceAll("\t?\n\n", ""));
    }
    @Test
    public void testFor() {
        int[] arr = {1, 3, 5, 7};
        int start = 0;
        int offset = 100;
        int total = 1024;
        start += offset;
        for (; start + offset < total; start += offset) {
            System.out.println(start);
        }
        System.out.println(start);
    }

    @Test
    public void testGO() {
        String reg = "[\\u4e00-\\u9fa5]+(\\u4e2d\\u5fc3|\\u90e8\\u843d)";
        String center1 = "运营保障中心团队";
        String center2 = "新城市部落团队";
        Pattern compile = Pattern.compile(reg);
        Matcher matcher = compile.matcher(center1);
        if ( matcher.find()) {
            System.out.println("1");
            System.out.println(matcher.group());
        }
        Matcher matcher2 = compile.matcher(center2);
        if (matcher2.find()) {
            System.out.println("2");
            System.out.println(matcher2.group());
        }

    }

    @Test
    public void testNoneMatch() {
        List<String> list = new ArrayList<String>() {{
            add("1");
            add("2");
            add("3");
        }};
        List<String> res = new ArrayList<>();
        List<ListHas> listHases = new ArrayList<>();
        listHases.add(new ListHas(Arrays.asList("4", "5"), "a"));
        listHases.add(new ListHas(Arrays.asList("1", "6"), "b"));
        listHases.add(new ListHas(Arrays.asList("7", "3"), "c"));
        listHases.forEach(x -> {
            if (x.getList().stream().noneMatch(list::contains)) {
                res.add(x.getName());
            }
        });
        System.out.println(res);
    }

    private static final class ListHas{
        private List<String> list;
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ListHas(List<String> list, String name) {
            this.list = list;
            this.name = name;
        }

        public void setList(List<String> list) {
            this.list = list;
        }

        public List<String> getList() {
            return list;
        }

        public ListHas(List<String> list) {
            this.list = list;
        }
    }

    @Test
    public void testCompleteRun() {
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                log.error("出错", e);
            }
            return 1;
        });
        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> 2);
        CompletableFuture.allOf(f1, f2);
        System.out.println(f1.join() + f2.join());
    }

    @Test
    public void testComplete2() {
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                log.error("", e);
            }
            return 1;
        }).applyToEither(CompletableFuture.supplyAsync(() -> 2), (Integer a) -> a + 10);
        Integer join = f1.join();
        System.out.println(join);

        List<Integer> list1 = new ArrayList<>();
        CompletableFuture<Void> f2 = CompletableFuture.supplyAsync(FuncEx.wrap(() -> {
            Thread.sleep(3000);
            return 1;
        })).thenApply((Integer a) -> a * 5).acceptEither(CompletableFuture.supplyAsync(FuncEx.wrap(() -> {
            Thread.sleep(5000);
            return 3;
        })), (Integer a) -> list1.add(a + 10));
        f2.join();
        System.out.println(list1);
    }

    @Test
    public void testJson() {
        String arr = "[\"assess-1\",\"assess-2\"]";
        List<String> strings = JSON.parseArray(arr, String.class);
        System.out.println(strings);
    }

    private static final class TestPriority {
        private Integer priority;
        private String name;

        public TestPriority() {
        }

        public TestPriority(Integer priority, String name) {
            this.priority = priority;
            this.name = name;
        }

        public Integer getPriority() {
            return priority;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "TestPriority{" +
                    "priority=" + priority +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Test
    public void testPriorityQueue() {
//        PriorityQueue<TestPriority> queue = new PriorityQueue<>(Comparator.comparing(TestPriority::getPriority).thenComparing(TestPriority::getName));
//
//        queue.offer(new TestPriority(3, "3p1"));
//        queue.offer(new TestPriority(4, "4p"));
//        queue.offer(new TestPriority(3, "3p2"));
//        queue.offer(new TestPriority(3, "3p3"));
//        queue.offer(new TestPriority(2, "2p"));
//        queue.offer(new TestPriority(1, "1p"));
//
//
//        while (!queue.isEmpty()) {
//            TestPriority p = queue.poll();
//            System.out.println(p);
//        }

        PriorityQueue<Long> queue = new PriorityQueue<>((o1, o2) -> (int)(o2 - o1));
        queue.add(6L);
        queue.add(3L);
        queue.add(5L);
        queue.add(9L);
        queue.add(7L);
        System.out.println(queue.peek());
    }

    @Test
    public void testGroupBy() {
        List<MyDemo> myDemos = new ArrayList<>();
        for (int i = 1; i <= 24; i++) {
            myDemos.add(new MyDemo(i, "gp" + (i % 5)));
        }
        Map<String, List<MyDemo>> collect = myDemos.stream()
                .sorted(Comparator.comparing(MyDemo::getKey).reversed()
                        .thenComparing(Comparator.comparing(MyDemo::getId).reversed()))
                .collect(groupingBy(MyDemo::getKey, LinkedHashMap::new, toList()));
        collect.forEach((k, v) -> System.out.println(k + "=>" + v));
    }

    private static class MyDemo{
        private Integer id;
        private String key;

        public MyDemo(Integer id, String key) {
            this.id = id;
            this.key = key;
        }

        public Integer getId() {
            return id;
        }

        public String getKey() {
            return key;
        }

        @Override
        public String toString() {
            return "MyDemo{" +
                    "id=" + id +
                    ", key='" + key + '\'' +
                    '}';
        }
    }

    @Test
    public void testFor2() {
        Node node = buildLink(Arrays.asList(1, 2, 3, 4, 5));
        for (Node cur = node; cur != null; cur = cur.next) {
            System.out.println(cur);
        }
    }

    private Node buildLink(List<Integer> list) {
        Node head = new Node(null);
        Node pre = head;
        for (Integer data : list) {
            Node node = new Node(data);
            node.pre = pre;
            pre.next = node;
            pre = node;
        }
        return head;
    }

    static final class Node{
        Object data;
        Node pre;
        Node next;

        public Node(Object data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    '}';
        }
    }

    @Data
    public static final class StudentExcelModel {
        @ExcelProperty(value = "班级")
        private String className;
        @ExcelProperty(value = "学号")
        private String stuNumber;
        @ExcelProperty(value = "姓名")
        private String stuName;
    }

    @Test
    public void convertExcel() {

        String fileName1 = "F:\\wsy_file\\all_student_data.xls";
        List<StudentExcelModel> cachedDataList = new ArrayList<>();
        EasyExcel.read(fileName1, StudentExcelModel.class, new ReadListener<StudentExcelModel>() {
            @Override
            public void invoke(StudentExcelModel data, AnalysisContext context) {
                if (data.getClassName().startsWith("高一")) {
                    cachedDataList.add(data);
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {

            }
        }).sheet().doRead();

        List<StudentClassExcelModel> classExcelModels = new ArrayList<>();

        String fileName2 = "F:\\wsy_file\\student_classify.xls";
        EasyExcel.read(fileName2, StudentClassExcelModel.class, new ReadListener<StudentClassExcelModel>() {
            @Override
            public void invoke(StudentClassExcelModel data, AnalysisContext context) {
                ReadSheetHolder readSheetHolder = context.readSheetHolder();
                String sheetName = readSheetHolder.getSheetName();
                data.setClassName("高一" + data.getClassName());
                data.setSheetName(sheetName);
                classExcelModels.add(data);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {

            }
        }).headRowNumber(2).doReadAll();

        Map<String, String> stuNameNumMap = cachedDataList.stream().collect(toMap(data -> data.getStuName(), data -> data.getStuNumber(), (v1, v2) -> v1));

        List<StudentWriteClassExcelModel> writeDataList = new ArrayList<>();
        for (StudentClassExcelModel classExcelModel : classExcelModels) {
            String number = stuNameNumMap.get(classExcelModel.getName());
            if (StringUtils.isEmpty(number)) {
                System.out.println(classExcelModel.getName() + "学号未获取到");
                continue;
            }
            StudentWriteClassExcelModel writeData = new StudentWriteClassExcelModel(classExcelModel, number);
            writeDataList.add(writeData);
        }
//        System.out.println(writeDataList);
        Map<String, List<StudentWriteClassExcelModel>> writeDataGroup = writeDataList.stream().collect(groupingBy(data -> data.getSheetName(), LinkedHashMap::new, Collectors.toList()));
        doWrite(writeDataGroup);
    }

    private void doWrite(Map<String, List<StudentWriteClassExcelModel>> writeDataGroup) {
//        String fileName = TestFileUtil.getPath() + "repeatedWrite" + System.currentTimeMillis() + ".xlsx";
        String fileName =  "F:\\wsy_file\\student_classify_" + System.currentTimeMillis() + ".xlsx";
        // 这里 指定文件
        try (ExcelWriter excelWriter = EasyExcel.write(fileName, StudentWriteClassExcelModel.class).build()) {
            // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
            for (Map.Entry<String, List<StudentWriteClassExcelModel>> sheetEntry : writeDataGroup.entrySet()) {
                String sheetName = sheetEntry.getKey();
                WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).relativeHeadRowIndex(0).build();
                AtomicInteger count = new AtomicInteger(1);
                List<StudentWriteClassExcelModel> sheetDataList = sheetEntry.getValue().stream()
                        .sorted(Comparator.comparing(data -> data.getNumber()))
                        .peek(data -> data.setSeq(count.getAndIncrement()))
                        .collect(toList());
                excelWriter.write(sheetDataList, writeSheet);
            }
        }
    }

    @Data
    public static class StudentClassExcelModel {
        @ExcelProperty(value = "序号")
        private Integer seq;
        @ExcelProperty(value = "学生姓名")
        private String name;
        @ExcelProperty(value = "性别")
        private String male;
        @ExcelProperty(value = "班级")
        private String className;
        @ExcelIgnore
        private String sheetName;
    }

    @Data
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 1)
    @HeadFontStyle(fontHeightInPoints = 20)
    @ContentFontStyle(fontHeightInPoints = 14)
    public static class StudentWriteClassExcelModel {
        @ExcelProperty(value = {"选课标题","序号"})
        @ColumnWidth(value = 10)
        private Integer seq;
        @ExcelProperty(value = {"选课标题","班级"})
        @ColumnWidth(value = 20)
        private String className;
        @ExcelProperty(value = {"选课标题","学号"})
        @ColumnWidth(value = 20)
        private Integer number;
        @ExcelProperty(value = {"选课标题","学生姓名"})
        @ColumnWidth(value = 20)
        private String name;
        @ExcelProperty(value = {"选课标题","性别"})
        @ColumnWidth(value = 10)
        private String male;
        @ExcelProperty(value = {"选课标题","成绩"})
        @ColumnWidth(value = 20)
        private String score;

        @ExcelProperty(value = {"选课"})
        @ColumnWidth(value = 20)
        private String sheetName;

        public StudentWriteClassExcelModel(StudentClassExcelModel m1, String number) {
            this.seq = m1.getSeq();
            this.number = Integer.valueOf(number);
            this.name = m1.getName();
            this.male = m1.getMale();
            this.className = m1.getClassName();
            this.sheetName = m1.getSheetName();
            this.score = "";
        }
    }

    @Test
    public void testRange() {
        IntStream.rangeClosed(1, 1).forEach(System.out::println);
    }


    @Test
    public void testUrl() throws InterruptedException {
        String test = "测试应用【应用名first】123【4556aaaa】";
        int start = 0;
        int end = 0;
        char[] charArray = test.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == '【') {
                start = i;
                continue;
            }
            if (charArray[i] == '】') {
                end = i;
                break;
            }
        }
        System.out.println( test.substring(start + 1, end));
    }


}

