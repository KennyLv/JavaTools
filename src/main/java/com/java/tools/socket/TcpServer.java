package com.java.tools.socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
//	public static final int PORT = 12345;// 监听的端口号
    
	public static final int TCP_TAX_PORT = 25817;//从机开票端口
//    public static final int MULTICAST_PORT = 35271;//主从机组播端口
//    public static final int GUEST_LISTENER_HOST_NAME_PORT = 27392;//从机监听主机地址端口
//    public static final int UDP_AUHT_PROT = 29317;//从机认证端口

    private String ServerName = "";
    private int port = 0;
	public static void main(String[] args) {
		System.out.println("服务器启动...\n");
//		SocketServer server2 = new SocketServer();
		new TcpServer().init("TCP_TAX_PORT", TCP_TAX_PORT);
//		new SocketServer().init("MULTICAST_PORT", MULTICAST_PORT);
//		new SocketServer().init("GUEST_LISTENER_HOST_NAME_PORT", GUEST_LISTENER_HOST_NAME_PORT);
//		new SocketServer().init("UDP_AUHT_PROT", UDP_AUHT_PROT);
//		new SocketServer().init("TCP_TAX_PORT", TCP_TAX_PORT);
	}

	public void init(String ServerName, int port) {
		this.ServerName = ServerName;
		this.port = port;
		try {
			ServerSocket serverSocket = new ServerSocket(25817);
			while (true) {
				// 一旦有堵塞, 则表示服务器与客户端获得了连接
				Socket client = serverSocket.accept();
				// 处理这次连接
				new HandlerThread(client);
			}
		} catch (Exception e) {
			System.out.println(ServerName + "服务器异常: " + e.getMessage());
		}
	}

	private class HandlerThread implements Runnable {
		private Socket socket;

		public HandlerThread(Socket client) {
			socket = client;
			new Thread(this).start();
		}

		public void run() {

			System.out.println(ServerName + "新链接进入\n");
			
			OutputStream out = null;
			DataInputStream input = null;
			
			try {
				// 向客户端回复信息
				out = new DataOutputStream(socket.getOutputStream());
				// 读取客户端数据
				input = new DataInputStream(socket.getInputStream());
				
				// 主动推送一个随机码
				if(port == TCP_TAX_PORT) {
					//Thread.sleep(3*1000);
					String response = "<?xml version=\"1.0\" encoding=\"utf-8\"?><action id=\"code\"><content>49e339fd</content></action>";
					out.write(response.getBytes());
					out.flush();
					System.out.println(ServerName + "连接返回");
				}
				while(true) {
					String clientInputStr = input.readUTF();// 这里要注意和客户端输出流的写方法对应,否则会抛 EOFException

					// 处理客户端数据
					System.out.println(ServerName + "客户端发过来的内容:" + clientInputStr);

					if(clientInputStr.contains("id=\"code\"")){
						if(clientInputStr.contains("49793239")) {
							String response = "<?xml version=\"1.0\" encoding=\"utf-8\"?><action id=\"code\"><content>ok</content></action>";
							out.write(response.getBytes());
							//out.writeUTF("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<action id=\"code\">\n	<content>ok</content>\n</action>\r\n");
							out.flush();
						}
						else {
							String response = "<?xml version=\"1.0\" encoding=\"utf-8\"?><action id=\"code\"><content>err</content></action>";
							out.write(response.getBytes());
							//out.writeUTF("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<action id=\"code\">\n	<content>err</content>\n</action>\r\n");
							out.flush();
						}
					}
					else if(clientInputStr.contains("id=\"shopping\"")){
						String response = "<?xml version=\"1.0\" encoding=\"utf-8\"?><action id=\"code\"><content>ok</content></action>";
						out.write(response.getBytes());
						//out.writeUTF("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<action id=\"code\">\n	<content>ok</content>\n</action>\r\n");
						out.flush();
					}
				}

			} catch (Exception e) {
				System.out.println(ServerName + "服务器 run 异常: " + e.getMessage());
			} finally {
				try {
					out.close();
					input.close();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				if (socket != null) {
					try {
						socket.close();
					} catch (Exception e) {
						socket = null;
						System.out.println("服务端 finally 异常:" + e.getMessage());
					}
				}
			}
		}
	}

//	/**
//	 * Socket服务端
//	 */
//	public static void main(String[] args) {
//		try {
//			ServerSocket serverSocket = new ServerSocket(25817);
//			System.out.println("服务端已启动，等待客户端连接..");
//			Socket socket = serverSocket.accept();// 侦听并接受到此套接字的连接,返回一个Socket对象
//
//			// 根据输入输出流和客户端连接
//			InputStream inputStream = socket.getInputStream();// 得到一个输入流，接收客户端传递的信息
//			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);// 提高效率，将自己字节流转为字符流
//			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);// 加入缓冲区
//			String temp = null;
//			String info = "";
//			while ((temp = bufferedReader.readLine()) != null) {
//				info += temp;
//				System.out.println("已接收到客户端连接");
//				System.out.println("服务端接收到客户端信息：" + info + ",当前客户端ip为：" + socket.getInetAddress().getHostAddress());
//			}
//
//			OutputStream outputStream = socket.getOutputStream();// 获取一个输出流，向服务端发送信息
//			PrintWriter printWriter = new PrintWriter(outputStream);// 将输出流包装成打印流
//			printWriter.print("<?xml version=\"1.0\" encoding=\"utf-8\"?><action id=\"code\"><content>24a05584</content></action>");
//			printWriter.flush();
//			socket.shutdownOutput();// 关闭输出流
//
//			// 关闭相对应的资源
//			printWriter.close();
//			outputStream.close();
//			bufferedReader.close();
//			inputStream.close();
//			socket.close();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	
	
	
}
