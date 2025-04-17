//package users;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import java.io.FileReader;
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.util.List;
////test
//public class FlightJson {
//
//    private String filePath;
//
//    public FlightJson(String filePath){
//        this.filePath = filePath;
//    }
//
//    /**creates flight objects for each flight in the json file
//     * input - none
//     * output- void
//     * @throw IllegalArgumentException if file not found
//     */
//    public void createFlights(){
//        try (FileReader reader = new FileReader(filePath)) {
//            Type flightListType = new TypeToken<List<RawFlight>>() {}.getType();
//            List<RawFlight> rawFlights = new Gson().fromJson(reader, flightListType);
//
//            for (RawFlight rf : rawFlights) {
//                AirportController ac = AirportController.getInstance();
//
//                Airport srcAirport = ac.getAirports().get(rf.src);
//                if (srcAirport == null) {
//                    srcAirport = new Airport(rf.src, rf.srcName);
//                }
//
//                Airport destAirport = ac.getAirports().get(rf.dest);
//                if (destAirport == null) {
//                    destAirport = new Airport(rf.dest, rf.destName);
//                }
//
//                Terminal terminal = srcAirport.getTerminals().get(rf.terminal);
//                Gate gate = new Gate(rf.gate, rf.gateNumber, false);
//
//                Flight flight = new Flight(
//                    rf.flightNumber,
//                    srcAirport,
//                    destAirport,
//                    rf.departureTime,
//                    rf.arrivalTime,
//                    rf.status,
//                    terminal,
//                    gate
//                );
//            }
//        } catch (IOException e) {
//            throw new IllegalArgumentException("File Not Found");
//        }
//    }
//
//}
//
