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
import com.google.inject.Scopes;
import com.google.inject.internal.UniqueAnnotations;
import dependencyInjection.guice.LifeManager;
import dependencyInjection.guice.Listener;

import java.lang.annotation.Annotation;

public class Client implements Listener {
    private final Service service;

    private void go() {
        System.out.println("client end point");
        service.go();
    }

    @Inject(optional = false)
    public Client(Service service) {
        this.service = service;
    }

    public static void main(String[] args) {
        LifeManager manager = new LifeManager();
        manager.add(Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Service.class)
                        .to(ServiceImpl.class)
                        .in(Scopes.SINGLETON);
                final Annotation id = UniqueAnnotations.create();
                bind(Listener.class).annotatedWith(id).to(Client.class);
            }
        }));
        manager.start();
    }

    @Override
    public void start() {
        go();
    }

    @Override
    public void stop() {

    }
}