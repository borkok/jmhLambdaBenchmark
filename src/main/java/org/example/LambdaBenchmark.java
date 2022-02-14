/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.example;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(value = Scope.Thread)
@Fork(1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class LambdaBenchmark {

	public static String staticAddOne(int a) {
		return "Hello " + a;
	}

	public static String staticSum(int a, int b) {
		return "Hello " + a + b;
	}

	private int number = 15;

	public String addOne(int a) {
		return "Bye " + a;
	}

	public String sum(int a, int b) {
		return "Bye " + a + b;
	}

	/**
	 * Let's measure lambda performance
	 * static method reference
	 * static lambda
	 * static lambda capturing local variable
	 * method reference
	 * lambda
	 * lambda capturing local variable
	 * lambda capturing local field
	 */
	@Benchmark
	public List<String> testStaticMethodReference() {
		return IntStream.range(0, 50)
				.boxed()
				.map(LambdaBenchmark::staticAddOne)
				.collect(Collectors.toList());
	}

	@Benchmark
	public List<String> testStaticLambda() {
		return IntStream.range(0, 50)
				.boxed()
				.map(a -> staticAddOne(a))
				.collect(Collectors.toList());
	}

	@Benchmark
	public List<String> testStaticLambdaCapturing() {
		int local = 5;
		return IntStream.range(0, 50)
				.boxed()
				.map(a -> staticSum(a, local))
				.collect(Collectors.toList());
	}

	@Benchmark
	public List<String> testMethodReference() {
		return IntStream.range(0, 50)
				.boxed()
				.map(this::addOne)
				.collect(Collectors.toList());
	}

	@Benchmark
	public List<String> testLambda() {
		return IntStream.range(0, 50)
				.boxed()
				.map(a -> addOne(a))
				.collect(Collectors.toList());
	}

	@Benchmark
	public List<String> testLocalLambdaCapturingVariable() {
		int local = 5;
		return IntStream.range(0, 50)
				.boxed()
				.map(a -> sum(a, local))
				.collect(Collectors.toList());
	}

	@Benchmark
	public List<String> testLocalLambdaCapturingField() {
		return IntStream.range(0, 50)
				.boxed()
				.map(a -> sum(a, number))
				.collect(Collectors.toList());
	}

	/**
	 * You can run this test via the command line:
	 * $ mvn clean install
	 * $ java -jar target/benchmarks.jar
	 * (we requested single fork; there are also other options, see -h)
	 */
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(LambdaBenchmark.class.getSimpleName())
				.forks(1)
				.build();

		new Runner(opt).run();
	}
}
