//  Copyright 2016 The Sawdust Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package net;

import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import org.apache.sshd.common.io.DefaultIoServiceFactoryFactory;
import org.apache.sshd.common.io.IoAcceptor;
import org.apache.sshd.common.io.IoServiceFactoryFactory;
import org.apache.sshd.common.io.mina.MinaServiceFactoryFactory;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.BlockingArrayQueue;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.List;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@BindingAnnotation
@interface SshListenAddresses {
}

interface MySshServerFactory {
    MySshServer create();
}

class MySshServer extends SshServer {

    private List<SocketAddress> address;
    private volatile IoAcceptor daemonAcceptor;

    @Override
    public synchronized void start() throws IOException {
        if (daemonAcceptor == null) {
            if (sessionFactory == null) {
                sessionFactory = createSessionFactory();
            }
            sessionFactory.setServer(this);
            daemonAcceptor = createAcceptor();

            // the port number is automatically allocated from an ephemeral port range by
            // native method
            // Net.localPort()
            // InetSocketAddress address = new InetSocketAddress(0);
            daemonAcceptor.bind(address);
            log.info("start() listen on port="
                    + ((InetSocketAddress) daemonAcceptor.getBoundAddresses().iterator().next()).getPort());
        }
    }

    @Override
    public synchronized void stop() {
        if (daemonAcceptor != null) {
            try {
                daemonAcceptor.close(true).await();
                log.info("Stopped my SSHD");
            } catch (IOException e) {
                log.warn("Exception caught while closing", e);
            } finally {
                daemonAcceptor = null;
            }
        }
    }

    public IoAcceptor getIoAcceptor() {
        return daemonAcceptor;
    }


    @Inject(optional = false)
    public MySshServer(@SshListenAddresses List<SocketAddress> address) {
        this.address = address;
        setUpDefaultServer();
        setIoServiceFactoryFactory(new DefaultIoServiceFactoryFactory());
        System.setProperty(IoServiceFactoryFactory.class.getName(),
                MinaServiceFactoryFactory.class.getName());
    }
}

public class SocketTest {
    @Inject(optional = false)
    private MySshServerFactory sshdServerFactory;

    private Server prepareJettyServerForTestOnly() {
        // http://www.eclipse.org/jetty/documentation/current/embedded-examples.html#d0e19863
        QueuedThreadPool threadPool = new QueuedThreadPool(10 /** needed(acceptors=0 + selectors=9 + request=1*/, 1, 2,
                new BlockingArrayQueue<Runnable>(3 /* capacity*/, 2 /* growBy*/, 5 /* maxCapacity*/));
        threadPool.setName("HTTPD");

        Server httpd = new Server(threadPool);
        HttpConfiguration httpConfig = new HttpConfiguration();
        httpConfig.setRequestHeaderSize(128);
        httpConfig.setSendServerVersion(false);
        httpConfig.setSendDateHeader(true);
        ConnectionFactory connectionFactory = new HttpConnectionFactory(httpConfig);

        ServerConnector httpServerConnector =
                new ServerConnector(httpd,
                        null, null, null, 0, 3,
                        connectionFactory);
        // without this line the port number is automatically allocated from an ephemeral port range by
        // the native method
        // Net.localPort()
        httpServerConnector.setPort(9999);

        ServerConnector httpsServerConnector =
                new ServerConnector(httpd,
                        null, null, null, 0, 3,
                        new SslConnectionFactory(new SslContextFactory(), "http/1.1"),
                        connectionFactory);
        httpsServerConnector.setPort(8888);

        httpd.setConnectors(new Connector[]{httpServerConnector, httpsServerConnector});
        return httpd;
    }

    @Before
    public void setupInjection() {
        Guice.createInjector(
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        install(new FactoryModuleBuilder().build(MySshServerFactory.class));
                    }

                    @Provides
                    @Singleton
                    @SshListenAddresses
                    public List<SocketAddress> getListenAddresses() {
                        return Arrays.<SocketAddress>asList(new InetSocketAddress(29488));
                    }
                }

        ).injectMembers(this);
    }

    @Test(timeout = 2000L, expected = Test.None.class)
    public void portTest() throws ClassNotFoundException, IOException, Exception {

        // 1 server socket
        ServerSocket socketServer1 = new ServerSocket(9090, 0, InetAddress.getLoopbackAddress());
        Assert.assertEquals(socketServer1.getLocalPort(), 9090);

        // the port number is automatically allocated from an ephemeral port range
        // by native method
        // PlainSocketImpl.socketBind().
        ServerSocket socketServer2 = new ServerSocket(0, 0, InetAddress.getLoopbackAddress());
        Assert.assertNotEquals(socketServer2.getLocalPort(), 0);

        // 2 client socket
        // connect to above server socket

        // by native method
        // AbstractPlainSocketImpl.socketConnect()
        Socket clientSocket = new Socket("127.0.0.1", 9090);
        Assert.assertNotEquals(clientSocket.getLocalPort(), -1);
        Assert.assertEquals(clientSocket.getPort(), 9090); // connect to 9090

        Socket clientSocket2 = new Socket();
        clientSocket2.bind(new InetSocketAddress("localhost", 4445));
        clientSocket2.connect(new InetSocketAddress("127.0.0.1", 9090));
        Assert.assertEquals(clientSocket2.getPort(), 9090);
        Assert.assertEquals(clientSocket2.getLocalPort(), 4445);

        // 3 Jetty httpd
        Server httpd = prepareJettyServerForTestOnly();
        httpd.start();

        Connector con = httpd.getConnectors()[0];
        if (con instanceof ServerConnector) {
            ServerConnector serverCon = (ServerConnector) con;
            String host = serverCon.getHost();
            Assert.assertEquals(serverCon.getLocalPort(), 9999);
        }
        // 4 sshd
        // http://mina.apache.org/sshd-project/embedding_ssh.html
        // https://svn.apache.org/repos/asf/mina/sshd/tags/sshd-0.3.0/sshd-core/src/main/java/org/apache/sshd/SshServer.java
        // sshd case 1
        MySshServer sshdServer = sshdServerFactory.create();
        sshdServer.start();
        Assert.assertEquals(((InetSocketAddress) sshdServer.getIoAcceptor().getBoundAddresses().iterator().next())
                        .getPort(),
                29488);

        // sshd case 2
        SshServer sshdServer2 = SshServer.setUpDefaultServer();
        sshdServer2.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(new File("key.ser")));

        sshdServer2.start();
        Assert.assertNotEquals(sshdServer2.getPort(), 0);

        socketServer1.close();
        socketServer2.close();
        clientSocket.close();
        clientSocket2.close();
        httpd.stop();
        httpd.join();
        sshdServer.stop();
        sshdServer2.stop();
    }
}


