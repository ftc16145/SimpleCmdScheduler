/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj2.command;

import org.junit.jupiter.api.Test;

import edu.wpi.first.wpilibj2.util.Timer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandDecoratorTest extends CommandTestBase {
  @Test
  void withTimeoutTest() {
    SimpleCmdScheduler scheduler = new SimpleCmdScheduler();

    Command command1 = new WaitCommand(10);

    Command timeout = command1.withTimeout(2);

    scheduler.schedule(timeout);
    scheduler.run();

    assertFalse(scheduler.isScheduled(command1));
    assertTrue(scheduler.isScheduled(timeout));

    Timer.delay(3);
    scheduler.run();

    assertFalse(scheduler.isScheduled(timeout));
  }

  @Test
  void withInterruptTest() {
    SimpleCmdScheduler scheduler = new SimpleCmdScheduler();

    ConditionHolder condition = new ConditionHolder();

    Command command = new WaitCommand(10).withInterrupt(condition::getCondition);

    scheduler.schedule(command);
    scheduler.run();
    assertTrue(scheduler.isScheduled(command));
    condition.setCondition(true);
    scheduler.run();
    assertFalse(scheduler.isScheduled(command));
  }

  @Test
  void beforeStartingTest() {
    SimpleCmdScheduler scheduler = new SimpleCmdScheduler();

    ConditionHolder condition = new ConditionHolder();
    condition.setCondition(false);

    Command command = new InstantCommand();

    scheduler.schedule(command.beforeStarting(() -> condition.setCondition(true)));

    assertTrue(condition.getCondition());
  }

  @Test
  void andThenLambdaTest() {
    SimpleCmdScheduler scheduler = new SimpleCmdScheduler();

    ConditionHolder condition = new ConditionHolder();
    condition.setCondition(false);

    Command command = new InstantCommand();

    scheduler.schedule(command.andThen(() -> condition.setCondition(true)));

    assertFalse(condition.getCondition());

    scheduler.run();

    assertTrue(condition.getCondition());
  }

  @Test
  void andThenTest() {
    SimpleCmdScheduler scheduler = new SimpleCmdScheduler();

    ConditionHolder condition = new ConditionHolder();
    condition.setCondition(false);

    Command command1 = new InstantCommand();
    Command command2 = new InstantCommand(() -> condition.setCondition(true));

    scheduler.schedule(command1.andThen(command2));

    assertFalse(condition.getCondition());

    scheduler.run();

    assertTrue(condition.getCondition());
  }

  @Test
  void deadlineWithTest() {
    SimpleCmdScheduler scheduler = new SimpleCmdScheduler();

    ConditionHolder condition = new ConditionHolder();
    condition.setCondition(false);

    Command dictator = new WaitUntilCommand(condition::getCondition);
    Command endsBefore = new InstantCommand();
    Command endsAfter = new WaitUntilCommand(() -> false);

    Command group = dictator.deadlineWith(endsBefore, endsAfter);

    scheduler.schedule(group);
    scheduler.run();

    assertTrue(scheduler.isScheduled(group));

    condition.setCondition(true);
    scheduler.run();

    assertFalse(scheduler.isScheduled(group));
  }

  @Test
  void alongWithTest() {
    SimpleCmdScheduler scheduler = new SimpleCmdScheduler();

    ConditionHolder condition = new ConditionHolder();
    condition.setCondition(false);

    Command command1 = new WaitUntilCommand(condition::getCondition);
    Command command2 = new InstantCommand();

    Command group = command1.alongWith(command2);

    scheduler.schedule(group);
    scheduler.run();

    assertTrue(scheduler.isScheduled(group));

    condition.setCondition(true);
    scheduler.run();

    assertFalse(scheduler.isScheduled(group));
  }

  @Test
  void raceWithTest() {
    SimpleCmdScheduler scheduler = new SimpleCmdScheduler();

    Command command1 = new WaitUntilCommand(() -> false);
    Command command2 = new InstantCommand();

    Command group = command1.raceWith(command2);

    scheduler.schedule(group);
    scheduler.run();

    assertFalse(scheduler.isScheduled(group));
  }

  @Test
  void perpetuallyTest() {
    SimpleCmdScheduler scheduler = new SimpleCmdScheduler();

    Command command = new InstantCommand();

    Command perpetual = command.perpetually();

    scheduler.schedule(perpetual);
    scheduler.run();
    scheduler.run();
    scheduler.run();

    assertTrue(scheduler.isScheduled(perpetual));
  }
}
