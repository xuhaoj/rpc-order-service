package com.jack.applicationInit;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//spring容器启动完成之后，会发布一个ContextRefreshedEvent
@Component
public class SocketServerInitial implements ApplicationListener<ContextRefreshedEvent> {
    //线程池
    private final ExecutorService executorService = new ThreadPoolExecutor(5, 10, 0L,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(10), Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //启动服务
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8888);
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(new ProcessorHandler(socket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭socket
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
