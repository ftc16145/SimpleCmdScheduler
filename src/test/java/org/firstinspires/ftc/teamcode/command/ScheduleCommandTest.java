/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.firstinspires.ftc.teamcode.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;

class ScheduleCommandTest extends CommandTestBase {
  @Test
  void scheduleCommandScheduleTest() {
    SimpleCmdScheduler scheduler = new SimpleCmdScheduler();

    MockCommandHolder command1Holder = new MockCommandHolder(true);
    Command command1 = command1Holder.getMock();
    MockCommandHolder command2Holder = new MockCommandHolder(true);
    Command command2 = command2Holder.getMock();

    ScheduleCommand scheduleCommand = new ScheduleCommand(command1, command2);

    scheduler.schedule(scheduleCommand);

    verify(command1).schedule();
    verify(command2).schedule();
  }

  @Test
  void scheduleCommandDuringRunTest() {
    SimpleCmdScheduler scheduler = SimpleCmdScheduler.getInstance();

    InstantCommand toSchedule = new InstantCommand();
    ScheduleCommand scheduleCommand = new ScheduleCommand(toSchedule);
    SequentialCommandGroup group =
        new SequentialCommandGroup(new InstantCommand(), scheduleCommand);

    scheduler.schedule(group);
    scheduler.schedule(new InstantCommand().perpetually());
    scheduler.run();
    assertDoesNotThrow(scheduler::run);
  }
}
