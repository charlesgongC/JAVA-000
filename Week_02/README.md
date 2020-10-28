## 各GC模式性能分析
### 串行GC
>java -XX:+UseSerialGC -Xmx256m -Xms256m -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis

共发生12次GC，9次Young GC，3次Full GC

![](https://github.com/charlesgongC/JAVA-000/tree/main/Week_02/images/串行GC.png)

细节如下：

| GC类型 |Young区内存变化(KB)  |Old区内存变化(KB)| 耗时(ms) |
| --- | --- | --- |---|
| Young GC | -60884 | +14867 |17|
| Young GC | -69859 | +24484 |38|
| Young GC | -69336 | +25783 |35|
| --- | --- | --- |---|
| Full GC | -59146 | -235 |50|
| Full GC | -59798 | -126 |78|
| Full GC | -38341 | -17  |21|

分析：
+ 1.Young GC 时新生代带减少，老年代变多
+ 2.Full GC 时，老年代内存轻微减少，效率不高

### 并行GC
>java -XX:+UseParallelGC -Xmx256m -Xms256m -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis

![Image](https://github.com/charlesgongC/JAVA-000/tree/main/Week_02/images/并行GC1.png)
![Image](https://github.com/charlesgongC/JAVA-000/tree/main/Week_02/images/并行GC2.png)

共发生12次GC，10次Young GC，2次Full GC
细节如下：

| GC类型 |Young区内存变化(KB)  |Old区内存变化(KB)| 耗时(ms) |
| --- | --- | --- |---|
| Young GC | -54798 | +11580 |11|
| Young GC | -64957 | +19926 |47|
| Young GC | -38646 | +17629 |79|
| --- | --- | --- |---|
| Full GC | -19640 | -9554  |89|
| Full GC | -29696 | -9105  |55|
分析：
+ 1.Young GC 时效率和串行GC相近
+ 2.Full GC 时，年轻代被清空，老年代的释放也大于串行GC
 
### CMS GC
>java -XX:+UseConcMarkSweepGC -Xmx256m -Xms256m -XX:+PrintGCDetails -XX:+PrintGCDateS`****`tamps GCLogAnalysis

共发生11次GC，9次Young GC，2次Full GC
细节如下：

| GC类型 |Young区内存变化(KB)  |Old区内存变化(KB)| 耗时(ms) |
| --- | --- | --- |---|
| Young GC | -61249 | +17351 |21|
| Young GC | -69951 | +19135 |45|
| Young GC | 0 | 0 |0|
| --- | --- | --- |---|
| Full GC | 8848 | 126912  |0.2+6|
| Full GC | 8894 | 163615  |0.2+2|
分析：
+ 1.Young GC 时效率和并行GC相近
+ 2.Full GC,耗时大幅度下降，时间为第一阶段和第四阶段之和

### G1 GC
>java -XX:+UseG1GC -Xmx256m -Xms256m -XX:+PrintGC -XX:+PrintGCDateStamps GCLogAnalysis

16次Young GC，10次mixed GC,1次Full GC

分析：
+ young模式回收效率很高，耗时都在30ms内，回收15M左右内存
+ mixed模式耗时更短，
+ cleanup 失败的原因是应该刚好被young回收过，所以没有可回收的内存吗？

![](https://github.com/charlesgongC/JAVA-000/blob/main/Week_02/images/G1-2.png)
+ Full GC 效率高于 young和mixed，但耗时也高许多

## 压测演练 (wrk)






