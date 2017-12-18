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

public class Proxyer implements CanInterface {

    private CanInterface worker;

    public Proxyer(CanInterface coder) {
        this.worker = coder;
    }

    @Override
    public void implDemands(String demandName) {
        worker.implDemands(demandName);
    }

    @Override
    public void qualityAccept(String demandName) {

    }
}
