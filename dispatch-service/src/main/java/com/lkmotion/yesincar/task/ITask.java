package com.lkmotion.yesincar.task;

/**
 * @author dulin
 */
public interface ITask {

    public int execute(long current);

    public int getTaskId();

    public boolean isTime();
}
