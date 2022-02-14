##Results

| Benchmark                                        | Mode | Cnt | Score   | Error   | Units |
|--------------------------------------------------|------|-----|---------|---------|-------|
| LambdaBenchmark.testLambda                       | avgt | 5   | 723.322 | 137.002 | ns/op |
| LambdaBenchmark.testLocalLambdaCapturingField    | avgt | 5   | 966.169 | 75.359  | ns/op |
| LambdaBenchmark.testLocalLambdaCapturingVariable | avgt | 5   | 782.056 | 75.814  | ns/op |
| LambdaBenchmark.testMethodReference              | avgt | 5   | 642.433 | 77.998  | ns/op | 
| LambdaBenchmark.testStaticLambda                 | avgt | 5   | 821.021 | 83.398  | ns/op |     
| LambdaBenchmark.testStaticLambdaCapturing        | avgt | 5   | 981.835 | 75.461  | ns/op |
| LambdaBenchmark.testStaticMethodReference        | avgt | 5   | 812.685 | 72.690  | ns/op |

### Parameters

* JMH version: 1.34
* VM version: JDK 11.0.13, OpenJDK 64-Bit Server VM, 11.0.13+8
* Warmup: 5 iterations, 10 s each
* Measurement: 5 iterations, 10 s each
* Timeout: 10 min per iteration
* Threads: 1 thread, will synchronize iterations

######Disclaimer
_REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on why the numbers are the way they
are. Use profilers (see -prof, -lprof), design factorial experiments, perform baseline and negative tests that provide
experimental control, make sure the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
Do not assume the numbers tell you what you want them to tell._