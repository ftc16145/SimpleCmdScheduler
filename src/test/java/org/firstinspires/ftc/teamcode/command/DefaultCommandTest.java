/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firstinspires.ftc.teamcode.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

class DefaultCommandTest extends CommandTestBase {
  @Test
  void defaultCommandScheduleTest() {
    SimpleCmdScheduler scheduler = new SimpleCmdScheduler();

    TestSubsystem hasDefaultCommand = new TestSubsystem();

    MockCommandHolder defaultHolder = new MockCommandHolder(true, hasDefaultCommand);
    Command defaultCommand = defaultHolder.getMock();

    scheduler.setDefaultCommand(hasDefaultCommand, defaultCommand);
    scheduler.run();

    assertTrue(scheduler.isScheduled(defaultCommand));
  }

  @Test
  void defaultCommandInterruptResumeTest() {
    SimpleCmdScheduler scheduler = new SimpleCmdScheduler();

    TestSubsystem hasDefaultCommand = new TestSubsystem();

    MockCommandHolder defaultHolder = new MockCommandHolder(true, hasDefaultCommand);
    Command defaultCommand = defaultHolder.getMock();
    MockCommandHolder interrupterHolder = new MockCommandHolder(true, hasDefaultCommand);
    Command interrupter = interrupterHolder.getMock();

    scheduler.setDefaultCommand(hasDefaultCommand, defaultCommand);
    scheduler.run();
    scheduler.schedule(interrupter);

    assertFalse(scheduler.isScheduled(defaultCommand));
    assertTrue(scheduler.isScheduled(interrupter));

    scheduler.cancel(interrupter);
    scheduler.run();

    assertTrue(scheduler.isScheduled(defaultCommand));
    assertFalse(scheduler.isScheduled(interrupter));
  }

  @Test
  void defaultCommandDisableResumeTest() {
    SimpleCmdScheduler scheduler = new SimpleCmdScheduler();

    TestSubsystem hasDefaultCommand = new TestSubsystem();

    MockCommandHolder defaultHolder = new MockCommandHolder(false, hasDefaultCommand);
    Command defaultCommand = defaultHolder.getMock();

    scheduler.setDefaultCommand(hasDefaultCommand, defaultCommand);
    scheduler.run();

    assertTrue(scheduler.isScheduled(defaultCommand));

    scheduler.setRobotDisabled(true);
    scheduler.run();

    assertFalse(scheduler.isScheduled(defaultCommand));

    scheduler.setRobotDisabled(false);
    scheduler.run();

    assertTrue(scheduler.isScheduled(defaultCommand));

    verify(defaultCommand).end(true);
  }
}
