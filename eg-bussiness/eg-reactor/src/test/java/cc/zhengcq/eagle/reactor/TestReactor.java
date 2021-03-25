package cc.zhengcq.eagle.reactor;

import cc.zhengcq.eagle.reactor.api.ITestFuncInteface;
import cc.zhengcq.eagle.reactor.model.City;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.math.BigInteger;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;

/**
 * @ClassName: TestReactor
 * @Description: todo
 * @Company: 广州市两棵树网络科技有限公司
 * @Author: zhengcq
 * @Date: 2021/1/22
 */
public class TestReactor {
    private static final Logger logger = LoggerFactory.getLogger(TestReactor.class);
    @Test
    public void disposable() throws InterruptedException {

        Flux<Long> longFlux = Flux.interval(Duration.ofMillis(1));
        //take方法准确获取订阅数据量
        Disposable disposable = longFlux.take(50).subscribe(x->logger.info("->{}",x));
        //不能停止正在推送数据中的Flux或Mono流
        Thread.sleep(100);
        //彻底停止正在推送数据中的Flux或Mono流
        disposable.dispose();
        logger.info("->Stop");

    }

    @Test
    public void testTreeSet() {
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(3);
        treeSet.add(1);
        treeSet.add(6);
        treeSet.add(2);
        for (Integer i : treeSet) {
            System.out.println(i);
        }
        System.out.println(treeSet.ceiling(5));
        System.out.println(treeSet.floor(5));
        System.out.println(treeSet.pollFirst());
        for (Integer i : treeSet) {
            System.out.println(i);
        }
        Set<Integer> tmpSet = new HashSet<>();
        tmpSet.add(3);
        tmpSet.add(1);
        tmpSet.add(23);
        tmpSet.add(11);
        tmpSet.add(6);
        tmpSet.add(2);
        tmpSet.add(26);
        tmpSet.add(32);
        for (Integer i : tmpSet) {
            System.out.println(i);
        }
        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.put("123", "123");
        treeMap.put("234", "234");
        treeMap.put("223", "34");
        treeMap.put("2f", "123");
        for (String i : treeMap.keySet()) {
            System.out.println(i);
        }
    }


    @Test
    public void testQsort() {
        int[] nums = {3, 9, 1, 2, 6, 7, 8, 4, 15, 12, 11};
        qsort(nums, 0, nums.length -1);
        for (int num : nums) {
            System.out.println(num);
        }
    }

    public void qsort(int[] array, int l, int r) {
        if (l >= r) {
            return;
        }
        int i = l;
        int j = r;
        int key = array[i];
        while (i < j) {
            while (i < j && array[j] >= key) j--;
            array[i] = array[j];
            while (i < j && array[i] <= key) i++;
            array[j] = array[i];
        }
        array[i] = key;
        qsort(array, l, i - 1);
        qsort(array, i + 1, r);
        return;
    }

    @Test
    public void testTrans() {
        City city = new City();
        city.setName("gz");
        city.setNick("g");
        System.out.println(JSON.toJSONString(city));
        System.out.println(toJSONStringIgnoreTransient(city));
    }

    public static String toJSONStringIgnoreTransient(Object object) {
        SerializeWriter out = new SerializeWriter();
        try {
            JSONSerializer serializer = new JSONSerializer(out);
            serializer.config(SerializerFeature.SkipTransientField, false);
            serializer.write(object);
            return out.toString();
        } finally {
            out.close();
        }
    }

    @Test
    public void testFuncInterface() {
        testFunc1(City::new);
        City city = new City();
        System.out.println(city);
        String str = null;
        Optional<String> opt = Optional.ofNullable(str);
        System.out.println(opt.map(s -> str + "world").orElse("hahah"));

    }

    public static void testFunc1(ITestFuncInteface iTestFuncInteface) {
        System.out.println(iTestFuncInteface.testGet());
    }

    @Test
    public void testInput() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            System.out.println(scanner.next());
        }
    }



    public static int timeCheck(int[] a,int step){
        int time=1;//最少也要一分钟
        int sum=0;//前k步步数总和
        for(int i:a){
            if(sum+i>step){//前k步步数和大于step(最大步数)
                sum=i;
                time+=1;
            }else{
                sum+=i;
            }
        }
        return time;
    }
    @Test
    public void testLinshi() throws IOException {
        Scanner bf=new Scanner(System.in);
        int max=0;
        int sum=0;
        int steps=0;
        int times=0;
        int n=bf.nextInt();
        int m=bf.nextInt();
        int a[]=new int[n];
        for(int i=0;i<n;i++){
            a[i]=bf.nextInt();
            max=Math.max(max,a[i]);
            sum+=a[i];
        }
        while(max<sum){
            steps=max+(sum-max)/2;
            times=timeCheck(a,steps);
            System.out.println(steps + " " + times + " " + sum);
            if(times>m){//花费时间大于规定时间 m，故最大步数要增大
                max=steps+1;
            }else if(times<m){//花费时间小于规定时间 m，故最大步数要减少
                sum=steps-1;
            }else{
                sum=steps;
            }
        }
        System.out.println(max);
        bf.close();
    }

    @Test
    public void testLinshi1() throws IOException {
        Scanner bf=new Scanner(System.in);
        int n = bf.nextInt();
        int m = bf.nextInt();
        int[] nums = new int[n];
        int max = -1;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            nums[i] = bf.nextInt();
            max = Math.max(max, nums[i]);
            sum += nums[i];
        }
        int l = max;
        int r = sum;
        while (l < r) {
            int mid = (l + r) >> 1;
            int count = 1;
            int tmpSum = 0;
            for (int i = 0; i < n; i++) {
                if ((tmpSum + nums[i]) > mid) {
                    tmpSum = nums[i];
                    count++;
                } else {
                    tmpSum += nums[i];
                }
            }
            if (count < m) {
                r = mid - 1;
            } else if (count > m) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        System.out.println(l);

    }

    @Test
    public void testOffice() throws IOException {
        Scanner bf=new Scanner(System.in);
        int x=bf.nextInt();
        int y=bf.nextInt();
        int n=bf.nextInt();
        int[][] dp = new int[30+1][30+1];
        for (int i = 0; i < n; i++) {
            int x1=bf.nextInt();
            int y1=bf.nextInt();
            dp[x1][y1] = -1;
        }
        for (int i = 0; i <= x; i++) dp[i][0] = 1;
        for (int i = 0; i <= y; i++) dp[0][i] = 1;
        for (int i = 1; i <= x; i++) {
            for (int j = 1; j <= y; j++) {
                if (dp[i][j] == -1) continue;
                if (dp[i - 1][j] != -1) dp[i][j] += dp[i - 1][j];
                if (dp[i][j - 1] != -1) dp[i][j] += dp[i][j - 1];
            }
        }
        System.out.println(dp[x][y]);
    }






    @Test
    public  void testLinux() {
        Scanner scanner = new Scanner(System.in);
        //通配字符串
        String pattern = scanner.nextLine();
        //要匹配查找的字符串
        String target = scanner.nextLine();
        char[] patternChar = pattern.toCharArray();
        char[] targetChar = target.toCharArray();
        int count = 0;
        for(int i=0; i<targetChar.length; i++) {
            if (patternChar[0] != '*' && patternChar[0] != targetChar[i]) continue;
            for(int j=i; j<targetChar.length; j++) {
                if (patternChar[patternChar.length - 1] != '*' && patternChar[patternChar.length - 1] != targetChar[j]) continue;
                if(match(patternChar, targetChar, 0, i, j)) {
                    System.out.println(i+" "+(j-i+1));
                    count++;
                }
            }
        }
        if (count == 0) {
            System.out.println("-1 0");
        }
        System.out.println(cnt);
    }

    @Test
    public  void testLinux1() {
        Scanner scanner = new Scanner(System.in);
        String matchStr = scanner.nextLine();
        String targetStr = scanner.nextLine();
        char[] matchChars = matchStr.toCharArray();
        char[] targetChars = targetStr.toCharArray();
        int count = 0;
        for (int i = 0; i < targetChars.length; i++) {
            if (matchChars[0] != '*' && matchChars[0] != targetChars[i]) continue;
            for (int j = i; j < targetChars.length; j++) {
                if (matchChars[matchChars.length -1] != '*' && matchChars[matchChars.length - 1] != targetChars[j]) continue;
                if (match1(matchChars, targetChars, 0, i, j)) {
                    System.out.println(i + " "+ (j - i + 1));
                    count++;
                }
            }
        }
        if (count == 0) {
            System.out.println("-1 0");
        }
        System.out.println(cnt);
    }

    public static int cnt = 0;
    //匹配target[left,...,right]，匹配到模式串的第i位
    private static boolean match(char[] pattern, char[] target, int i, int left, int right) {
        cnt++;
        // TODO Auto-generated method stub
        //刚好匹配完
        if (i==pattern.length && left>right) {
            return true;
        }
        //target已经匹配完，但是pattern还没到结尾，且pattern的后一位还不是*，没法看成空字符串
        if (left>right && i!=pattern.length && pattern[i]!='*') {
            return false;
        }
        //已经到了模式串的最后一位，但是target还没匹配完
        if (i==pattern.length && left<=right) {
            return false;
        }
        //当通配字符是*时，通配字符向后移动/查找字符向后移动
        else if (pattern[i]=='*') {
            //匹配到最后一个字符，pattern后面还有*，那么将*看成空字符，pattern往后移一位匹配
            if (left>right) {
                return match(pattern, target, i+1, left, right);
            }
            //匹配过程中遇到*，那么如果*看成空字串，则pattern后移一位　　　　　　　//如果*看成多个字符，首先*与当前字符匹配，然后就开始与target的下一个字符匹配，那么target向后移一位
            else {
                return match(pattern, target, i, left+1, right) ||
                        match(pattern, target, i+1, left, right);
            }
        }
        //当前模式串是字符，且与目标串匹配，则后移匹配下一字符
        else if(left<=right && pattern[i]==target[left]){
            return match(pattern, target, i+1, left+1, right);
        }
        //匹配中出现了不同的字符，匹配失败
        else if(left<=right && pattern[i]!=target[left]){
            return false;
        }
        else {
            return false;
        }
    }

    private static boolean match1(char[] pattern, char[] target, int i, int left, int right) {
        cnt++;
        // 刚好匹配完
        if (i == pattern.length && left > right) {
            return true;
        }
        // 匹配完字符串， 模式刚好到最后以为，且为*
        if (left > right  && pattern[i] != '*') {
            return false;
        }
        if (i == pattern.length) {
            return false;
        }
        if (pattern[i] == '*') {
            if (left > right) {
                return match1(pattern, target, i + 1, left, right);
            } else {
                return match1(pattern, target, i + 1, left, right)
                            || match1(pattern, target, i, left + 1, right);
            }
        } else if (left <= right && pattern[i] == target[left]) {
            return match1(pattern, target, i + 1, left + 1, right);
        } else if (left <= right && pattern[i] != target[left]) {
            return false;
        }
        return false;
    }

    @Test
    public void testLogic() {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        int[][] net = new int[num][num];
        List<Point> points = new ArrayList<>();
        int minX = num + 1;
        int maxX = -1;
        int minY = num + 1;
        int maxY = -1;
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                net[i][j] = scanner.nextInt();
                if (net[i][j] == 1) {
                    Point point = new Point(i, j);
                    points.add(point);
                    minX = Math.min(minX, i);
                    maxX = Math.max(maxX, i);
                    minY = Math.min(minY, j);
                    maxY = Math.max(maxY, j);
                }
            }
        }
        int min = Integer.MAX_VALUE;
        for (int i = Math.max(0, minX); i < Math.min(maxX + 1, num); i++) {
            for (int j = Math.max(0, minY); j < Math.min(maxY + 1, num); j++) {
                if (net[i][j] == 0) {
                    int tmp = net[i][j];
                    int tmpCnt = 0;
                    for (Point point : points) {
                        tmpCnt += Math.abs(point.x - i) + Math.abs(point.y - j);
                    }
                    min = Math.min(min, tmpCnt);
                }
            }
        }
        if (min == Integer.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(min);
        }

    }

    public static class Point {
        public int x;
        public int y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class ListNode {
      public int val;
      public ListNode next;
      ListNode(int x) {
          val = x;
          next = null;
      }
     }

    public static boolean hasCycle(ListNode head) {
        HashSet<ListNode> set = new HashSet<>();
        while (head != null) {
            if (set.contains(head)) {
                return true;
            }
            set.add(head);
            head = head.next;
        }
        return false;
    }

    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);

    }

    public int pop() {
        if (stack2.isEmpty()) {
            while (!stack1.empty()) {
                stack2.push(stack1.pop());
            }
            return stack2.pop();
        }
        return stack2.pop();
    }

    @Test
    public void testBigNum() {
        String str = "234234345345345345345345345345345345345";
        String str1 = "34653453453453453453456456456456456456456456";
        System.out.println(solveBigNum(str, str1));
        BigInteger bi1 = new BigInteger(str);
        BigInteger bi2 = new BigInteger(str1);
        System.out.println(bi1.add(bi2).toString());
    }

    public String solveBigNum(String s, String t) {
        Stack<Integer> stack = new Stack<>();
        StringBuilder stringBuilder = new StringBuilder();
        int i = s.length() - 1, j = t.length() - 1, carry = 0;
        while (i >= 0 || j >= 0 || carry != 0) {
            carry += i >= 0 ? s.charAt(i--) - '0' : 0;
            carry += j >= 0 ? t.charAt(j--) - '0' : 0;
            stack.push(carry % 10);
            carry = carry / 10;
        }
        while (!stack.isEmpty())
            stringBuilder.append(stack.pop());
        return stringBuilder.toString();
    }


    @Test
    public void testIntStr() {
        String str = "   010";
        str = str.replaceFirst("^[+ \t0]*", "");
        System.out.println(Integer.valueOf(str));
    }

    @Test
    public void testMaxLength() {
       int[] arr = {2,2,3,4,3,5,7};
        System.out.print(maxLength(arr));
        System.out.println("");
        System.out.print(maxLength1(arr));
    }

    public static int maxLength (int[] arr) {
        // write code here
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return 1;
        }
        Map<Integer, Integer> map = new HashMap<>();
        int j = 0;
        int max = -1;
        for (int i = 0; i < arr.length; i++) {
            if (map.containsKey(arr[i])) {
               j = Math.max(j, map.get(arr[i]) + 1);
            }
            max = Math.max(max, i - j + 1);
            map.put(arr[i], i);
        }
        return max;
    }

    public static int maxLength1 (int[] arr) {
        HashMap<Integer,Integer> map = new HashMap<>();
        int max = 1;
        for(int start = 0, end = 0; end<arr.length ; end++){
            if(map.containsKey(arr[end])){
                //重复了
                start = Math.max(start, map.get(arr[end])+1);
                //注意：这里一定要取最大的start，不然就错误了
                //为什么？ 因为重复数字的索引很可能比start小
            }
            max = Math.max(max , end-start+1);
            map.put(arr[end],end);
        }
        return max;
    }

    @Test
    public void testMidNum() {
        int[] arr1 = {1, 3, 5, 7, 9, 10, 11};
        int[] arr2 = {2, 4, 6, 8, 10};
        System.out.println(findMedianSortedArrays1(arr1, arr2));
    }

    public double findMedianSortedArrays(int[] A, int[] B) {
        int m = A.length;
        int n = B.length;
        if (m > n) { // to ensure m<=n
            int[] temp = A; A = B; B = temp;
            int tmp = m; m = n; n = tmp;
        }
        int iMin = 0, iMax = m, halfLen = (m + n + 1) / 2;
        while (iMin <= iMax) {
            int i = (iMin + iMax) / 2;
            int j = halfLen - i;
            if (i < iMax && B[j-1] > A[i]){
                iMin = i + 1; // i is too small
            }
            else if (i > iMin && A[i-1] > B[j]) {
                iMax = i - 1; // i is too big
            }
            else { // i is perfect
                int maxLeft = 0;
                if (i == 0) { maxLeft = B[j-1]; }
                else if (j == 0) { maxLeft = A[i-1]; }
                else { maxLeft = Math.max(A[i-1], B[j-1]); }
                if ( (m + n) % 2 == 1 ) { return maxLeft; }

                int minRight = 0;
                if (i == m) { minRight = B[j]; }
                else if (j == n) { minRight = A[i]; }
                else { minRight = Math.min(B[j], A[i]); }

                return (maxLeft + minRight) / 2.0;
            }
        }
        return 0.0;
    }


    public double findMedianSortedArrays1(int[] A, int[] B) {
        int m = A.length;
        int n = B.length;
        int halfLen = (m + n + 1) >> 1;
        int iMin = 0;
        int iMax = m;
        int i, j;
        while (iMin <= iMax) {
            i = (iMin + iMax) >> 1;
            j = halfLen - i;
            if (i < iMax && B[j - 1] > A[i]) {
                iMin = i + 1;
            } else if ( i > iMin && A[i - 1] > B[j]) {
                iMax = i - 1;
            } else {
                int maxLeft = 0;
                if (i == 0) maxLeft = B[j - 1];
                else if (j == 0)  maxLeft = A[i - 1];
                else maxLeft = Math.max(A[i - 1], B[j - 1]);
                if ((m + n) % 2 == 1) return maxLeft;
                int minRight = 0;
                if (i == m) minRight = B[j];
                else if (j == n) minRight = A[i];
                else minRight = Math.min(A[i], B[j]);

                return (maxLeft + minRight) / 2.0;
            }
        }

        return 0.0;
    }


    @Test
    public void testCallable() {
        Long start = System.currentTimeMillis();
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        MyThread2 myThread2 = new MyThread2();
        FutureTask<Integer> futureTask = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(10000);
                return 3;
            }
        });
        FutureTask<Integer> futureTask1 = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(10000);
                return 4;
            }
        });
        FutureTask<Integer> futureTask2 = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(10000);
                return 5;
            }
        });
        Thread thread = new Thread(futureTask, "线程名：有返回值的线程2");
        thread.start();
        Thread thread1 = new Thread(futureTask1, "线程名：有返回值的线程2");
        thread1.start();
        Thread thread2 = new Thread(futureTask2, "线程名：有返回值的线程2");
        thread2.start();
        try {
            Integer rs = futureTask.get();
            Integer rs1 = futureTask1.get();
            Integer rs2 = futureTask2.get();
            System.out.println(rs + rs1 + rs2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Long end = System.currentTimeMillis();
        System.out.println((end - start));

        start = System.currentTimeMillis();
        FutureTask<Integer> futureTask3 = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(10000);
                return 3;
            }
        });
        FutureTask<Integer> futureTask4 = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(10000);
                return 4;
            }
        });
        FutureTask<Integer> futureTask5 = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(10000);
                return 5;
            }
        });
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.submit(futureTask3);
        executorService.submit(futureTask4);
        executorService.submit(futureTask5);
        Future<Integer> future6 = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(10000);
                return 6;
            }
        });
        try {
            Integer rs = futureTask3.get();
            Integer rs1 = futureTask4.get();
            Integer rs2 = futureTask5.get();
            Integer rs3 = future6.get();
            System.out.println(rs + rs1 + rs2 + rs3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        end = System.currentTimeMillis();
        System.out.println((end - start));
    }

    @Test
    public void testStaticCode() {
        System.out.println("test");
        System.out.println(City.test1());
        System.out.println(City.getInstance());
        System.out.println(City.TEST_STR);
//        System.out.println(City.getInstance1());

    }

    @Test
    public void testArr1() {
        int[][] matrix = {{1,3,7,9,10},{2,4,8,10,11}, {4,5,12,13,15}, {6, 9, 14, 17, 19}, {11, 15, 19, 20, 32}};
        //
    }

    public int findMatrixNum(int[][] matrix, int x1, int x2, int y1, int y2, int num) {
        if (x1 == x2 ) {
            if (num == matrix[x1][y1]) {
                System.out.println(x1 + " " + y1);
                return num;
            } else {
                return -1;
            }
        }
        int iMin = x1;
        int jMin = y1;
        int iMax = x2;
        int jMax = y2;
        int iMid, jMid;
        while (iMin <= iMax && jMin <= jMax) {
            iMid = (x1 + x2) >> 1;
            jMid = (y1 + y2) >> 1;
            if (matrix[iMid][jMid] == num) {
                System.out.println(iMid + " " + jMid);
                return num;
            }
        }
        return -1;
    }

    @Test
    public void testGc() {
        if (true) {
            byte[] placeHolder = new byte[64 * 1024 * 1024];
            System.out.println(placeHolder.length / 1024);
//            placeHolder = null;
        }
//        int a = 1;
//        City city = new City();
        System.gc();
    }

    @Test
    public void testBSort() {
        int[] arr = {1, 3, 7, 9, 11, 19, 21, 31, 34};
        int[] arr1 = {3, 5, 7};
        System.out.println(bSort(arr1, 0, arr1.length - 1, 6));
    }

    public int bSort(int[] arr, int l, int r, int num) {
        if (l > r) {
            return -1;
        }
        if (l == r) {
            if (arr[l] == num) {
                return l;
            } else {
                return -1;
            }
        }
        int mid;
        while (l <= r) {
            mid = (l + r) >> 1;
            if (arr[mid] == num) {
                return mid;
            } else if (arr[mid] > num) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        System.out.println(l + " " + r);
        return  -1;
    }

    @Test
    public void testKMP() {
        int[] rs = getNext("abcabd");
        for (int tmp : rs) {
            System.out.println(tmp);
        }
    }
    public static int[] getNext(String ps) {

        char[] p = ps.toCharArray();
        int[] next = new int[p.length];
        next[0] = -1;
        int j = 0;
        int k = -1;
        while (j < p.length - 1) {
            if (k == -1 || p[j] == p[k]) {
                next[++j] = ++k;
            } else {
                k = next[k];
            }
        }
        return next;
    }


    @Test
    public void testZSHU() {

        int x = 2;
        int y = 100;
        double m = Math.log10(y);
    }

    @Test
    public void testMap() {
        Map<Integer, Integer> map = new HashMap<>(50000000);
        for (int i = 0; i < 100000000; i++) {
            map.put(i, i);
        }
        long startTime = System.currentTimeMillis();

        for (int i = 1; i < 1000; i++) {
            map.get(i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime));
    }
}
