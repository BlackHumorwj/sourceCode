package com.example.okhttp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @time 2020/4/30 10:19
 * @desc
 */
public class OkhttpSource {

    /*
     1、 异步请求的两个队列如何切换的
     2、 Dispatcher 分发器类的作用
     3、 拦截器链的调用
     4、 线程池到底是怎么复用线程的
     5、Deque<RealCall>  双端队列是个什么结构
     */

    private OkHttpClient mClient;
    private Request mRequest;

    private void init() {
        mClient = new OkHttpClient.Builder().readTimeout(10, TimeUnit.MILLISECONDS).build();

        /*
        //配置 Client 的一些参数信息
         public Builder() {
            dispatcher = new Dispatcher();//分发器类
            protocols = DEFAULT_PROTOCOLS;
            connectionSpecs = DEFAULT_CONNECTION_SPECS;
            eventListenerFactory = EventListener.factory(EventListener.NONE);
            proxySelector = ProxySelector.getDefault();
            cookieJar = CookieJar.NO_COOKIES;
            socketFactory = SocketFactory.getDefault();
            hostnameVerifier = OkHostnameVerifier.INSTANCE;
            certificatePinner = CertificatePinner.DEFAULT;
            proxyAuthenticator = Authenticator.NONE;
            authenticator = Authenticator.NONE;
            connectionPool = new ConnectionPool();//连接池
            dns = Dns.SYSTEM;
            followSslRedirects = true;
            followRedirects = true;
            retryOnConnectionFailure = true;
            connectTimeout = 10_000;
            readTimeout = 10_000;
            writeTimeout = 10_000;
            pingInterval = 0;
        }

         */

        mRequest = new Request.Builder().get().url("www.hao123.com").build();

        /*
        //构建请求的一些参数
           Request(Builder builder) {
            this.url = builder.url;
            this.method = builder.method;
            this.headers = builder.headers.build();
            this.body = builder.body;
            this.tag = builder.tag != null ? builder.tag : this;
          }

         */

    }

    //同步请求
    private void sync() throws IOException {

        //返回一个请求的 RealCall 对象,RealCall中封装了一些请求和client信息
        final Call call = mClient.newCall(mRequest);

        //调用 RealCall 的 execute() 方法返回响应对象
        final Response execute = call.execute();

        /*
        ```

        RealCall#execute()

        @Override public Response execute() throws IOException {
            synchronized (this) {
            //
              if (executed) throw new IllegalStateException("Already Executed");
              executed = true;
            }
            captureCallStackTrace();
            eventListener.callStart(this);
            try {
              //Dispatcher 执行 executed()
              client.dispatcher().executed(this);

              //通过拦截器链返回 Response 对象
              Response result = getResponseWithInterceptorChain();
              if (result == null) throw new IOException("Canceled");
              return result;
            } catch (IOException e) {
              eventListener.callFailed(this, e);
              throw e;
            } finally {
            //分发器来finish 这个 RealCall 对象
              client.dispatcher().finished(this);
            }
         }

        ```

        ```
          Dispatcher # executed()
          synchronized void executed(RealCall call) {
            //添加进正在请求的 同步队列
            runningSyncCalls.add(call);
          }

        ```

        ```
         private <T> void finished(Deque<T> calls, T call, boolean promoteCalls) {
            int runningCallsCount;
            Runnable idleCallback;
            synchronized (this) {
             //队列中清除当前的call
              if (!calls.remove(call)) throw new AssertionError("Call wasn't in-flight!");

             //异步调用时执行此方法
              if (promoteCalls) promoteCalls();

              runningCallsCount = runningCallsCount();
              idleCallback = this.idleCallback;
            }

            if (runningCallsCount == 0 && idleCallback != null) {
              idleCallback.run();
            }
          }


           private void promoteCalls() {
                if (runningAsyncCalls.size() >= maxRequests) return; // Already running max capacity.
                if (readyAsyncCalls.isEmpty()) return; // No ready calls to promote.

                for (Iterator<AsyncCall> i = readyAsyncCalls.iterator(); i.hasNext(); ) {
                  AsyncCall call = i.next();

                  if (runningCallsForHost(call) < maxRequestsPerHost) {
                    i.remove();
                   //正在执行的异步请求队列添加等待执行的call对象
                    runningAsyncCalls.add(call);
                    //线程洗执行这个call对象
                    executorService().execute(call);
                  }

                  if (runningAsyncCalls.size() >= maxRequests) return; // Reached max capacity.
                }
          }




        ```



         */

    }

    //异步请求
    private void aSync() {
        final Call call = mClient.newCall(mRequest);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }


}
