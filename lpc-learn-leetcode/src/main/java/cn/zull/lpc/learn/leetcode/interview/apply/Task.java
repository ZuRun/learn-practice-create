package cn.zull.lpc.learn.leetcode.interview.apply;

/**
 * @author zurun
 * @date 2022/9/4 09:19:46
 */
public class Task implements Comparable<Task> {
    private String name;
    private Integer priority;
    private Integer resourceNum;

    public void execute() {
        // todo 任务
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getResourceNum() {
        return resourceNum;
    }

    public void setResourceNum(Integer resourceNum) {
        this.resourceNum = resourceNum;
    }

    @Override
    public int compareTo(Task t) {
        if (t == null) return 0;
        return this.getPriority().compareTo(t.getPriority());
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", priority=" + priority +
                ", resourceNum=" + resourceNum +
                '}';
    }
}
