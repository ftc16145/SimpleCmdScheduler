/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firstinspires.ftc.teamcode.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InstantCommandTest extends CommandTestBase {
  @Test
  void instantCommandScheduleTest() {
    SimpleCmdScheduler scheduler = new SimpleCmdScheduler();

    ConditionHolder cond = new ConditionHolder();

    InstantCommand command = new InstantCommand(() -> cond.setCondition(true));

    scheduler.schedule(command);
    scheduler.run();

    assertTrue(cond.getCondition());
    assertFalse(scheduler.isScheduled(command));
  }
}
