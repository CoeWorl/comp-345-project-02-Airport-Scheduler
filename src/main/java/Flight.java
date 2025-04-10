import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import layout.Airport;
import layout.Gate;
import layout.Terminal;

import java.time.Instant;
import layout.Airport;
import layout.Terminal;
import layout.Gate;

public class Flight {
    private String flightNumber;
    private Airport src;
    private Airport dest;
    private long deptTime; // Unix timestamp
    private long arrTime; // Unix timestamp
    private String status;
    private Terminal terminal;
    private Gate gate;

    @JsonCreator
    public Flight(@JsonProperty String flightNumber,
                  @JsonProperty Airport src,
                  @JsonProperty Airport dest,
                  @JsonProperty long deptTime,
                  @JsonProperty long arrTime,
                  @JsonProperty String status,
                  @JsonProperty Terminal terminal,
                  @JsonProperty Gate gate) {
        this.flightNumber = flightNumber;
        this.src = src;
        this.dest = dest;
        this.deptTime = deptTime;
        this.arrTime = arrTime;
        this.status = status;
        this.terminal = terminal;
        this.gate = gate;
    }

    private void printTime(long time) {
        // Converting Unix timestamp into an instant
        Instant instant = Instant.ofEpochSecond(time);
        // Setting timezone we want to use
        ZoneId zoneId = ZoneId.of( "UTC");
        // Creating ZonedDateTime from instant and timezone and printing it
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, zoneId);
        System.out.println(zdt);
    }

    public void getDeptTime() {
        printTime(deptTime);
    }

    public void getArrTime() {
        printTime(arrTime);
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public Airport getSrc() {
        return src;
    }
    public Airport getDest() {
        return dest;
    }
    public String getStatus() {
        return status;
    }
    public Terminal getTerminal() {
        return terminal;
    }
    public Gate getGate() {
        return gate;
    }

    public void changeStatus(String status) {
        this.status = status;
    }

    public long getDepartureTime(){
        return deptTime;
    }
}
