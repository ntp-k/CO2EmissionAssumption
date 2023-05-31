package co2emissionassumption;

public class Rules {
    private int start;
    private int end;
    private int percent;

    public Rules(int start, int end, int percent) {
        this.start = start;
        this.end = end;
        this.percent = percent;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
