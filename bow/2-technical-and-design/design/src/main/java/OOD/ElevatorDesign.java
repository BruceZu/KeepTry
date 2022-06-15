//  Copyright 2021 The KeepTry Open Source Project
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

package OOD;

public class ElevatorDesign {
  /*
  Event driven
   */
  public interface OuterPanel {
    void register(ElevatorStatus e);
    /*
    get request status from outer panel
    keep the status
    notice elevator
     */
    void request(int level, boolean isOn);

    void pending(int elevatorId);
  }

  public interface ElevatorPanel {
    void set(int levels);

    void move(boolean isUp);

    boolean stopAtNextLevel();

    void pending();

    void configureWeightLimitation(int w, int diff);
  }

  public interface OuterPanelListener {
    public void onOuterPanelEvent(OuterPanel outerPanel);
  }

  public interface InnerPanelListener {
    public void onInnerPanelEvent(OuterPanel outerPanel);
  }

  /*
    Status decided by
     - inner panel status
     - current level
     - weight
     - outer panel event
     - direction: up|down|null


   Each Elevator decide
   - handle outer panel event or not
     if it is full it will ignore any outer panel event
       -  how to know it is full?
     else it will pick up half way outer panel event, till reach the last inner/outer request level on current direction

     if its direction is null, no inner request.
     direction will be decided by which side has more outer panel waiting event
  */

  /*
  condition:
     if weight is not full
     if in stop status
  update the outerPanel status
  */

  public interface Calculator {
    // decide the direction and next stop level
    public void updateInnerPanel(int level, boolean inOn);

    public void updateOuterPanel(int level, boolean inOn);

    public int nextStopLevel();

    public void setCurrentLevel(int level);

    public int getCurrentLevel();
  }

  public class ElevatorStatusControlImp
      implements OuterPanelListener, InnerPanelListener, ElevatorListenter {
    Calculator c;
    ElevatorStatus e;
    int currentTargetLevel;
    Thread work =
        new Thread(
            new Runnable() {
              @Override
              public void run() {
                //
              }
            });

    public ElevatorStatusControlImp(Calculator c, ElevatorStatus e) {
      this.c = c;
      this.e = e;
      e.register(this);
    }

    @Override
    public void onPassLevelEvent(int l) {
      c.setCurrentLevel(l + 1); // assume time enough to stop if need stop
      // need stop at next level?
      if (!e.isInStop())
        if (c.nextStopLevel() == c.getCurrentLevel() + 1) {
          e.stopAtNextLevel();
        }
    }

    @Override
    public void onInnerPanelEvent(OuterPanel outerPanel) {}

    @Override
    public void onOuterPanelEvent(OuterPanel outerPanel) {
      // trigger start
    }
  }

  interface ElevatorListenter {
    void onPassLevelEvent(int l);
  }

  /*
   stop->open->close-> still-> move-> stop
   stop->still-> move->stop
  */
  interface ElevatorStatus {
    boolean isInStop();

    void move(boolean isUp);

    void stopAtNextLevel();

    // continue monitor the level status and issue passing level event
    void issuePassLevelEvent();

    void register(ElevatorListenter l);

    public void openDoor();

    public void closeDoor();
  }
}
