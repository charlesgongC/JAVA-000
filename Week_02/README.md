## 各GC模式性能分析
### 串行GC
>java -XX:+UseSerialGC -Xmx256m -Xms256m -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis

<img src="https://github.com/charlesgongC/JAVA-000/blob/main/Week_02/images/串行GC.png" width = "800"  alt="串行GC" />
共发生12次GC，9次Young GC，3次Full GC

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

<img src="https://github.com/charlesgongC/JAVA-000/blob/main/Week_02/images/并行GC1.png" width = "800"  alt="并行GC" />
<img src="https://github.com/charlesgongC/JAVA-000/blob/main/Week_02/images/并行GC2.png" width = "800"  alt="并行GC" />

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

<img src="https://github.com/charlesgongC/JAVA-000/blob/main/Week_02/images/CMSGC1.png" width = "800"  alt="CMSGC1" />
<img src="https://github.com/charlesgongC/JAVA-000/blob/main/Week_02/images/CMSGC2.png" width = "800"  alt="CMSGC2" />

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

<img src="https://github.com/charlesgongC/JAVA-000/blob/main/Week_02/images/G1GC1.png" width = "800"  alt="G1GC1" />
<img src="https://github.com/charlesgongC/JAVA-000/blob/main/Week_02/images/G1GC2.png" width = "800"  alt="G1GC2" />

16次Young GC，10次mixed GC,1次Full GC

分析：
+ young模式回收效率很高，耗时都在30ms内，回收15M左右内存
+ mixed模式耗时更短，但好像是内存比较小的时候用young，比较高的时候用mixed
<img src="https://github.com/charlesgongC/JAVA-000/blob/main/Week_02/images/G1-1.png" width = "800"  alt="G1GC2" />
+ cleanup 失败的原因是应该刚好被young回收过，所以没有可回收的内存吗？
<img src="https://github.com/charlesgongC/JAVA-000/blob/main/Week_02/images/G1-2.png" width = "800"  alt="G1GC2" />
+ Full GC 效率高于 young和mixed，但耗时也高许多

## 压测演练 (wrk)
### SerialGC
> java -jar -XX:+UseSerialGC -Xms512m -Xms512m gateway-server-0.0.1-SNAPSHOT.jar

> 使用 4 核 40 线程 压测 30s
> wrk -t4 -c40 -d60s --latency http://localhost:8088/api/hello

```shell
wrk -t4 -c40 -d60s --latency http://localhost:8088/api/hello
Running 1m test @ http://localhost:8088/api/hello
  4 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     9.05ms   20.67ms 366.14ms   92.66%
    Req/Sec     2.58k     1.03k    7.22k    65.14%
  Latency Distribution
     50%    3.20ms
     75%    5.79ms
     90%   20.99ms
     99%   99.25ms
  614149 requests in 1.00m, 73.32MB read
Requests/sec:  10220.26
Transfer/sec:      1.22MB
```
分析：
- 每秒处理1W次请求,平均每秒读取数据1.2kb
- 99%延迟在100ms左右,性能比较差

> java -jar -XX:+UseSerialGC -Xms1024m -Xms1024m gateway-server-0.0.1-SNAPSHOT.jar
> 使用 4 核 40 线程 压测 30s
> wrk -t4 -c40 -d60s --latency http://localhost:8088/api/hello
```shell
 a@macxx  ~  wrk -t4 -c40 -d60s --latency http://localhost:8088/api/hello
Running 1m test @ http://localhost:8088/api/hello
  4 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    11.31ms   25.21ms 386.70ms   92.00%
    Req/Sec     2.22k     0.86k    5.82k    67.45%
  Latency Distribution
     50%    3.78ms
     75%    7.20ms
     90%   28.42ms
     99%  119.12ms
  528478 requests in 1.00m, 63.09MB read
Requests/sec:   8793.81
Transfer/sec:      1.05MB
```
分析：
- 性能反而下降了？？？是因为我在本机上可用内存不足1G了，所以性能反而下降了？
- 反复尝试多次-Xms1024m 性能均低于-Xms512m,希望助教老师解答。

### ParallelGC
> java -jar -XX:+UseParallelGC -Xms512m -Xms512m gateway-server-0.0.1-SNAPSHOT.jar

> 使用 4 核 40 线程 压测 30s
> wrk -t4 -c40 -d60s --latency http://localhost:8088/api/hello
```shell
 a@macxx  ~  wrk -t4 -c40 -d60s --latency http://localhost:8088/api/hello
Running 1m test @ http://localhost:8088/api/hello
  4 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    10.98ms   25.66ms 444.58ms   91.78%
    Req/Sec     2.63k     1.15k    6.14k    63.39%
  Latency Distribution
     50%    2.92ms
     75%    6.41ms
     90%   29.20ms
     99%  119.26ms
  626349 requests in 1.00m, 74.78MB read
Requests/sec:  10421.47
Transfer/sec:      1.24MB
```
分析：
- 请求处理量，和读取数据量略好于SerialGC，符合预期

### CMS GC
> java -jar -XX:+UseConcMarkSweepGC -Xms512m -Xms512m gateway-server-0.0.1-SNAPSHOT.jar

> 使用 4 核 40 线程 压测 30s
> wrk -t4 -c40 -d60s --latency http://localhost:8088/api/hello
```shell
 a@macxx  ~  wrk -t4 -c40 -d60s --latency http://localhost:8088/api/hello
Running 1m test @ http://localhost:8088/api/hello
  4 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     8.50ms   17.72ms 280.01ms   92.34%
    Req/Sec     2.52k     0.96k    5.62k    65.62%
  Latency Distribution
     50%    3.33ms
     75%    5.74ms
     90%   19.34ms
     99%   93.42ms
  601163 requests in 1.00m, 71.77MB read
Requests/sec:  10005.53
Transfer/sec:      1.19MB
```
分析：
- 低于串行GC

### G1 GC
> java -jar -XX:+UseG1GC -Xms512m -Xms512m gateway-server-0.0.1-SNAPSHOT.jar

> 使用 4 核 40 线程 压测 30s
> wrk -t4 -c40 -d60s --latency http://localhost:8088/api/hello
```shell
 a@macxx  ~  wrk -t4 -c40 -d60s --latency http://localhost:8088/api/hello
Running 1m test @ http://localhost:8088/api/hello
  4 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     9.19ms   20.60ms 450.83ms   91.99%
    Req/Sec     2.74k     1.20k    9.11k    62.73%
  Latency Distribution
     50%    2.87ms
     75%    5.83ms
     90%   23.28ms
     99%   99.03ms
  653567 requests in 1.00m, 78.03MB read
Requests/sec:  10874.88
Transfer/sec:      1.30MB
```
分析：
- 性能最好！

### 总结：
- 性能 G1>并行>串行>CMS,好像和预期太一样
- 理论上应该串行最低，并行最高，G1和CMS中间
- 串行适用于小项目，没吞吐量要求的
- 并行适用于吞吐量大，但对延迟要求不强的
- G1比较适合对延迟敏感的项目
- CMS在并行和G1中间，不上不下，估计会别淘汰掉









