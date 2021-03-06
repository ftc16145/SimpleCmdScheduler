/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firstinspires.ftc.teamcode.command;

import org.junit.jupiter.api.Test;

import static org.firstinspires.ftc.teamcode.command.CommandGroupBase.parallel;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RobotDisabledCommandTest extends CommandTestBase {
  @Test
  void robotDisabledCommandCancelTest() {
    SimpleCmdScheduler scheduler = SimpleCmdScheduler.getInstance();
    scheduler.setRobotDisabled(false);

    MockCommandHolder holder = new MockCommandHolder(false);
    Command mockCommand = holder.getMock();

    scheduler.schedule(mockCommand);

    assertTrue(scheduler.isScheduled(mockCommand));

    scheduler.setRobotDisabled(true);

    scheduler.run();

    assertFalse(scheduler.isScheduled(mockCommand));

    scheduler.setRobotDisabled(false);
  }

  @Test
  void runWhenDisabledTest() {
    SimpleCmdScheduler scheduler = SimpleCmdScheduler.getInstance();

    MockCommandHolder holder = new MockCommandHolder(true);
    Command mockCommand = holder.getMock();

    scheduler.schedule(mockCommand);

    assertTrue(scheduler.isScheduled(mockCommand));

//    setDSEnabled(false);

    scheduler.run();

    assertTrue(scheduler.isScheduled(mockCommand));
  }

  @Test
  void sequentialGroupRunWhenDisabledTest() {
    SimpleCmdScheduler scheduler = SimpleCmdScheduler.getInstance();

    MockCommandHolder command1Holder = new MockCommandHolder(true);
    Command command1 = command1Holder.getMock();
    MockCommandHolder command2Holder = new MockCommandHolder(true);
    Command command2 = command2Holder.getMock();
    MockCommandHolder command3Holder = new MockCommandHolder(true);
    Command command3 = command3Holder.getMock();
    MockCommandHolder command4Holder = new MockCommandHolder(false);
    Command command4 = command4Holder.getMock();

    Command runWhenDisabled = new SequentialCommandGroup(command1, command2);
    Command dontRunWhenDisabled = new SequentialCommandGroup(command3, command4);

    scheduler.schedule(runWhenDisabled);
    scheduler.schedule(dontRunWhenDisabled);

    scheduler.setRobotDisabled(true);

    scheduler.run();

    assertTrue(scheduler.isScheduled(runWhenDisabled));
    assertFalse(scheduler.isScheduled(dontRunWhenDisabled));
  }

  @Test
  void parallelGroupRunWhenDisabledTest() {
    SimpleCmdScheduler scheduler = SimpleCmdScheduler.getInstance();

    MockCommandHolder command1Holder = new MockCommandHolder(true);
    Command command1 = command1Holder.getMock();
    MockCommandHolder command2Holder = new MockCommandHolder(true);
    Command command2 = command2Holder.getMock();
    MockCommandHolder command3Holder = new MockCommandHolder(true);
    Command command3 = command3Holder.getMock();
    MockCommandHolder command4Holder = new MockCommandHolder(false);
    Command command4 = command4Holder.getMock();

    Command runWhenDisabled = new ParallelCommandGroup(command1, command2);
    Command dontRunWhenDisabled = new ParallelCommandGroup(command3, command4);

    scheduler.schedule(runWhenDisabled);
    scheduler.schedule(dontRunWhenDisabled);

    scheduler.setRobotDisabled(true);

    scheduler.run();

    assertTrue(scheduler.isScheduled(runWhenDisabled));
    assertFalse(scheduler.isScheduled(dontRunWhenDisabled));
  }

  @Test
  void conditionalRunWhenDisabledTest() {

    MockCommandHolder command1Holder = new MockCommandHolder(true);
    Command command1 = command1Holder.getMock();
    MockCommandHolder command2Holder = new MockCommandHolder(true);
    Command command2 = command2Holder.getMock();
    MockCommandHolder command3Holder = new MockCommandHolder(true);
    Command command3 = command3Holder.getMock();
    MockCommandHolder command4Holder = new MockCommandHolder(false);
    Command command4 = command4Holder.getMock();

    SimpleCmdScheduler scheduler = SimpleCmdScheduler.getInstance();
    scheduler.setRobotDisabled(true);

    Command runWhenDisabled = new ConditionalCommand(command1, command2, () -> true);
    Command dontRunWhenDisabled = new ConditionalCommand(command3, command4, () -> true);

    scheduler.schedule(runWhenDisabled, dontRunWhenDisabled);

    assertTrue(scheduler.isScheduled(runWhenDisabled));
    assertFalse(scheduler.isScheduled(dontRunWhenDisabled));
  }

  @Test
  void selectRunWhenDisabledTest() {
    // TODO: Fix this test
//    MockCommandHolder command1Holder = new MockCommandHolder(true);
//    Command command1 = command1Holder.getMock();
//    MockCommandHolder command2Holder = new MockCommandHolder(true);
//    Command command2 = command2Holder.getMock();
//    MockCommandHolder command3Holder = new MockCommandHolder(true);
//    Command command3 = command3Holder.getMock();
//    MockCommandHolder command4Holder = new MockCommandHolder(false);
//    Command command4 = command4Holder.getMock();
//
//    Command runWhenDisabled = new SelectCommand(Map.of(1, command1, 2, command2), () -> 1);
//    Command dontRunWhenDisabled = new SelectCommand(Map.of(1, command3, 2, command4), () -> 1);
//
//    SimpleCmdScheduler scheduler = new SimpleCmdScheduler();
//    scheduler.setRobotDisabled(true);
//
//    scheduler.schedule(runWhenDisabled, dontRunWhenDisabled);
//
//    assertTrue(scheduler.isScheduled(runWhenDisabled));
//    assertFalse(scheduler.isScheduled(dontRunWhenDisabled));
  }

  @Test
  void parallelConditionalRunWhenDisabledTest() {

    MockCommandHolder command1Holder = new MockCommandHolder(true);
    Command command1 = command1Holder.getMock();
    MockCommandHolder command2Holder = new MockCommandHolder(true);
    Command command2 = command2Holder.getMock();
    MockCommandHolder command3Holder = new MockCommandHolder(true);
    Command command3 = command3Holder.getMock();
    MockCommandHolder command4Holder = new MockCommandHolder(false);
    Command command4 = command4Holder.getMock();

    SimpleCmdScheduler scheduler = SimpleCmdScheduler.getInstance();
    scheduler.setRobotDisabled(true);

    Command runWhenDisabled = new ConditionalCommand(command1, command2, () -> true);
    Command dontRunWhenDisabled = new ConditionalCommand(command3, command4, () -> true);

    Command parallel = parallel(runWhenDisabled, dontRunWhenDisabled);

    scheduler.schedule(parallel);

    assertFalse(scheduler.isScheduled(runWhenDisabled));
  }
}
