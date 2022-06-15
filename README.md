# P2Pconnect
using JAVA to realize P2P chat software
运行环境和说明文档
运行环境：操作系统为Windows10，编译器Eclipse 2022-03，JAVASE_1.8
软件说明：
本次课程设计的内容是实现一个P2P的聊天软件，即用户与用户的点对点通信，无需通过服务器中转消息文件。
架构如下：
 ![image](https://user-images.githubusercontent.com/76092270/173814551-2b8bebdf-6f92-4045-b7eb-58563d9558c0.png)

用户与用户通信方式如下：
首先，每个用户运行时都会将自己的如下信息送至服务器进行更新：用户唯一主键 ，当前ip地址，端口号。然后，用户之间通过各自的唯一主键去服务器进行查询，之后建立联系进行通信。比如client1想和client2进行通信，那么client1就去服务器查询client2当前的ip和端口号，然后通过ip和端口号连接client2，连接成功后，二者就可以实现通信了。
				
软件运行说明文档
	代码实现结果：
			代码可以分为两部分实现，一个是用户端，一个是服务器端。
服务器端
	服务器端代码功能可以分为用户注册和用户查询两个，当接受到一个用户连接时判断用户是否注册，为用户注册创建注册表。接受用户查询请求时，为用户返回查询结果。
  
![image](https://user-images.githubusercontent.com/76092270/173814685-ac2ea830-48c6-4810-9945-e05d6b35da49.png)
（服务器启动并为用户创建注册表）

![image](https://user-images.githubusercontent.com/76092270/173814851-3e5c750c-dd49-429d-8b58-f3c361dc1e0d.png)
![image](https://user-images.githubusercontent.com/76092270/173814868-e79e0b3a-8041-48bb-b295-41824afa1260.png)
（服务器为用户提供查询服务）

用户端
用户端代码的功能可以分为两部分，一个是发送请求和接受请求。用户向服务发送请求进行查询，获得查询结果后向对应用户发送请求与对方进行通信。

![image](https://user-images.githubusercontent.com/76092270/173814903-4721d09d-5791-4aa6-a8f1-40ad15f3338c.png)
(向服务器发送查询请求）

![image](https://user-images.githubusercontent.com/76092270/173814935-878da40f-631a-4812-b86d-669eccca5831.png)![image](https://user-images.githubusercontent.com/76092270/173814947-0def8339-cac9-4b6e-8445-8e9da3e6ecb2.png)
（根据查询结果向对应的用户发起连接，对应用户接受到连接）

![image](https://user-images.githubusercontent.com/76092270/173814995-998de09b-7275-41a9-b09b-020188a35bfc.png)
（用户之间进行通信）

注：整个项目中有两个主函数，分别在用户端（TalkClient）和服务器端（TalkServer）。在运行程序时，首先运行TalkServer，再运行TalkClient。在服务器的console窗口会输出服务器的注册表信息，复制注册表的clientId（用户唯一主键）用于查询连接就可以实现P2P通信了。
