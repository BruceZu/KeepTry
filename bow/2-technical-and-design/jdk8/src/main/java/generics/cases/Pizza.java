//  Copyright 2019 The KeepTry Open Source Project
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

package generics.cases;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public abstract class Pizza {
    final Set<Topping> toppings;

    public Pizza(Builder<?> builder) {
        toppings = builder.toppings.clone(); // why clone() see item 50
    }

    // ------------------------

    public enum Topping {
        HAM,
        MUSHROOM,
        ONION,
        PEPPER,
        SAUSAGE
    }
    // -------------------------

    abstract static class Builder<T extends Builder<T>> {
        EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

        // all T are for this method
        public T addTopping(Topping topping) {
            toppings.add(topping);
            return self();
        }

        protected abstract T self();

        abstract Pizza build();
    }
}
// ------New York Pizza
class NewYorkPizza extends Pizza {

    private final Size size;

    public NewYorkPizza(NYBuilder b) {
        super(b);
        size = b.size;
    }

    // --
    public enum Size {
        SMAL,
        MEDIUM,
        LARGE
    }

    // ---
    static class NYBuilder extends Pizza.Builder<NYBuilder> {
        private final Size size;

        public NYBuilder(Size size) {
            this.size = Objects.requireNonNull(size);
        }

        @Override
        protected NYBuilder self() {
            return this;
        }

        @Override
        public NewYorkPizza build() {
            return new NewYorkPizza(this);
        }
    }
}
// --- Calzone Pizza

class Calzone extends Pizza {
    private final boolean sauceInside;

    public Calzone(CZBuilder b) {
        super(b);
        this.sauceInside = b.sauceInside;
    }

    // ---
    public static class CZBuilder extends Pizza.Builder<CZBuilder> {
        private boolean sauceInside = false; // default

        public CZBuilder sauceInside() {
            sauceInside = true;
            return this;
        }

        @Override
        protected CZBuilder self() {
            return this;
        }

        @Override
        Calzone build() {
            return new Calzone(this);
        }
    }
}
