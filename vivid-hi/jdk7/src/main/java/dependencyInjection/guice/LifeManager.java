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

package dependencyInjection.guice;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Providers;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LifeManager {
    private final List<Provider<Listener>> listeners = newList();

    // Index of the last listener to start successfully; -1 when not started.
    private int startedIndex = -1;

    private static List<Binding<Listener>> get(Injector i) {
        return i.findBindingsByType(new TypeLiteral<Listener>() {
        });
    }

    private static <T> List<T> newList() {
        return Lists.newArrayListWithCapacity(4);
    }

    // Add a single listener.
    public void add(Listener listener) {
        listeners.add(Providers.of(listener));
    }

    // Add a single listener.
    public void add(Provider<Listener> listener) {
        listeners.add(listener);
    }

    // Add all {@link Listener}s registered in the Injector.
    public void add(Injector injector) {
        Preconditions.checkState(startedIndex < 0, "Already started");
        for (Binding<Listener> binding : get(injector)) {
            add(binding.getProvider());
        }
    }

    // Add all {@link Listener}s registered in the Injectors.
    public void add(Injector... injectors) {
        for (Injector i : injectors) {
            add(i);
        }
    }

    // Start all listeners, in the order they were registered.
    // Managed by Guice and do something on bootstartup
    public void start() {
        for (int i = startedIndex + 1; i < listeners.size(); i++) {
            Listener listener = listeners.get(i).get();
            startedIndex = i;
            listener.start();
        }
    }

    // Stop all listeners, in the reverse order they were registered.
    public void stop() {
        for (int i = startedIndex; 0 <= i; i--) {
            Listener obj = listeners.get(i).get();
            try {
                obj.stop();
            } catch (Throwable err) {
                LoggerFactory.getLogger(obj.getClass()).warn("Failed to stop", err);
            }
            startedIndex = i - 1;
        }
    }
}
