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

package dependencyInjection;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.PrivateModule;
import com.google.inject.Scopes;
import com.google.inject.internal.UniqueAnnotations;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import dependencyInjection.guice.LifeManager;
import dependencyInjection.guice.Listener;

import java.lang.annotation.Annotation;

public class Client implements Listener {
    private final Service service1;
    private final Service service2;

    private void go1() {
        System.out.println("client end point");
        service1.go();

    }

    private void go2() {
        System.out.println("client end point");
        service2.go();
    }

    @Inject(optional = false)
    public Client(@Named("1") Service service1, @Named("2") Service service2) {
        this.service1 = service1;
        this.service2 = service2;
    }

    public static void main(String[] args) {
        LifeManager manager = new LifeManager();
        manager.add(Guice.createInjector(new PrivateModule() {
            @Override
            protected void configure() {
                bind(Service.class).annotatedWith(Names.named("1"))
                        .to(ServiceImpl.class)
                        .in(Scopes.SINGLETON);
                expose(Service.class).annotatedWith(Names.named("1"));
                bind(ImplWorker.class).toInstance(new ImplWorker() {
                    @Override
                    public void work() {
                        System.out.println("work 1 done");
                    }
                });
            }
        }, new PrivateModule() {
            @Override
            protected void configure() {
                bind(Service.class).annotatedWith(Names.named("2"))
                        .to(ServiceImpl.class)
                        .in(Scopes.SINGLETON);
                expose(Service.class).annotatedWith(Names.named("2"));

                bind(ImplWorker.class).toInstance(new ImplWorker() {
                    @Override
                    public void work() {
                        System.out.println("work 2 done");
                    }
                });
            }
        }, new AbstractModule() {
            @Override
            protected void configure() {
                final Annotation id = UniqueAnnotations.create();
                bind(Listener.class).annotatedWith(id).to(Client.class).asEagerSingleton();
            }
        }));
        manager.start();
    }

    @Override
    public void start() {
        go1();
        go2();
    }

    @Override
    public void stop() {

    }
}