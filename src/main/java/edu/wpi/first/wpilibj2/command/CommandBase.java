/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj2.command;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class CommandBase implements Command {

  protected Set<Subsystem> m_requirements = new HashSet<>();
  private String subsystem;
  private String name;

  protected CommandBase() {
    name = getClass().getName();
  }

  /**
   * Adds the specified requirements to the command.
   *
   * @param requirements the requirements to add
   */
  public final void addRequirements(Subsystem... requirements) {
    m_requirements.addAll(Arrays.asList(requirements) );
  }

  @Override
  public Set<Subsystem> getRequirements() {
    return m_requirements;
  }

  @Override
  public String getName() {
    return name;
  }

  /**
   * Sets the name of this Command.
   *
   * @param name name
   */
  @Override
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the subsystem name of this Command.
   *
   * @return Subsystem name
   */
  @Override
  public String getSubsystem() {
    return subsystem;
  }

  /**
   * Sets the subsystem name of this Command.
   *
   * @param subsystem subsystem name
   */
  @Override
  public void setSubsystem(String subsystem) {
    this.subsystem = subsystem;
  }
}
