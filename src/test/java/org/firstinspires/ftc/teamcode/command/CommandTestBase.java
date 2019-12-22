/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firstinspires.ftc.teamcode.command;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;

//import edu.wpi.first.hal.sim.DriverStationSim;
//import edu.wpi.first.wpilibj.DriverStation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Basic setup for all {@link Command tests}."
 */
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
abstract class CommandTestBase {
  @BeforeEach
  void commandSetup() {
    SimpleCmdScheduler.getInstance().cancelAll();
    SimpleCmdScheduler.getInstance().enable();
    SimpleCmdScheduler.getInstance().clearButtons();
    CommandGroupBase.clearGroupedCommands();

//    setDSEnabled(true);
  }

  void setDSEnabled(boolean enabled) {
//    DriverStationSim sim = new DriverStationSim();
//    sim.setDsAttached(true);
//
//    sim.setEnabled(enabled);
//    sim.notifyNewData();
//    DriverStation.getInstance().isNewControlData();
//    while (DriverStation.getInstance().isEnabled() != enabled) {
//      try {
//        Thread.sleep(1);
//      } catch (InterruptedException exception) {
//        exception.printStackTrace();
//      }
//    }
  }

  class TestSubsystem extends SubsystemBase {
    TestSubsystem() {
      super();
    }
  }

  protected class MockCommandHolder {
    private final Command m_mockCommand = mock(Command.class);

    MockCommandHolder(boolean runWhenDisabled, Subsystem... requirements) {
      when(m_mockCommand.getRequirements()).thenReturn(new HashSet<>(Arrays.asList(requirements)));
      when(m_mockCommand.isFinished()).thenReturn(false);
      when(m_mockCommand.runsWhenDisabled()).thenReturn(runWhenDisabled);
    }

    Command getMock() {
      return m_mockCommand;
    }

    void setFinished(boolean finished) {
      when(m_mockCommand.isFinished()).thenReturn(finished);
    }

  }

  protected class Counter {
    int m_counter;

    void increment() {
      m_counter++;
    }
  }

  protected class ConditionHolder {
    private boolean m_condition;

    void setCondition(boolean condition) {
      m_condition = condition;
    }

    boolean getCondition() {
      return m_condition;
    }
  }
}
