package com.jack.applicationInit;

import com.jack.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;


/**
 * @author jack xu
 */
public class ProcessorHandler implements Runnable {

    private Socket socket;

    public ProcessorHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            //反序列化
            RpcRequest request = (RpcRequest) inputStream.readObject();
            //根据传过来的参数执行方法
            System.out.println("request :" + request);
            Object result = processor(request);
            System.out.println("response :" + result);
            //将计算结果写入输出流
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(result);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭流
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Object processor(RpcRequest request) {
        try {
            Map<String, BeanInfo> map = Mediator.getInstance().getMap();
            //接口名.方法名
            String key = request.getClassName() + "." + request.getMethodName();
            //取出方法
            BeanInfo beanInfo = map.get(key);
            if (beanInfo == null) {
                return null;
            }
            //bean对象
            Object bean = beanInfo.getBean();
            //方法
            Method method = beanInfo.getMethod();
            //反射
            return method.invoke(bean, request.getArgs());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
