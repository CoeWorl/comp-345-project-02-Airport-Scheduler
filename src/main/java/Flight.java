import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import layout.Airport;
import layout.Gate;
import layout.Terminal;

import java.util.Objects;

public class Flight {
    private final String flightNumber;
    private final Airport src;
    private final Airport dest;
    private final long deptTime; // Unix timestamp
    private final long arrTime; // Unix timestamp
    private String status;
    private final Terminal terminal;
    private final Gate gate;

    @JsonCreator
    public Flight(@JsonProperty("flightNumber") String flightNumber,
                  @JsonProperty("src") Airport src,
                  @JsonProperty("dest") Airport dest,
                  @JsonProperty("deptTime") long deptTime,
                  @JsonProperty("arrTime") long arrTime,
                  @JsonProperty("status") String status,
                  @JsonProperty("terminal") Terminal terminal,
                  @JsonProperty("gate") Gate gate) {
        this.flightNumber = flightNumber;
        this.src = src;
        this.dest = dest;
        this.deptTime = deptTime;
        this.arrTime = arrTime;
        this.status = status;
        this.terminal = terminal;
        this.gate = gate;
        AirportController.getInstance().getFlights().put(flightNumber, this);
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

    public long getDeptTime() {
        return deptTime;
    }

    public long getArrTime() {
        return arrTime;
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

    @JsonValue
    public String toJsonKey() {
        // Serialize the Flight object as its flightNumber when used as a key
        return flightNumber;
    }

    @JsonCreator
    public static Flight fromJsonKey(String flightNumber) {
        // Deserialize the Flight object from its flightNumber
        return new Flight(flightNumber, null, null, 0, 0, null, null, null);
    }

    @Override
    public String toString() {
        return flightNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(flightNumber, flight.flightNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightNumber);
    }

    public long getDepartureTime() {
        return deptTime;
    }

    public void changeStatus(String status) {
        this.status = status;
    }
}