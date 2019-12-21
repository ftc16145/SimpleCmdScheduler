package edu.wpi.first.wpilibj2.command;

import static java.lang.System.currentTimeMillis;

/**
 * Class that holds scheduling state for a command.  Used internally by the
 * {@link SimpleCmdScheduler}.
 */
class CommandState {
  //The time since this command was initialized.
  private double m_startTime = -1;

  //Whether or not it is interruptible.
  private final boolean m_interruptible;

  // TODO:  I don't understand why we call startRunning to set m_startTime to -1

  CommandState(boolean interruptible) {
    m_interruptible = interruptible;
    startTiming();
    startRunning();
  }

  // TODO: Must change the currentTimeMillis to the Android timer.

  private void startTiming() {
    m_startTime = currentTimeMillis();
  }

  synchronized void startRunning() {
    m_startTime = -1;
  }

  boolean isInterruptible() {
    return m_interruptible;
  }

  double timeSinceInitialized() {
    return m_startTime != -1 ? currentTimeMillis() - m_startTime : -1;
  }
}
