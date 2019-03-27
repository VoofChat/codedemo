package cn.voofchat.codedemo.file;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2019/2/25
 * @time: 11:00 AM
 * Description:
 */
public class App {

    public static ConcurrentLinkedQueue<String> getFiles2(String path) {
        ConcurrentLinkedQueue<String> files = new ConcurrentLinkedQueue<>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                files.add(tempList[i].toString());
            }
        }
        return files;
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        String path = "/apps/home/worker/zhengzhixiong/gongzhonghao/template/wx479c7f488fa64020/wx479c7f488fa64020";
        String substring = path.substring(path.lastIndexOf(File.separator) + 1);
        System.out.println(substring);
//        Set<String> openId1 = syncRead("/Users/bjhl/Desktop/test_am_gongzhonghao_user.merge");
//
//        System.out.println("-----------------");
//
//        Set<String> openIdS21 = syncRead("/Users/bjhl/Desktop/split/split_flieaaa");
//        System.out.println(openIdS21.size());
//        Set<String> openIdS22 = syncRead("/Users/bjhl/Desktop/split/split_flieaab");
//        Set<String> openIdS23 = syncRead("/Users/bjhl/Desktop/split/split_flieaac");
//        Set<String> openIdS24 = syncRead("/Users/bjhl/Desktop/split/split_flieaad");
//
//        System.out.println("-----------------");
//
//        Set<String> openId2 = new HashSet<>();
//        openId2.addAll(openIdS21);
//        openId2.addAll(openIdS22);
//        openId2.addAll(openIdS23);
//        openId2.addAll(openIdS24);

//        Set<String> openId3 = asyncRead1("/Users/bjhl/Desktop/split");
//        System.out.println("-----------------");
//        Set<String> openId4 = asyncRead2("/Users/bjhl/Desktop/split");
//        System.out.println("-----------------");
//        Set<String> openId5 = asyncRead3("/Users/bjhl/Desktop/split");

//        System.out.println("-----------------");
//        System.out.println("同步读取1：" + openId1.size());
//        System.out.println("同步读取2：" + openId2.size());
//        System.out.println("异步读取1：" + openId3.size());
//        System.out.println("异步读取2：" + openId4.size());
//        System.out.println("异步读取3：" + openId5.size());

//        test();
    }

    private static Set<String>  asyncRead1(String s) throws InterruptedException {

        ConcurrentLinkedQueue<String> linkedDeque = getFiles2(s);
        int fileCount = linkedDeque.size();

        CountDownLatch latch = new CountDownLatch(fileCount);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        AtomicLong count = new AtomicLong();
        Set<String> allOpenIds = Collections.synchronizedSet(new HashSet<>());

        long start = System.currentTimeMillis();
        for (int i = 0; i < fileCount; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {

                    long start = System.currentTimeMillis();
                    BufferedReader br = null;
                    try{
                        String filePathName = linkedDeque.poll();
                        if (filePathName == null) {
                            return;
                        }

                        br = new BufferedReader(new FileReader(filePathName));

                        char[] buff = new char[1024 * 1024];
                        while (br.read(buff) != -1) {
                            try{
                                String str = new String(buff).trim();
                                String[] recordArr = str.split("\n");

                                for (int i = 0; i < recordArr.length; i++) {
                                    String[] split = recordArr[i].split("\t");
                                    for (int j = 0; j < split.length; j++) {
                                        if (split.length == 5) {
                                            if (split[2] != null && "广东".equals(split[2]) && "中国".equals(split[3])){
                                                count.addAndGet(1);
                                                allOpenIds.add(split[0]);
                                            }
                                        }
                                    }
                                }
                            }catch (Exception e){
                                continue;
                            }
                        }

                    }catch (Exception e) {
                        // //
                    }finally {
                        latch.countDown();
                        long end = System.currentTimeMillis();
                        System.out.println(Thread.currentThread().getName() + "读取完成, 耗时 = " + (end - start));
                        if (br != null) {
                            try {
                                br.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        }
        latch.await();
        long end = System.currentTimeMillis();
        System.out.println("总耗时：" + (end - start) + ", count = " + count);
        executorService.shutdown();
        return allOpenIds;
    }


    private static Set<String> asyncRead2(String s) {

        ConcurrentLinkedQueue<String> fileLinkedQueue = getFiles2(s);
        LinkedBlockingQueue<String> stringBuffQueue = new LinkedBlockingQueue<>();

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        long start = System.currentTimeMillis();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while(!fileLinkedQueue.isEmpty()) {
                    BufferedReader br = null;
                    try {

                        String filePathName = fileLinkedQueue.poll();
                        if (filePathName == null) {
                            return;
                        }

                        br = new BufferedReader(new FileReader(filePathName));
                        char[] buff = new char[1024 * 1024];
                        while (br.read(buff) != -1) {
                            stringBuffQueue.offer(new String(buff).trim());
                        }

                    }catch (Exception e){

                    } finally {
                        if (br != null) {
                            try {
                                br.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });

        CountDownLatch latch = new CountDownLatch(3);
        ConcurrentHashMap<String, String> openMap = new ConcurrentHashMap<>();
        for (int i = 0; i < 3; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {

                            String str = stringBuffQueue.poll(5, TimeUnit.SECONDS);
                            if (str == null) {
                                break;
                            }

                            str = str.trim();
                            String[] recordArr = str.split("\n");

                            for (int i = 0; i < recordArr.length; i++) {
                                String[] split = recordArr[i].split("\t");

                                if (split.length == 5 && split[2] != null && "广东".equals(split[2]) && "中国".equals(split[3])){
                                    openMap.put(split[0], split[1]);
                                }
                            }

                        }
                    }catch (Exception e){

                    }finally {
                        latch.countDown();
                        long end = System.currentTimeMillis();
                        System.out.println(Thread.currentThread().getName() + "读取完成, 耗时 = " + (end - start - 5000));
                    }
                }
            });
        }

        try {
            latch.await();
            executorService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Set<String> allOpenIds = new HashSet<>();
        for(Map.Entry<String, String> entry: openMap.entrySet()) {
            allOpenIds.add(entry.getKey());
        }
        return allOpenIds;
    }

    private static Set<String> asyncRead3(String s) {

        AtomicLong count = new AtomicLong();
        ConcurrentLinkedQueue<String> files = getFiles2(s);
        System.out.println(files);

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Set<String> openIds = Collections.synchronizedSet(new HashSet<>());


        long start = System.currentTimeMillis();
        new GongzhongnhaoUserRead(executorService, files, new GongzhongnhaoUserReadInterface(){

            @Override
            public boolean multiThreadAsyncRead(Thread thread, String filePath) {
                String line = "";
                BufferedReader br = null;
                boolean isSuccess = true;
                long start = System.currentTimeMillis();
                try {
                    br = new BufferedReader(new FileReader(filePath));

                    char[] buff = new char[10000 * 60];
                    while (br.read(buff) != -1) {

                        String str = new String(buff).trim();
                        String[] recordArr = str.split("\n");

                        for (int i = 0; i < recordArr.length; i++) {
                            line = recordArr[i];
                            try{
                                String[] split = line.split("\t");
                                if (split.length == 5) {

                                    String rUserId = split[0];
                                    String rOpenId = split[1];
                                    String rProvince = split[2];
                                    String rCountry = split[3];
                                    String rAppId = split[4];

                                    if ("中国".equals(rCountry) && "广东".equals(rProvince)) {
                                        count.addAndGet(1);
                                        openIds.add(rUserId);
                                    }

                                } else {
                                    System.out.println(Thread.currentThread().getName() + ", 数据不符合格式: " + "filePath = " + filePath + ", line = " + line);
                                }
                            }catch (Exception e){
                                System.out.println("数据解析出现错误");
                                continue;
                            }
                        }

                    }
                    System.out.println("------------------------------------------------------------------------");
                }catch (Exception e) {
                    isSuccess = false;
                    System.out.println(thread.getName() + ", 读取出现错误");
                }finally {
                    long end = System.currentTimeMillis();
                    System.out.println(thread.getName() + "读取完成, 耗时 = " + (end - start));
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return isSuccess;
            }

            @Override
            public void success() {
                System.out.println("[ success ] count = " + count);
            }

            @Override
            public void fail(String msg, Exception e) {
                System.out.println("[ error ] msg:" + e.getMessage());
                openIds.clear();
            }

        }).execute();

        long end = System.currentTimeMillis();
        System.out.println("总耗时：" + (end - start));

        executorService.shutdown();

        return openIds;
    }

    private static Set<String> syncRead(String filePathName) {
        Set<String> openIds = new HashSet<>();
        try{
            long start = System.currentTimeMillis();
            BufferedReader br = new BufferedReader(new FileReader(filePathName));

            int sum = 0;
            int charSum = 0;
            /*
            char[] buff = new char[10000 * 60];
            while (br.read(buff) != -1) {
                try{
                    String str = new String(buff).trim();
                    charSum += str.length();
                    String[] recordArr = str.split("\n");

                    for (int i = 0; i < recordArr.length; i++) {
                        String line = recordArr[i];
                        try{
                            String[] split = line.split("\t");
                            if (split.length == 5) {

                                String rUserId = split[0];
                                String rOpenId = split[1];
                                String rProvince = split[2];
                                String rCountry = split[3];
                                String rAppId = split[4];

                                if ("中国".equals(rCountry) && "广东".equals(rProvince)) {
                                    openIds.add(rUserId);
                                }

                            } else {
                                System.out.println(Thread.currentThread().getName() + ", 数据不符合格式: " + "filePath = " + filePathName + ", line = " + line);
                            }
                        }catch (Exception e){
                            System.out.println("数据解析出现错误");
                            continue;
                        }
                    }

                }catch (Exception e){
                    continue;
                }
            }
            */

            String line ;
            while( (line = br.readLine()) != null) {
                String[] split = line.split("\t");
                if (split.length == 5) {
                    sum++;
                    String rUserId = split[0];
                    openIds.add(rUserId);
                }else{
                    System.out.println(line);
                    System.out.println("========");
                }
            }

            long end = System.currentTimeMillis();
//            System.out.println("字符数： " + charSum);
            System.out.println(Thread.currentThread().getName() + ",读大文件   BufferedReader + char[], 耗时=" + (end - start) + ", sum = " + sum );

        }catch (Exception e) {
            // //
        }
        return openIds;
    }




    static class GongzhongnhaoUserRead{

        /**
         * 线程池
         */
        private ExecutorService executor;

        /**
         * 文件队列
         */
        private ConcurrentLinkedQueue<String> deque;

        /**
         * 结果队列
         */
        private ConcurrentLinkedQueue<Boolean> resultQueue = new ConcurrentLinkedQueue<>();

        private GongzhongnhaoUserReadInterface gongzhongnhaoUserRead;

        public GongzhongnhaoUserRead(ExecutorService executor, ConcurrentLinkedQueue<String> deque, GongzhongnhaoUserReadInterface userRead) {
            this.executor = executor;
            this.deque = deque;
            this.gongzhongnhaoUserRead = userRead;
        }

        public void execute(){
            int size = deque.size();
            CountDownLatch latch = new CountDownLatch(size);
            for (int i = 0; i < size; i++) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String filePathName = deque.poll();
                            boolean result =  gongzhongnhaoUserRead.multiThreadAsyncRead(Thread.currentThread(), filePathName);
                            resultQueue.offer(result);
                        }finally {
                            latch.countDown();
                        }
                    }
                });
            }

            boolean await = false;
            try {
                await = latch.await(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                System.out.println("出现异常-读取时间超时");
                gongzhongnhaoUserRead.fail("" , new Exception("读取出现线程中断"));
                return;
            }

            if (await) {
                boolean isSuccess = true;
                System.out.println("result: " + resultQueue);
                while (!resultQueue.isEmpty()) {
                    Boolean result = resultQueue.poll();
                    if (!result) {
                        isSuccess = false;
                        break;
                    }
                }

                if (isSuccess) {
                    gongzhongnhaoUserRead.success();
                }else {
                    gongzhongnhaoUserRead.fail("多线程读取文件失败", new Exception("多线程读取文件失败"));
                }

            } else {
                gongzhongnhaoUserRead.fail("读取时间超时", new Exception("读取时间超时"));
            }
        }
    }

    /**
     * gongzhonghao_user 表用户读取
     */
    interface GongzhongnhaoUserReadInterface {

        /**
         * 多线程异步执行
         *
         * 注意自己捕获异常
         * @param filePath
         * @return
         */
        boolean multiThreadAsyncRead(Thread thread, String filePath);

        /**
         * 读取成功
         */
        void success();

        /**
         * 读取失败
         */
        void fail(String msg, Exception e);
    }
}
