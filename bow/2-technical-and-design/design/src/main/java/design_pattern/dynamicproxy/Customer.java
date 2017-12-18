//  Copyright 2017 The KeepTry Open Source Project
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

package design_pattern.dynamicproxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Customer {
    String result;

    private static void proxyTest() {
        CanInterface shipper = new Worker("Zhang");
        CanInterface proxy = new Proxyer(shipper);
        proxy.implDemands(" My requirement");
    }

    private static void dynamicPorxyTest() {
        CanInterface shipper = new Worker("Zhang");
        InvocationHandler handler = new HandlerImplement(shipper);

        // E.g. any DB
        CanInterface dynamicProxy = (CanInterface) Proxy.newProxyInstance(
                shipper.getClass().getClassLoader(),
                shipper.getClass().getInterfaces(),
                handler
        );
        dynamicProxy.implDemands("Modify management");
        dynamicProxy.qualityAccept("Modify management");
    }

    public static void main(String args[]) throws Exception {
        // proxyTest();
        dynamicPorxyTest();
    }

}
