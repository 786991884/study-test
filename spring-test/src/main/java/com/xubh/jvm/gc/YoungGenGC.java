package com.xubh.jvm.gc;

public class YoungGenGC {
    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        //testAllocation();  
        //testHandlePromotion();  
        //testPretenureSizeThreshold();  
        testTenuringThreshold();
        // testTenuringThreshold2();  
    }

    /**
     * 测试年轻代回收
     * <p/>
     * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     * <p/>
     * [GC [DefNew: 6487K->147K(9216K), 0.0039095 secs] 6487K->6291K(19456K), 0.0039393 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     * Heap
     * def new generation   total 9216K, used 4407K [0x315e0000, 0x31fe0000, 0x31fe0000)
     * eden space 8192K,  52% used [0x315e0000, 0x31a08fd8, 0x31de0000)
     * from space 1024K,  14% used [0x31ee0000, 0x31f04e08, 0x31fe0000)
     * to   space 1024K,   0% used [0x31de0000, 0x31de0000, 0x31ee0000)
     * tenured generation   total 10240K, used 6144K [0x31fe0000, 0x329e0000, 0x329e0000)
     * the space 10240K,  60% used [0x31fe0000, 0x325e0030, 0x325e0200, 0x329e0000)
     * compacting perm gen  total 12288K, used 369K [0x329e0000, 0x335e0000, 0x369e0000)
     * the space 12288K,   3% used [0x329e0000, 0x32a3c458, 0x32a3c600, 0x335e0000)
     * ro space 10240K,  54% used [0x369e0000, 0x36f5c0f0, 0x36f5c200, 0x373e0000)
     * rw space 12288K,  55% used [0x373e0000, 0x37a7fb80, 0x37a7fc00, 0x37fe0000)
     */
    @SuppressWarnings("unused")
    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];  // 出现一次Minor GC  
    }

    /**
     * 对象直接进入年老代
     * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     * -XX:PretenureSizeThreshold=3145728
     * <p/>
     * Heap
     * def new generation   total 9216K, used 671K [0x029d0000, 0x033d0000, 0x033d0000)
     * eden space 8192K,   8% used [0x029d0000, 0x02a77e70, 0x031d0000)
     * from space 1024K,   0% used [0x031d0000, 0x031d0000, 0x032d0000)
     * to   space 1024K,   0% used [0x032d0000, 0x032d0000, 0x033d0000)
     * tenured generation   total 10240K, used 4096K [0x033d0000, 0x03dd0000, 0x03dd0000)
     * the space 10240K,  40% used [0x033d0000, 0x037d0010, 0x037d0200, 0x03dd0000)
     * compacting perm gen  total 12288K, used 2107K [0x03dd0000, 0x049d0000, 0x07dd0000)
     * the space 12288K,  17% used [0x03dd0000, 0x03fdefc8, 0x03fdf000, 0x049d0000)
     * No shared spaces configured.
     */
    @SuppressWarnings("unused")
    public static void testPretenureSizeThreshold() {
        byte[] allocation;
        allocation = new byte[4 * _1MB];  //直接分配在老年代中  
    }

    /**
     * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1
     * -XX:+PrintTenuringDistribution
     */
    @SuppressWarnings("unused")
    public static void testTenuringThreshold() {
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[_1MB / 4];  // 什么时候进入老年代决定于XX:MaxTenuringThreshold设置  
        allocation2 = new byte[4 * _1MB];
        allocation3 = new byte[4 * _1MB];
        allocation3 = null;
        allocation3 = new byte[4 * _1MB];
    }

    /**
     * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15
     * -XX:+PrintTenuringDistribution
     */
    @SuppressWarnings("unused")
    public static void testTenuringThreshold2() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[_1MB / 4];   // allocation1+allocation2大于survivo空间一半  
        allocation2 = new byte[_1MB / 4];
        allocation3 = new byte[4 * _1MB];
        allocation4 = new byte[4 * _1MB];
        allocation4 = null;
        allocation4 = new byte[4 * _1MB];
    }

    /**
     * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:-HandlePromotionFailure
     */
    @SuppressWarnings("unused")
    public static void testHandlePromotion() {
        byte[] allocation1, allocation2, allocation3, allocation4, allocation5, allocation6, allocation7;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation1 = null;
        allocation4 = new byte[2 * _1MB];
        allocation5 = new byte[2 * _1MB];
        allocation6 = new byte[2 * _1MB];
        allocation4 = null;
        allocation5 = null;
        allocation6 = null;
        allocation7 = new byte[2 * _1MB];
    }

}
